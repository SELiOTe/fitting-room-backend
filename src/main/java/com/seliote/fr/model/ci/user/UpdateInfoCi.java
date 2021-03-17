package com.seliote.fr.model.ci.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.AvatarBase64;
import com.seliote.fr.annotation.validation.Gender;
import com.seliote.fr.annotation.validation.Nickname;
import lombok.Data;

/**
 * 修改个人信息 CI
 *
 * @author seliote
 */
@Data
public class UpdateInfoCi {

    // 用户头像 Base 64
    @JsonProperty("avatar")
    @AvatarBase64
    private String avatar;

    // 用户昵称
    @JsonProperty("nickname")
    @Nickname
    private String nickname;

    // 用户性别，0 为未知，1 为男，2 为女
    @JsonProperty("gender")
    @Gender
    private Short gender;
}
