package com.zhaqsylyq.ecom.order.domain.order.vo;

import com.zhaqsylyq.ecom.shared.error.domain.Assert;

public record OrderPrice(double value) {

  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
