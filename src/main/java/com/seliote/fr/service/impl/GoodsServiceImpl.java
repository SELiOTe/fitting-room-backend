package com.seliote.fr.service.impl;

import com.seliote.fr.exception.OrderByException;
import com.seliote.fr.mapper.GoodsBrowseRecordMapper;
import com.seliote.fr.mapper.GoodsCommentMapper;
import com.seliote.fr.mapper.GoodsMapper;
import com.seliote.fr.model.PageO;
import com.seliote.fr.model.mi.goods.SelectInfoMi;
import com.seliote.fr.model.mi.goods.SelectSkuIdMi;
import com.seliote.fr.model.mi.goodsbrowserecord.InsertMi;
import com.seliote.fr.model.mi.goodscomment.SelectCommentSortablePageMi;
import com.seliote.fr.model.si.goods.AddBrowseRecordSi;
import com.seliote.fr.model.si.goods.CommentSi;
import com.seliote.fr.model.si.goods.InfoSi;
import com.seliote.fr.model.si.goods.SkuIdSi;
import com.seliote.fr.model.so.goods.CommentSo;
import com.seliote.fr.model.so.goods.InfoSo;
import com.seliote.fr.service.GoodsService;
import com.seliote.fr.util.ReflectUtils;
import com.seliote.fr.util.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 商品 Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;
    private final GoodsBrowseRecordMapper goodsBrowseRecordMapper;
    private final GoodsCommentMapper goodsCommentMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper,
                            GoodsBrowseRecordMapper goodsBrowseRecordMapper,
                            GoodsCommentMapper goodsCommentMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsBrowseRecordMapper = goodsBrowseRecordMapper;
        this.goodsCommentMapper = goodsCommentMapper;
        log.debug("Construct {}", getClassName(GoodsServiceImpl.class));
    }

    @Override
    public InfoSo info(InfoSi si) {
        var mo = goodsMapper.selectInfo(ReflectUtils.copy(si, SelectInfoMi.class));
        log.info("Query info for: {}", si);
        return ReflectUtils.copy(mo, InfoSo.class);
    }

    @Override
    @Transactional
    public void addBrowseRecord(AddBrowseRecordSi si) {
        goodsBrowseRecordMapper.insert(ReflectUtils.copy(si, InsertMi.class));
    }

    @Override
    public PageO<CommentSo> comment(CommentSi si) {
        // 1 是 goods_comment.created_at DESC
        Set<Integer> allowedOrderBy = new HashSet<>(Collections.singletonList(1));
        if (!allowedOrderBy.contains(si.getOrderBy())) {
            log.error("Error order by clause for: {}", si);
            throw new OrderByException("Error order by: " + si.getOrderBy());
        }
        var mi = ReflectUtils.copy(si, SelectCommentSortablePageMi.class);
        var mo = goodsCommentMapper.selectCommentSortablePage(mi);
        // 需要为 PageO 提供类型捕获
        var so = new PageO<CommentSo>();
        ReflectUtils.copy(mo, so, new TypeReference<CommentSo>() {
        });
        return so;
    }

    @Override
    @Transactional
    public Optional<Long> skuId(SkuIdSi si) {
        var id = goodsMapper.selectSkuIdMi(ReflectUtils.copy(si, SelectSkuIdMi.class));
        return Optional.ofNullable(id);
    }
}
