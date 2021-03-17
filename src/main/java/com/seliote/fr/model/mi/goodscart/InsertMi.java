package com.seliote.fr.model.mi.goodscart;

import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.annotation.validation.SkuId;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

/**
 * 新增购物车数据 MI
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

    // SKU ID
    @SkuId
    private Long skuId;
}
