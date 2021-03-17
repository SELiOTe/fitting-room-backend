package com.seliote.fr.model.ci.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.fr.annotation.validation.UserId;
import lombok.Data;

import java.util.List;

/**
 * 获取其他用户信息 CI
 *
 * @author seliote
 */
@Data
public class OthersInfoBatchCi {

    // 用户 ID
    @JsonProperty("id")
    @UserId
    private Long id;
}
