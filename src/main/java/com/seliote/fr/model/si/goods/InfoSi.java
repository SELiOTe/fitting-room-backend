package com.seliote.fr.model.si.goods;

import com.seliote.fr.annotation.validation.GoodsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主要信息 SI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoSi {
    // 商品 ID
    @GoodsId
    private Long id;
}
