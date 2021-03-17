package com.seliote.fr.controller;

import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.model.PageO;
import com.seliote.fr.model.ci.goods.CommentCi;
import com.seliote.fr.model.ci.goods.InfoCi;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.model.co.goods.CommentCo;
import com.seliote.fr.model.co.goods.InfoCo;
import com.seliote.fr.model.si.goods.AddBrowseRecordSi;
import com.seliote.fr.model.si.goods.CommentSi;
import com.seliote.fr.model.si.goods.InfoSi;
import com.seliote.fr.service.GoodsService;
import com.seliote.fr.service.UserService;
import com.seliote.fr.util.ReflectUtils;
import com.seliote.fr.util.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 商品 Controller
 *
 * @author seliote
 */
@Log4j2
@Validated
@ApiController
@RequestMapping(value = "goods", method = RequestMethod.POST)
public class GoodsController {

    private final GoodsService goodsService;
    private final UserService userService;

    @Autowired
    public GoodsController(GoodsService goodsService,
                           UserService userService) {
        this.goodsService = goodsService;
        this.userService = userService;
        log.debug("Construct {}", getClassName(GoodsController.class));
    }

    /**
     * 商品信息
     *
     * @param ci CI
     * @return CO
     */
    @RequestMapping("info")
    @ResponseBody
    @NotNull
    @Valid
    public Co<InfoCo> info(@Valid @RequestBody InfoCi ci) {
        var so = goodsService.info(new InfoSi(ci.getId()));
        var si = new AddBrowseRecordSi(userService.currentUser(), ci.getId(), ci.getJumpFrom());
        goodsService.addBrowseRecord(si);
        return Co.cco(ReflectUtils.copy(so, InfoCo.class));
    }

    /**
     * 商品评论
     * order_by 排序方式：
     * 1: goods_comment.created_at DESC
     *
     * @param ci CI
     * @return PageO CO
     */
    @RequestMapping("comment")
    @ResponseBody
    @NotNull
    @Valid
    public Co<PageO<CommentCo>> comment(@Valid @RequestBody CommentCi ci) {
        var si = ReflectUtils.copy(ci, CommentSi.class);
        var so = goodsService.comment(si);
        var pageCo = new PageO<CommentCo>();
        ReflectUtils.copy(so, pageCo, new TypeReference<CommentCo>() {
        });
        return Co.cco(pageCo);
    }
}
