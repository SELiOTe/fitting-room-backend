package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.sms.InsertMi;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 短信 Mapper
 *
 * @author seliote
 */
@Validated
public interface SmsMapper {

    /**
     * 全量插入一条短信发送记录
     */
    void insert(@NotNull @Valid @Param("mi") InsertMi mi);
}
