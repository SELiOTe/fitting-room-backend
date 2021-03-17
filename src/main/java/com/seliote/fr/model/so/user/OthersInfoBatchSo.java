package com.seliote.fr.model.so.user;

import com.seliote.fr.annotation.validation.Gender;
import com.seliote.fr.annotation.validation.Nickname;
import com.seliote.fr.annotation.validation.Sha1;
import com.seliote.fr.annotation.validation.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 批量获取用户信息 SO
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OthersInfoBatchSo {

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
