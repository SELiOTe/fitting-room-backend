package com.seliote.fr.model.ci.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.ColorId;
import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.annotation.validation.SizeId;
import lombok.Data;

/**
 * 购物车添加 CI
 *
 * @author seliote
 */
@Data
public class AddCi {

    // 商品 ID
    @JsonProperty("goods_id")
    @GoodsId
    private Long goodsId;

    // 商品颜色 ID
    @JsonProperty("color_id")
    @ColorId
    private Long colorId;

    // 商品尺码 ID
    @JsonProperty("size_id")
    @SizeId
    private Long sizeId;
}
