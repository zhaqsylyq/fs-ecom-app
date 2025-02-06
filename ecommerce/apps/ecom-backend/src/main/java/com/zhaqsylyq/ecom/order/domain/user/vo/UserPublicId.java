package com.zhaqsylyq.ecom.order.domain.user.vo;

import com.zhaqsylyq.ecom.shared.error.domain.Assert;

import java.util.UUID;

public record UserPublicId(UUID value) {

  public UserPublicId {
    Assert.notNull("value", value);
  }
}
