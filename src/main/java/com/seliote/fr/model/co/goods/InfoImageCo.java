package com.seliote.fr.model.co.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.GoodsImageOrder;
import com.seliote.fr.annotation.validation.Sha1;
import lombok.Data;

/**
 * 商品信息图片 CO
 *
 * @author seliote
 */
@Data
public class InfoImageCo {

    // 商品图片 SHA1
    @JsonProperty("image")
    @Sha1
    private String image;

    // 商品图片顺序
    @JsonProperty("image_order")
    @GoodsImageOrder
    private Integer imageOrder;
}
