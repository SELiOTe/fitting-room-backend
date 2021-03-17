package com.seliote.fr.model.mo.goodscomment;

import com.seliote.fr.annotation.validation.GoodsCommentImageOrder;
import com.seliote.fr.annotation.validation.Sha1;
import lombok.Data;

/**
 * 评论图片 MO 中
 *
 * @author seliote
 */
@Data
public class SelectCommentSortablePageImageMo {

    // 商品评论图片 SHA1
    @Sha1
    private String image;

    // 商品评论图片排序
    @GoodsCommentImageOrder
    private Integer imageOrder;
}
