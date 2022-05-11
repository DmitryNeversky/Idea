package org.dneversky.kitchen.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.kitchen.repository.OrderRepository;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Order {

    private OrderRepository orderRepository;

    @Id
    private Long id;

    private State state;

    @DocumentReference
    private Consumer consumer;

    @DBRef
    private List<MenuItem> menuItemList = new ArrayList<>();

    public Order(State state, Consumer consumer, List<MenuItem> menuItemList) {
        this.state = state;
        this.consumer = consumer;
        this.menuItemList = menuItemList;
    }

    public Order createOrder(Consumer consumer, List<MenuItem> menuItemList) {
        Order order = new Order(State.PENDING, consumer, menuItemList);

        return orderRepository.save(order);
    }
}
