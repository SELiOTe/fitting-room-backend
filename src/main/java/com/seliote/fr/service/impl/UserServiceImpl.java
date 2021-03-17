package com.seliote.fr.service.impl;

import com.seliote.fr.config.auth.TokenFilter;
import com.seliote.fr.config.auth.TokenHandler;
import com.seliote.fr.config.auth.TokenModel;
import com.seliote.fr.exception.DataException;
import com.seliote.fr.exception.ServiceException;
import com.seliote.fr.mapper.UserAvatarMapper;
import com.seliote.fr.mapper.UserMapper;
import com.seliote.fr.mapper.UserPositionMapper;
import com.seliote.fr.model.mi.user.*;
import com.seliote.fr.model.si.sms.CheckLoginSi;
import com.seliote.fr.model.si.user.*;
import com.seliote.fr.model.so.user.InfoSo;
import com.seliote.fr.model.so.user.LoginSo;
import com.seliote.fr.model.so.user.OthersInfoBatchSo;
import com.seliote.fr.service.RedisService;
import com.seliote.fr.service.SmsService;
import com.seliote.fr.service.UserService;
import com.seliote.fr.util.CommonUtils;
import com.seliote.fr.util.ReflectUtils;
import com.seliote.fr.util.TextUtils;
import com.seliote.fr.util.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 用户帐户 Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    // 资源文件目录
    @Value("${sys.resource-path}")
    private String resourcePath;

    private final RedisService redisService;
    private final SmsService smsService;
    private final TokenHandler tokenHandler;
    private final UserMapper userMapper;
    private final UserPositionMapper userPositionMapper;
    private final UserAvatarMapper userAvatarMapper;

    @Autowired
    public UserServiceImpl(RedisService redisService,
                           SmsService smsService,
                           TokenHandler tokenHandler,
                           UserMapper userMapper,
                           UserPositionMapper userPositionMapper,
                           UserAvatarMapper userAvatarMapper) {
        this.redisService = redisService;
        this.smsService = smsService;
        this.tokenHandler = tokenHandler;
        this.userMapper = userMapper;
        this.userPositionMapper = userPositionMapper;
        this.userAvatarMapper = userAvatarMapper;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    @Transactional
    public LoginSo login(LoginSi si) {
        if (!smsService.checkLogin(ReflectUtils.copy(si, CheckLoginSi.class))) {
            log.warn("Check sms for login failed for {}", si);
            return new LoginSo(1, null);
        }
        // 未注册的自动注册
        if (!isRegistered(ReflectUtils.copy(si, IsRegisteredSi.class))) {
            log.info("Auto register for: {}", si);
            register(ReflectUtils.copy(si, RegisterSi.class));
        }
        var id = userId(ReflectUtils.copy(si, UserIdSi.class));
        if (id.isEmpty()) {
            log.error("Error when select user id for: {}", si);
            throw new DataException();
        }
        var idStr = id.get().toString();
        var token = tokenHandler.generateToken(new TokenModel(idStr, Instant.now()));
        redisService.set(redisService.formatKey(TokenFilter.REDIS_NAMESPACE, idStr), token);
        log.info("Login success: {}", si);
        return new LoginSo(0, token);
    }

    @Override
    public boolean isRegistered(IsRegisteredSi si) {
        var count = userMapper.countUser(ReflectUtils.copy(si, CountUserMi.class));
        log.info("Count for: {}, result: {}", si, count);
        if (count == null || count < 0) {
            throw new ServiceException("Error when count user for: " + si.toString());
        }
        return count > 0;
    }

    @Override
    @Transactional
    public void register(RegisterSi si) {
        var mi = ReflectUtils.copy(si, InsertMi.class);
        var tmp = System.currentTimeMillis() + "";
        mi.setNickname("fr_" + tmp.substring(tmp.length() - 7));
        mi.setGender((short) 0);
        log.info("Register for: {}", si);
        userMapper.insert(mi);
    }

    @Override
    public Optional<Long> userId(UserIdSi si) {
        return Optional.ofNullable(userMapper.selectId(ReflectUtils.copy(si, SelectIdMi.class)));
    }

    @Override
    public Long currentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.error("Can not get current user authentication, uri: {}",
                    CommonUtils.getUri().orElse("null"));
            throw new ServiceException("Could not get current authentication");
        }
        var id = authentication.getName();
        if (id == null || id.length() <= 0) {
            log.error("Can not get current user name, uri: {}", CommonUtils.getUri().orElse("null"));
            throw new ServiceException("Could not get current authentication name");
        }
        return Long.parseLong(id);
    }

    @Override
    @Transactional
    public void position(PositionSi si) {
        userPositionMapper.insert(ReflectUtils.copy(si, com.seliote.fr.model.mi.userposition.InsertMi.class));
        log.info("Save user position: {}", si);
    }

    @Override
    public InfoSo info(Long userId) {
        var mo = userMapper.selectInfo(userId);
        var so = ReflectUtils.copy(mo, InfoSo.class);
        log.info("Get info: {}, result: {}", userId, so);
        return so;
    }

    @Override
    @Transactional
    public void updateInfo(UpdateInfoSi si) {
        var sha1 = TextUtils.sha1(si.getAvatar());
        var updateInfoMi = ReflectUtils.copy(si, UpdateInfoMi.class);
        userMapper.updateInfo(updateInfoMi);
        var insertMi = new com.seliote.fr.model.mi.useravatar.InsertMi(si.getId(), sha1);
        userAvatarMapper.insert(insertMi);
        // 后存图，防止 IO 异常导致部分回滚
        var file = avatarSha1ToPath(sha1).toFile();
        // 不多次存
        if (file.exists()) {
            return;
        }
        try (var fos = new FileOutputStream(file)) {
            fos.write(si.getAvatar());
        } catch (IOException exception) {
            log.error("Could not save avatar, IOException message: {}", exception.getMessage());
            throw new DataException(exception);
        }
    }

    @Override
    public List<OthersInfoBatchSo> othersInfoBatch(List<OthersInfoBatchSi> si) {
        var mi = ReflectUtils.copy(si, new TypeReference<List<SelectInfoBatchMi>>() {
        });
        var mo = userMapper.selectBatchInfo(mi);
        // 自动类型推断了
        return ReflectUtils.copy(mo, new TypeReference<>() {
        });
    }

    /**
     * 头像 SHA1 得到头像存储路径
     *
     * @param sha1 头像 SHA1
     * @return 存储路径
     */
    @NonNull
    private Path avatarSha1ToPath(@NonNull String sha1) {
        final var avatarDir = "image";
        var avatarPath = Paths.get(resourcePath, avatarDir, sha1.substring(0, 1), sha1.substring(1, 2), sha1)
                .normalize();
        log.debug("Get avatar for: {}, result: {}", sha1, avatarPath);
        return avatarPath;
    }
}
