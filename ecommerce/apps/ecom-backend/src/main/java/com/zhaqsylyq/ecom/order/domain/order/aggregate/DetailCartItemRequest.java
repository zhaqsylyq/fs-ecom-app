package com.zhaqsylyq.ecom.order.domain.order.aggregate;

import com.zhaqsylyq.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
