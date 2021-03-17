package com.seliote.fr.model.si.user;

import com.seliote.fr.annotation.validation.AvatarBytes;
import com.seliote.fr.annotation.validation.Gender;
import com.seliote.fr.annotation.validation.Nickname;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

/**
 * 修改个人信息 SI
 *
 * @author seliote
 */
@Data
public class UpdateInfoSi {

    // 用户 ID
    @UserId
    private Long id;

    // 用户头像字节数组
    @AvatarBytes
    private byte[] avatar;

    // 用户昵称
    @Nickname
    private String nickname;

    // 用户性别
    @Gender
    private Short gender;
}
