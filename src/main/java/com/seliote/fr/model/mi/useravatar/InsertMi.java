package com.seliote.fr.model.mi.useravatar;

import com.seliote.fr.annotation.validation.Sha1;
import com.seliote.fr.annotation.validation.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增用户头像 MI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMi {

    // 用户 ID
    @UserId
    private Long id;

    // 用户头像 SHA1
    @Sha1
    private String avatar;
}
