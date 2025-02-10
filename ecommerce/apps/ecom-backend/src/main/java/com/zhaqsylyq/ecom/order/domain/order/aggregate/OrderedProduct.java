package com.zhaqsylyq.ecom.order.domain.order.aggregate;

import com.zhaqsylyq.ecom.order.domain.order.vo.OrderPrice;
import com.zhaqsylyq.ecom.order.domain.order.vo.OrderQuantity;
import com.zhaqsylyq.ecom.order.domain.order.vo.ProductPublicId;
import com.zhaqsylyq.ecom.product.domain.aggregate.Product;
import com.zhaqsylyq.ecom.product.domain.vo.ProductName;
import org.jilt.Builder;

@Builder
public class OrderedProduct {

  private final ProductPublicId productPublicId;

  private final OrderPrice price;

  private final OrderQuantity quantity;

  private final ProductName productName;

  public OrderedProduct(ProductPublicId productPublicId, OrderPrice price, OrderQuantity quantity, ProductName productName) {
    this.productPublicId = productPublicId;
    this.price = price;
    this.quantity = quantity;
    this.productName = productName;
  }

  public ProductPublicId getProductPublicId() {
    return productPublicId;
  }

  public OrderPrice getPrice() {
    return price;
  }

  public OrderQuantity getQuantity() {
    return quantity;
  }

  public ProductName getProductName() {
    return productName;
  }

  public static OrderedProduct create(long quantity, Product product) {
    return OrderedProductBuilder.orderedProduct()
      .price(new OrderPrice(product.getPrice().value()))
      .quantity(new OrderQuantity(quantity))
      .productName(product.getName())
      .productPublicId(new ProductPublicId(product.getPublicId().value()))
      .build();
  }

}
