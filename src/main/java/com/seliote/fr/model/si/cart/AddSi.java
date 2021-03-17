package com.seliote.fr.model.si.cart;

import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.annotation.validation.SkuId;
import com.seliote.fr.annotation.validation.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增购物车数据 SI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSi {

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
