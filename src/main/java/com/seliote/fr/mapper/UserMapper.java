package com.seliote.fr.mapper;

import com.seliote.fr.annotation.validation.UserId;
import com.seliote.fr.model.mi.user.*;
import com.seliote.fr.model.mo.user.SelectInfoBatchMo;
import com.seliote.fr.model.mo.user.InfoMo;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户 Mapper
 *
 * @author seliote
 */
@Validated
public interface UserMapper {

    /**
     * 插入一条用户数据
     *
     * @param mi MI
     */
    void insert(@NotNull @Valid @Param("mi") InsertMi mi);

    /**
     * 获取指定手机号码的注册数量
     *
     * @param mi MI
     * @return 注册数量
     */
    @NotNull
    Long countUser(@NotNull @Valid @Param("mi") CountUserMi mi);

    /**
     * 通过手机号码获取用户 ID
     *
     * @param mi MI
     * @return 存在用户信息时返回 ID，否则返回 null
     */
    @NotNull
    Long selectId(@NotNull @Valid @Param("mi") SelectIdMi mi);

    /**
     * 获取某用户的用户信息 MO
     *
     * @param userId 用户 ID
     * @return MO
     */
    @NotNull
    @Valid
    InfoMo selectInfo(@UserId @Param("userId") Long userId);

    /**
     * 修改个人信息（只涉及 user 表部分）
     *
     * @param mi MI
     */
    void updateInfo(@NotNull @Valid @Param("mi") UpdateInfoMi mi);

    /**
     * 批量获取用户信息
     * @param mi MI List
     * @return MO List
     */
    @NotNull
    @Valid
    List<SelectInfoBatchMo> selectBatchInfo(@NotNull @Valid @Param("mi")List<SelectInfoBatchMi> mi);
}
