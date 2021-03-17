package com.seliote.fr.model.ci.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

/**
 * 获取其他用户信息 CI
 *
 * @author seliote
 */
@Data
public class OthersInfoCi {

    // 用户 ID
    @JsonProperty("id")
    @UserId
    private Long id;
}
