package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.useravatar.InsertMi;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户头像 Mapper
 *
 * @author seliote
 */
@Validated
public interface UserAvatarMapper {

    /**
     * 新增头像
     *
     * @param mi MI
     */
    void insert(@NotNull @Valid @Param("mi") InsertMi mi);
}
