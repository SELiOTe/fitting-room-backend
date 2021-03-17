package com.seliote.fr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 分页查询输出，所有分页查询的结果均为该对象
 * 需要注意的是如果数据库空没有相应的业务数据时
 * 也不能返回 null，实际的分页相关参数仍需返回 page 对象置空 List
 *
 * @author seliote
 */
@Data
public class PageO<T> {

    // 页码
    @JsonProperty("page_number")
    @NotNull
    @Min(0)
    private Integer pageNumber;

    // 页大小
    @JsonProperty("page_size")
    @NotNull
    @Min(1)
    @Max(500)
    private Integer pageSize;

    // 数据库中数据总量
    @JsonProperty("total_count")
    @NotNull
    @Min(0)
    private Long totalCount;

    // 实际页数据
    @JsonProperty("page")
    @NotNull
    @Size(max = 500)
    @Valid
    private List<T> page;
}
