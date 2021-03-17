package com.seliote.fr.model.mi.goods;

import com.seliote.fr.annotation.validation.GoodsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主要信息 MI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectInfoMi {
    // 商品 ID
    @GoodsId
    private Long id;
}
