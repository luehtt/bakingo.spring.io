package io.bakingo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="item_id", nullable=false)
    private Item item;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Integer price;

    public Integer getSumPrice() {
        return amount * price;
    }

    public String getItemName() {
        return item.getName();
    }

    public Integer getItemId() {
        return item.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
