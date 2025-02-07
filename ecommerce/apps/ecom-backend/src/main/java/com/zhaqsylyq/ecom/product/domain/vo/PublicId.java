package com.zhaqsylyq.ecom.product.domain.vo;

import com.zhaqsylyq.ecom.shared.error.domain.Assert;

import java.util.UUID;

public record PublicId(UUID value) {

  public PublicId {
    Assert.notNull("value", value);
  }
}
