package com.seliote.fr.service;

import com.seliote.fr.annotation.validation.BatchInterfaceI;
import com.seliote.fr.annotation.validation.UserId;
import com.seliote.fr.model.si.user.*;
import com.seliote.fr.model.so.user.OthersInfoBatchSo;
import com.seliote.fr.model.so.user.InfoSo;
import com.seliote.fr.model.so.user.LoginSo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * 用户帐户 Service
 *
 * @author seliote
 */
@Validated
public interface UserService {

    /**
     * 登录用户帐户，未注册的账户将会自动注册
     *
     * @param si SI
     * @return SO
     */
    @Valid
    LoginSo login(@NotNull @Valid LoginSi si);

    /**
     * 判断用户是否注册
     *
     * @return 是返回 true，否则返回 false
     */
    boolean isRegistered(@NotNull @Valid IsRegisteredSi si);

    /**
     * 用户注册
     */
    void register(@NotNull @Valid RegisterSi si);

    /**
     * 通过手机号码获取用户 ID
     *
     * @param si SI
     * @return 用户 ID，当用户不存在时返回 null
     */
    @NotNull
    Optional<Long> userId(@NotNull @Valid UserIdSi si);

    /**
     * 从安全上下文中获取当前用户，调用必须保证当前用户已登录，否则抛出 ServiceException
     *
     * @return 当前用户 ID
     */
    @NotNull
    Long currentUser();

    /**
     * 用户定位
     */
    void position(@NotNull @Valid PositionSi si);

    /**
     * 获取某用户的用户信息
     *
     * @param userId 需要获取用户的用户 ID
     * @return SO
     */
    @NotNull
    @Valid
    InfoSo info(@UserId Long userId);

    /**
     * 更新用户信息
     *
     * @param si SI
     */
    void updateInfo(@NotNull @Valid UpdateInfoSi si);

    /**
     * 批量获取其他用户信息
     *
     * @param si SI List
     * @return SO List
     */
    @NotNull
    @Valid
    List<OthersInfoBatchSo> othersInfoBatch(@BatchInterfaceI @Valid List<OthersInfoBatchSi> si);
}
