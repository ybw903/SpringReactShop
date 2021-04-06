package com.springreactshop.demo.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public void setOrder(Order order) {this.order=order;}

    public void setAddress(Address address) {this.address = address;}

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {this.deliveryStatus = deliveryStatus;}

    //==생성 메소드==//
    public static Delivery createDelivery(Address address) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        return delivery;
    }
}
