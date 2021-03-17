package com.seliote.fr.service;

import com.seliote.fr.model.si.cart.AddSi;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 购物车 Service
 *
 * @author seliote
 */
@Validated
public interface CartService {

    /**
     * 新增购物车记录
     *
     * @param si SI
     */
    void add(@NotNull @Valid AddSi si);
}
