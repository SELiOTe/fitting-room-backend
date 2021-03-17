package com.seliote.fr.mapper;

import com.seliote.fr.model.PageO;
import com.seliote.fr.model.mi.goodscomment.SelectCommentSortablePageMi;
import com.seliote.fr.model.mo.goodscomment.SelectCommentSortablePageMo;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品评论 Mapper
 *
 * @author seliote
 */
@Validated
public interface GoodsCommentMapper {

    /**
     * 获取评论
     * order_by 排序方式：
     * 1: goods_comment.created_at DESC
     *
     * @param mi MI
     * @return PageO MO
     */
    @NotNull
    @Valid
    PageO<SelectCommentSortablePageMo> selectCommentSortablePage(
            @NotNull @Valid @Param("mi") SelectCommentSortablePageMi mi);
}
