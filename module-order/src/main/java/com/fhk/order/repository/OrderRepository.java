package com.fhk.order.repository;

import com.fhk.core.constant.OrderStatus;
import com.fhk.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    public OrderEntity findByOrderNumber(String orderNumber);

    public List<OrderEntity> findByCustomerId(Long customerId);

    public List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);

    public List<OrderEntity> findByCustomerIdAndOrderStatus(Long customerId, OrderStatus orderStatus);


}
