package com.seliote.fr.model.co.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.ColorId;
import com.seliote.fr.annotation.validation.SizeId;
import com.seliote.fr.annotation.validation.Stock;
import lombok.Data;

/**
 * 商品信息 SKU CO
 *
 * @author seliote
 */
@Data
public class InfoSkuCo {

    // 商品颜色 ID
    @JsonProperty("color_id")
    @ColorId
    private Long colorId;

    // 商品尺码 ID
    @JsonProperty("size_id")
    @SizeId
    private Long sizeId;

    // 商品库存
    @JsonProperty("stock")
    @Stock
    private Integer stock;
}
