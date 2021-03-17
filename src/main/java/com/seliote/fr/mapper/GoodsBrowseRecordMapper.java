package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.goodsbrowserecord.InsertMi;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品浏览记录 Mapper
 *
 * @author seliote
 */
@Valid
public interface GoodsBrowseRecordMapper {

    /**
     * 新增浏览记录
     *
     * @param mi 新增商品浏览记录
     */
    void insert(@NotNull @Valid @Param("mi") InsertMi mi);
}
