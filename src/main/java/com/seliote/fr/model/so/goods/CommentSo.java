package com.seliote.fr.model.so.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.GoodsCommentImageList;
import com.seliote.fr.annotation.validation.GoodsCommentText;
import com.seliote.fr.annotation.validation.PastInstant;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * 商品评论 CO
 *
 * @author seliote
 */
@Data
public class CommentSo {

    // 用户 ID
    @UserId
    private Long userId;

    // 用户评论创建时间
    @PastInstant
    private Instant createdAt;

    // 用户评论文本
    @GoodsCommentText
    private String text;

    // 商品评论图片列表
    @GoodsCommentImageList
    private List<CommentImageSo> images;
}
