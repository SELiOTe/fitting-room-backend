package com.seliote.fr.model.si.goods;

import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.model.SortablePageI;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品评论 SI
 *
 * @author seliote
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentSi extends SortablePageI {

    // 商品 ID
    @GoodsId
    private Long id;
}
