package com.seliote.fr.model.co.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.Sha1;
import com.seliote.fr.annotation.validation.Gender;
import com.seliote.fr.annotation.validation.Nickname;
import com.seliote.fr.annotation.validation.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改个人信息 CO
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoCo {
    // 用户 ID
    @JsonProperty("id")
    @UserId
    private Long id;

    // 用户昵称
    @JsonProperty("nickname")
    @Nickname
    private String nickname;

    // 用户性别，0 为未知，1 为男，2 为女
    @JsonProperty("gender")
    @Gender
    private Short gender;

    // 用户头像文件 SHA1
    @JsonProperty("avatar")
    @Sha1
    private String avatar;
}
