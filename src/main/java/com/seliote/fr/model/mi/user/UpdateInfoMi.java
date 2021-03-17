package com.seliote.fr.model.mi.user;

import com.seliote.fr.annotation.validation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 批量获取用户信息 MI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoMi {

    // 用户 ID
    @UserId
    private Long id;

    // 用户昵称
    @Nickname
    private String nickname;

    // 用户性别
    @Gender
    private Short gender;
}
