package com.seliote.fr.model.mo.user;

import com.seliote.fr.annotation.validation.Gender;
import com.seliote.fr.annotation.validation.Nickname;
import com.seliote.fr.annotation.validation.Sha1;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

/**
 * 获取某用户的用户信息 mo
 *
 * @author seliote
 */
@Data
public class InfoMo {

    // 用户 ID
    @UserId
    private Long id;

    // 用户昵称
    @Nickname
    private String nickname;

    // 用户性别
    @Gender
    private Short gender;

    // 用户头像文件 SHA1
    @Sha1
    private String avatar;
}
