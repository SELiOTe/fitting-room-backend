package com.seliote.fr.model.si.user;

import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * 用户定位 SI
 *
 * @author seliote
 */
@Data
public class PositionSi {

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
