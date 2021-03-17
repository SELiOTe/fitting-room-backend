package com.seliote.fr.model.co.goods;

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
public class CommentCo {

    // 用户 ID
    @JsonProperty("user_id")
    @UserId
    private Long userId;

    // 用户评论创建时间
    @JsonProperty("created_at")
    @PastInstant
    private Instant createdAt;

    // 用户评论文本
    @JsonProperty("text")
    @GoodsCommentText
    private String text;

    // 商品评论图片列表
    @JsonProperty("images")
    @GoodsCommentImageList
    private List<CommentImageCo> images;
}
