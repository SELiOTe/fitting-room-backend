package com.seliote.fr.model.mi.goodsbrowserecord;

import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.annotation.validation.JumpFrom;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

/**
 * 新增浏览记录 MI
 *
 * @author seliote
 */
@Data
public class InsertMi {

    // 用户 ID
    @UserId
    private Long userId;

    // 商品 ID
    @GoodsId
    private Long goodsId;

    // 跳转来源，即从哪里点击获取商品信息页的
    @JumpFrom
    private Integer jumpFrom;
}
