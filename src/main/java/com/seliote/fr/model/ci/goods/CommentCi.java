package com.seliote.fr.model.ci.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.model.SortablePageI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 商品评论 CI
 *
 * @author seliote
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentCi extends SortablePageI {

    // 商品 ID
    @JsonProperty("id")
    @GoodsId
    private Long id;
}
