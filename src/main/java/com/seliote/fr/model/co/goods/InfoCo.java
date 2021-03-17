package com.seliote.fr.model.co.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.*;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品信息 CO
 *
 * @author seliote
 */
@Data
public class InfoCo {
    // 商品 ID
    @JsonProperty("id")
    @GoodsId
    private Long id;

    // 商品名称
    @JsonProperty("name")
    @GoodsName
    private String name;

    // 商品出厂价，单位为分
    @JsonProperty("price")
    @Price
    private Integer price;

    // 商品售价计算方式
    @JsonProperty("list_price_cal_method")
    @ListPriceMethod
    private Integer listPriceMethod;

    // 商品图片
    @JsonProperty("images")
    @GoodsImageList
    @Valid
    private List<InfoImageCo> images;

    // 商品 SKU
    @JsonProperty("skus")
    @GoodsSkuList
    @Valid
    private List<InfoSkuCo> skus;
}
