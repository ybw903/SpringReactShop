package com.springreactshop.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="DELIVERY_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Delivery delivery;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "PAYMENT_ID")
//    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    private Date orderDate;

    public void setStatus(OrderStatus Status) {this.status = Status;}
    public void setTotalPrice(int totalPrice) {this.totalPrice = totalPrice;}
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    //==연관관계 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메소드==//
    public static Order createOrder(Member member,Delivery delivery, List<OrderProduct> orderProducts) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        int totalPrice = 0;
        for(OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
            totalPrice += orderProduct.getTotalPrice();
        }

        order.setStatus(OrderStatus.ORDER);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        return order;
    }

    //==비즈니스 로직==//
    /**주문 취소*/
    public void cancel() {
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderProduct orderProduct : orderProducts) {
            orderProduct.cancel();
        }
    }

}
