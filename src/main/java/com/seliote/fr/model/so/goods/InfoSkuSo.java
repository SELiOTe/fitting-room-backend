package com.seliote.fr.model.so.goods;

import com.seliote.fr.annotation.validation.ColorId;
import com.seliote.fr.annotation.validation.SizeId;
import com.seliote.fr.annotation.validation.Stock;
import lombok.Data;

/**
 * 商品信息 SKU SO
 *
 * @author seliote
 */
@Data
public class InfoSkuSo {

    // 商品颜色 ID
    @ColorId
    private Long colorId;

    // 商品尺码 ID
    @SizeId
    private Long sizeId;

    // 商品库存
    @Stock
    private Integer stock;
}
