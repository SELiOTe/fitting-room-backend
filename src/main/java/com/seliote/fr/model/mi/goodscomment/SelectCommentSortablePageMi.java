package com.seliote.fr.model.mi.goodscomment;

import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.model.SortablePageI;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取评论 MI
 *
 * @author seliote
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SelectCommentSortablePageMi extends SortablePageI {

    // 商品 ID
    @GoodsId
    private Long id;
}
