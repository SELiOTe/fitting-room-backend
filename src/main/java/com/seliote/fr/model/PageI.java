package com.seliote.fr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页查询输入，所有分页查询的 I 均需继承该类
 *
 * @author seliote
 */
@Data
public abstract class PageI {

    // 页码，从 1 开始
    @JsonProperty("page_number")
    @NotNull
    @Min(1)
    private Integer pageNumber;

    // 页大小
    @JsonProperty("page_size")
    @NotNull
    @Min(1)
    @Max(500)
    private Integer pageSize;
}
