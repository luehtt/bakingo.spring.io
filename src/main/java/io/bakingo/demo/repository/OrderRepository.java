package io.bakingo.demo.repository;

import io.bakingo.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
