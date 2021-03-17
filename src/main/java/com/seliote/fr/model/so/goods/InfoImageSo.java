package com.seliote.fr.model.so.goods;

import com.seliote.fr.annotation.validation.GoodsImageOrder;
import com.seliote.fr.annotation.validation.Sha1;
import lombok.Data;

/**
 * 商品信息图片 SO
 *
 * @author seliote
 */
@Data
public class InfoImageSo {

    // 商品图片
    @Sha1
    private String image;

    // 商品图片顺序
    @GoodsImageOrder
    private Integer imageOrder;
}
