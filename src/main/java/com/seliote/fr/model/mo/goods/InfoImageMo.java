package com.seliote.fr.model.mo.goods;

import com.seliote.fr.annotation.validation.GoodsImageOrder;
import com.seliote.fr.annotation.validation.Sha1;
import lombok.Data;

/**
 * 商品信息图片 MO
 *
 * @author seliote
 */
@Data
public class InfoImageMo {

    // 商品图片
    @Sha1
    private String image;

    // 商品图片顺序
    @GoodsImageOrder
    private Integer imageOrder;
}
