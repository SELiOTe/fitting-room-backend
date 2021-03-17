package com.seliote.fr.model.co.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 购物车添加 CO
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCo {

    // 添加状态，0 为成功，1 为商品不存在
    @JsonProperty("status")
    @NotNull
    private Integer status;
}
