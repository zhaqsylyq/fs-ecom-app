package com.zhaqsylyq.ecom.order.domain.order.vo;

import com.zhaqsylyq.ecom.shared.error.domain.Assert;

public record StripeSessionId(String value) {

  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
