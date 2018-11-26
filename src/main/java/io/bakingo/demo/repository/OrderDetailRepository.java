package io.bakingo.demo.repository;

import io.bakingo.demo.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
