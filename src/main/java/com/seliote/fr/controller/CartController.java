package com.seliote.fr.controller;

import com.seliote.fr.annotation.ApiFrequency;
import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.model.ci.cart.AddCi;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.model.co.cart.AddCo;
import com.seliote.fr.model.si.cart.AddSi;
import com.seliote.fr.model.si.goods.SkuIdSi;
import com.seliote.fr.service.CartService;
import com.seliote.fr.service.GoodsService;
import com.seliote.fr.service.UserService;
import com.seliote.fr.util.ReflectUtils;
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
 * 购物车 Controller
 *
 * @author seliote
 */
@Log4j2
@Validated
@ApiController
@RequestMapping(value = "cart", method = RequestMethod.POST)
public class CartController {

    private final GoodsService goodsService;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(GoodsService goodsService,
                          CartService cartService,
                          UserService userService) {
        this.goodsService = goodsService;
        this.cartService = cartService;
        this.userService = userService;
        log.debug("Construct {}", getClassName(CartController.class));
    }

    @ApiFrequency(frequency = 20)
    @RequestMapping("add")
    @ResponseBody
    @NotNull
    @Valid
    public Co<AddCo> add(@Valid @RequestBody AddCi ci) {
        var skuIdOptional = goodsService.skuId(ReflectUtils.copy(ci, SkuIdSi.class));
        if (skuIdOptional.isEmpty()) {
            log.warn("User select goods: {} not exists", ci);
            return Co.cco(new AddCo(1));
        }
        var skuId = skuIdOptional.get();
        var si = new AddSi();
        si.setUserId(userService.currentUser());
        si.setGoodsId(ci.getGoodsId());
        si.setSkuId(skuId);
        cartService.add(si);
        return Co.cco(new AddCo(0));
    }
}
