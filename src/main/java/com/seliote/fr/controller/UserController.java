package com.seliote.fr.controller;

import com.seliote.fr.annotation.ApiFrequency;
import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.annotation.validation.BatchInterfaceI;
import com.seliote.fr.config.api.ApiFrequencyType;
import com.seliote.fr.exception.ApiException;
import com.seliote.fr.model.ci.user.*;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.model.co.user.*;
import com.seliote.fr.model.si.user.LoginSi;
import com.seliote.fr.model.si.user.OthersInfoBatchSi;
import com.seliote.fr.model.si.user.PositionSi;
import com.seliote.fr.model.si.user.UpdateInfoSi;
import com.seliote.fr.service.UserService;
import com.seliote.fr.util.ReflectUtils;
import com.seliote.fr.util.TextUtils;
import com.seliote.fr.util.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 用户帐户 Controller
 *
 * @author seliote
 */
@Log4j2
@Validated
@ApiController
@RequestMapping(value = "user", method = {RequestMethod.POST})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 登录用户帐户，未注册的账户将会自动注册
     *
     * @param ci CI
     * @return CO
     */
    @ApiFrequency(type = ApiFrequencyType.ARG, key = "countryCode&&telNo")
    @RequestMapping("login")
    @ResponseBody
    @NotNull
    @Valid
    public Co<LoginCo> login(@Valid @RequestBody LoginCi ci) {
        var so = userService.login(ReflectUtils.copy(ci, LoginSi.class));
        if (so.getLoginResult() == 0 || so.getLoginResult() == 1) {
            return Co.cco(ReflectUtils.copy(so, LoginCo.class));
        } else {
            log.error("login for: {}, service return: {}", ci, so);
            throw new ApiException("service return value error");
        }
    }

    /**
     * 用户定位
     *
     * @param ci CI
     * @return CO
     */
    @ApiFrequency(frequency = 30)
    @RequestMapping("position")
    @ResponseBody
    @NotNull
    @Valid
    public Co<Void> position(@Valid @RequestBody PositionCi ci) {
        var currentUser = userService.currentUser();
        var positionSi = ReflectUtils.copy(ci, PositionSi.class);
        positionSi.setUserId(currentUser);
        userService.position(positionSi);
        return Co.cco(null);
    }

    /**
     * 获取用户自身信息
     *
     * @return CO
     */
    @ApiFrequency(frequency = 10)
    @RequestMapping("info")
    @ResponseBody
    @NotNull
    @Valid
    public Co<InfoCo> info() {
        var currentUser = userService.currentUser();
        var so = userService.info(currentUser);
        return Co.cco(ReflectUtils.copy(so, InfoCo.class));
    }

    /**
     * 修改个人信息
     *
     * @param ci CI
     * @return CO
     */
    @ApiFrequency(frequency = 10)
    @RequestMapping("update_info")
    @ResponseBody
    @NotNull
    @Valid
    public Co<UpdateInfoCo> updateInfo(@Valid @RequestBody UpdateInfoCi ci) {
        var currentUser = userService.currentUser();
        var si = new UpdateInfoSi();
        si.setId(currentUser);
        si.setAvatar(TextUtils.base64Decode(ci.getAvatar()));
        si.setNickname(ci.getNickname());
        si.setGender(ci.getGender());
        userService.updateInfo(si);
        var so = userService.info(currentUser);
        return Co.cco(ReflectUtils.copy(so, UpdateInfoCo.class));
    }


    /**
     * 获取其他用户信息
     *
     * @return CO
     */
    @ApiFrequency(frequency = 60)
    @RequestMapping("others_info")
    @ResponseBody
    @NotNull
    @Valid
    public Co<OthersInfoCo> othersInfo(@Valid @RequestBody OthersInfoCi ci) {
        var so = userService.info(ci.getId());
        return Co.cco(ReflectUtils.copy(so, OthersInfoCo.class));
    }

    /**
     * 批量获取获取其他用户信息
     *
     * @param ci CI List
     * @return CO
     */
    @ApiFrequency(frequency = 60)
    @RequestMapping("others_info_batch")
    @ResponseBody
    @NotNull
    @Valid
    public Co<List<OthersInfoBatchCo>> othersInfoBatch(@BatchInterfaceI @Valid @RequestBody List<OthersInfoBatchCi> ci) {
        var si = ReflectUtils.copy(ci, new TypeReference<List<OthersInfoBatchSi>>() {
        });
        var so = userService.othersInfoBatch(si);
        var co = ReflectUtils.copy(so, new TypeReference<List<OthersInfoBatchCo>>() {
        });
        return Co.cco(co);
    }
}
