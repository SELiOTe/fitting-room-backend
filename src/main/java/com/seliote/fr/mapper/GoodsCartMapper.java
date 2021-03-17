package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.goodscart.InsertMi;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 购物车 Mapper
 *
 * @author seliote
 */
public interface GoodsCartMapper {

    /**
     * 新增购物车数据
     *
     * @param mi MI
     */
    void insert(@NotNull @Valid @Param("mi") InsertMi mi);
}
