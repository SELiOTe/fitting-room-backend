package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.userposition.InsertMi;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户定位 Mapper
 *
 * @author seliote
 */
public interface UserPositionMapper {

    /**
     * 插入一条用户定位数据
     *
     * @param mi MI
     */
    void insert(@NotNull @Valid @Param("mi") InsertMi mi);
}
