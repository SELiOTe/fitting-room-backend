package com.seliote.fr.model.ci.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * 用户定位 CI
 *
 * @author seliote
 */
@Data
public class PositionCi {

    // 经度
    @JsonProperty("longitude")
    @DecimalMin("-180")
    @DecimalMax("180")
    private Double longitude;

    // 纬度
    @JsonProperty("latitude")
    @DecimalMin("-90")
    @DecimalMax("90")
    private Double latitude;
}
