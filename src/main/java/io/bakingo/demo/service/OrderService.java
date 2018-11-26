package io.bakingo.demo.service;

import io.bakingo.demo.model.Item;
import io.bakingo.demo.model.Order;
import io.bakingo.demo.model.OrderDetail;
import io.bakingo.demo.model.User;
import io.bakingo.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository statusRepository;
    private final OrderDetailRepository detailRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository,
                        @Qualifier("orderStatusRepository") OrderStatusRepository statusRepository,
                        @Qualifier("orderDetailRepository") OrderDetailRepository detailRepository,
                        @Qualifier("userRepository") UserRepository userRepository,
                        @Qualifier("itemRepository") ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.detailRepository = detailRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByStatus(String status) {
        return orderRepository.findAll()
                .stream().filter(x -> x.getStatus() == statusRepository.findByName(status)).collect(Collectors.toList());
    }

    public List<Order> findByStatus(String status, Integer userId) {
        return orderRepository.findAll().stream()
                .filter(x -> (int) x.getUser().getId() == userId)
                .filter(x -> x.getStatus() == statusRepository.findByName(status))
                .collect(Collectors.toList());
    }

    public Order findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    public Order findById(Integer id, User user) {
        Order item = orderRepository.findById(id).get();
        if ((int) item.getUser().getId() != user.getId()) throw new AccessDeniedException("Denied access!! Hacker detected!?");
        return item;
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findAll().stream()
                .filter(x -> (int) x.getUser().getId() == user.getId())
                .collect(Collectors.toList());
    }

    public Order save(User user, Integer[] itemId, Integer[] amount) {
        // begin init order and set default value in case of notnull
        Order order = new Order();
        order.setUser(userRepository.findByEmail(user.getEmail()));
        order.setStatus(statusRepository.findByName("Pending"));
        order.setAddress(user.getAddress());
        order.setPhone(user.getPhone());
        order.setTotalItems(0);
        order.setTotalPrice(0);

        // require timestamp
        Timestamp timestamp = new Timestamp((new Date().getTime()));
        order.setCreated(timestamp);
        order.setUpdated(timestamp);
        orderRepository.save(order);

        int n = amount.length;
        int totalItems = 0;
        int totalPrice = 0;
        Set<OrderDetail> details = new HashSet<>();
        for (int i = 0; i < n; i++) {
            // checking item_id and amount
            if (amount[i] == 0) continue;
            Optional<Item> get = itemRepository.findById(itemId[i]);
            if (!get.isPresent()) continue;
            Item item = get.get();

            // begin transaction for OrderDetail
            OrderDetail detail = new OrderDetail();
            detail.setItem(item);
            detail.setAmount(amount[i]);
            detail.setOrder(order);
            detail.setPrice(item.getSellPrice());
            detailRepository.save(detail);
            details.add(detail);

            // update totalItems & totalPrice
            totalItems += detail.getAmount();
            totalPrice += detail.getSumPrice();
        }

        order.setDetails(details);
        order.setTotalItems(totalItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return order;
    }

    public Order updateStatus(Integer id, String statusName) {
        Order order = orderRepository.findById(id).get();
        Timestamp timestamp = new Timestamp((new Date().getTime()));
        order.setStatus(statusRepository.findByName(statusName));
        order.setUpdated(timestamp);
        orderRepository.save(order);
        return order;
    }
}
