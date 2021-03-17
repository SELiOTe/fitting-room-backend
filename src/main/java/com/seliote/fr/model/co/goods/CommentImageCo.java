package com.seliote.fr.model.co.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.GoodsCommentImageOrder;
import com.seliote.fr.annotation.validation.Sha1;
import lombok.Data;

/**
 * 评论图片 CO
 *
 * @author seliote
 */
@Data
public class CommentImageCo {

    // 图片 SHA1
    @JsonProperty("image")
    @Sha1
    private String image;

    // 商品评论图片排序
    @JsonProperty("image_order")
    @GoodsCommentImageOrder
    private Integer imageOrder;
}
