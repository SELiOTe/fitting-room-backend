package com.seliote.fr.service.impl;

import com.seliote.fr.mapper.GoodsCartMapper;
import com.seliote.fr.model.mi.goodscart.InsertMi;
import com.seliote.fr.model.si.cart.AddSi;
import com.seliote.fr.service.CartService;
import com.seliote.fr.util.ReflectUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 购物车 Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class CartServiceImpl implements CartService {

    private final GoodsCartMapper goodsCartMapper;

    @Autowired
    public CartServiceImpl(GoodsCartMapper goodsCartMapper) {
        this.goodsCartMapper = goodsCartMapper;
        log.debug("Construct {}", getClassName(CartServiceImpl.class));
    }

    @Override
    public void add(@NotNull @Valid AddSi si) {
        goodsCartMapper.insert(ReflectUtils.copy(si, InsertMi.class));
    }
}
