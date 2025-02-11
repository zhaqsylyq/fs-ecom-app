package com.zhaqsylyq.ecom.order.application;

import com.zhaqsylyq.ecom.order.domain.order.aggregate.*;
import com.zhaqsylyq.ecom.order.domain.order.repository.OrderRepository;
import com.zhaqsylyq.ecom.order.domain.order.service.CartReader;
import com.zhaqsylyq.ecom.order.domain.order.service.OrderCreator;
import com.zhaqsylyq.ecom.order.domain.order.service.OrderReader;
import com.zhaqsylyq.ecom.order.domain.order.service.OrderUpdater;
import com.zhaqsylyq.ecom.order.domain.order.vo.StripeSessionId;
import com.zhaqsylyq.ecom.order.domain.user.aggregate.User;
import com.zhaqsylyq.ecom.order.infrastructure.secondary.service.stripe.StripeService;
import com.zhaqsylyq.ecom.product.application.ProductsApplicationService;
import com.zhaqsylyq.ecom.product.domain.aggregate.Product;
import com.zhaqsylyq.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

  private final ProductsApplicationService productsApplicationService;
  private final CartReader cartReader;
  private final UsersApplicationService usersApplicationService;
  private final OrderCreator orderCreator;
  private final OrderUpdater orderUpdater;
  private final OrderReader orderReader;

  public OrderApplicationService(ProductsApplicationService productsApplicationService,
                                 UsersApplicationService usersApplicationService,
                                 OrderRepository orderRepository,
                                 StripeService stripeService) {
    this.productsApplicationService = productsApplicationService;
    this.usersApplicationService = usersApplicationService;
    this.cartReader = new CartReader();
    this.orderCreator = new OrderCreator(orderRepository, stripeService);
    this.orderUpdater = new OrderUpdater(orderRepository);
    this.orderReader = new OrderReader(orderRepository);
  }

  @Transactional(readOnly = true)
  public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
    List<PublicId> publicIds = detailCartRequest.items().stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return cartReader.getDetails(productsInformation);
  }

  @Transactional
  public StripeSessionId createOrder(List<DetailCartItemRequest> items) {
    User authenticatedUser = usersApplicationService.getAuthenticatedUser();
    List<PublicId> publicIds = items.stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return orderCreator.create(productsInformation, items, authenticatedUser);
  }

  @Transactional
  public void updateOrder(StripeSessionInformation stripeSessionInformation) {
    List<OrderedProduct> orderedProducts = this.orderUpdater.updateOrderFromStripe(stripeSessionInformation);
    List<OrderProductQuantity> orderProductQuantities = this.orderUpdater.computeQuantity(orderedProducts);
    this.productsApplicationService.updateProductQuantity(orderProductQuantities);
    this.usersApplicationService.updateAddress(stripeSessionInformation.userAddress());
  }

  @Transactional(readOnly = true)
  public Page<Order> findOrdersForConnectedUser(Pageable pageable) {
    User authenticatedUser = usersApplicationService.getAuthenticatedUser();
    return orderReader.findAllByUserPublicId(authenticatedUser.getUserPublicId(), pageable);
  }

  @Transactional(readOnly = true)
  public Page<Order> findOrdersForAdmin(Pageable pageable) {
    return orderReader.findAll(pageable);
  }
}
