package com.seliote.fr.model.mi.userposition;

import com.seliote.fr.annotation.validation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * 插入一条用户定位数据 MI
 *
 * @author seliote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertMi {

    // 用户 ID
    @UserId
    private Long userId;

    // 经度
    @DecimalMin("-180")
    @DecimalMax("180")
    private Double longitude;

    // 纬度
    @DecimalMin("-90")
    @DecimalMax("90")
    private Double latitude;
}
