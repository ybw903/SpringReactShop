package com.springreactshop.demo.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
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
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    public void setStatus(OrderStatus Status) {this.status = Status;}
    public void setTotalPrice(int totalPrice) {this.totalPrice = totalPrice;}
    public void setPayment(Payment payment) {this.payment = payment;}


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
    public static Order createOrder(Member member,Delivery delivery, List<OrderProduct> orderProducts, Payment payment) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setPayment(payment);

        int totalPrice = 0;
        for(OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
            totalPrice += orderProduct.getTotalPrice();
        }

        order.setStatus(OrderStatus.ORDER);
        order.setTotalPrice(totalPrice);
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
