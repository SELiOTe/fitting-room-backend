package com.seliote.fr.model.so.goods;

import com.seliote.fr.annotation.validation.*;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品信息 SO
 *
 * @author seliote
 */
@Data
public class InfoSo {

    // 商品 ID
    @GoodsId
    private Long id;

    // 商品名称
    @GoodsName
    private String name;

    // 商品出厂价，单位为分
    @Price
    private Integer price;

    // 商品售价计算方式
    @ListPriceMethod
    private Integer listPriceMethod;

    // 商品图片
    @GoodsImageList
    @Valid
    private List<InfoImageSo> images;

    // 商品 SKU
    @GoodsSkuList
    @Valid
    private List<InfoSkuSo> skus;
}
