package com.seliote.fr.model.si.goods;

import com.seliote.fr.annotation.validation.GoodsId;
import com.seliote.fr.annotation.validation.JumpFrom;
import com.seliote.fr.annotation.validation.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 浏览记录 SI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBrowseRecordSi {

    // 用户 ID
    @UserId
    private Long userId;

    // 商品 ID
    @GoodsId
    private Long goodsId;

    // 跳转来源，即从哪里点击获取商品信息页的
    @JumpFrom
    private Integer jumpFrom;
}
