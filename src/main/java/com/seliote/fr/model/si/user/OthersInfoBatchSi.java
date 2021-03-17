package com.seliote.fr.model.si.user;

import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

/**
 * 批量获取用户信息 SI
 *
 * @author seliote
 */
@Data
public class OthersInfoBatchSi {

    // 用户 ID
    @UserId
    private Long id;
}
