package com.seliote.fr.model.mi.user;

import com.seliote.fr.annotation.validation.UserId;
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
public class SelectInfoBatchMi {

    // 用户 ID
    @UserId
    private Long id;
}
