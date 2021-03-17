package com.seliote.fr.model.ci.goods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.annotation.validation.JumpFrom;
import lombok.Data;

/**
 * 商品信息 CI
 *
 * @author seliote
 */
@Data
public class InfoCi {

    // 商品 ID
    @JsonProperty("id")
    @GoodsId
    private Long id;

    // 跳转来源，即从哪里点击获取商品信息页的
    @JsonProperty("jump_from")
    @JumpFrom
    private Integer jumpFrom;
}
