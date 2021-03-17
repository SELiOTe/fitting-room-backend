package com.seliote.fr.service;

import com.seliote.fr.model.PageO;
import com.seliote.fr.model.si.goods.AddBrowseRecordSi;
import com.seliote.fr.model.si.goods.CommentSi;
import com.seliote.fr.model.si.goods.InfoSi;
import com.seliote.fr.model.si.goods.SkuIdSi;
import com.seliote.fr.model.so.goods.CommentSo;
import com.seliote.fr.model.so.goods.InfoSo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * 商品 Service
 *
 * @author seliote
 */
@Validated
public interface GoodsService {

    /**
     * 商品信息
     *
     * @param si SI
     * @return SO
     */
    @Valid
    InfoSo info(@NotNull @Valid InfoSi si);

    /**
     * 新增商品浏览记录
     *
     * @param si si
     */
    void addBrowseRecord(@NotNull @Valid AddBrowseRecordSi si);

    /**
     * 商品评论
     * order_by 排序方式：
     * 1: goods_comment.created_at DESC
     *
     * @param si SI
     * @return PageO SO
     */
    @NotNull
    @Valid
    PageO<CommentSo> comment(@NotNull @Valid CommentSi si);

    /**
     * 获取商品的 SKU ID
     *
     * @param si SI
     * @return 商品存在时返回相应 ID，否则返回 null
     */
    @NotNull
    Optional<Long> skuId(@NotNull @Valid SkuIdSi si);
}
