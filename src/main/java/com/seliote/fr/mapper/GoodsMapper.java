package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.goods.SelectInfoMi;
import com.seliote.fr.model.mi.goods.SelectSkuIdMi;
import com.seliote.fr.model.mo.goods.InfoMo;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 商品 Mapper
 *
 * @author seliote
 */
@Validated
public interface GoodsMapper {

    /**
     * 商品信息
     *
     * @param mi MI
     * @return MO
     */
    @NotNull
    @Valid
    InfoMo selectInfo(@NotNull @Valid @Param("mi") SelectInfoMi mi);

    /**
     * 商品的 SKU
     *
     * @param mi MI
     * @return 商品的 SKU 或不存在时返回 null
     */
    Long selectSkuIdMi(@NotNull @Valid @Param("mi") SelectSkuIdMi mi);
}
