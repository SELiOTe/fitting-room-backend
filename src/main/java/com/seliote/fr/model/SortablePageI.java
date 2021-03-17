package com.seliote.fr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 可排序的分页查询输入
 *
 * @author seliote
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SortablePageI extends PageI {

    // 排序方式，各接口中预先定义，SQL 中进行 switch 选择，接口中会进行校验
    // 传输错误数值时接口将返回 order by 异常
    @JsonProperty("order_by")
    @NotNull
    private Integer orderBy;
}
