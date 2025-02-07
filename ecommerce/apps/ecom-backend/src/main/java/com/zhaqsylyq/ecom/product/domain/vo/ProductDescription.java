package com.zhaqsylyq.ecom.product.domain.vo;

import com.zhaqsylyq.ecom.shared.error.domain.Assert;

public record ProductDescription(String value) {

  public ProductDescription {
    Assert.field("value", value).notNull().minLength(10);
  }
}
