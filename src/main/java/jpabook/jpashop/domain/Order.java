package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "orders")		// order는 예약어기때문에, orders를 관용적으로 사용.
@Getter @Setter
public class Order {
	@Id @GeneratedValue
	@Column(name="order_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)		// 지연 참조
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)		// 1:1인 경우에, 더 자주 사용하는 부분에 연관관계의 주인을 정한다. (여기서는 Order를 주인으로)
	@JoinColumn(name="delivery_id")
	private Delivery delivery;

	// Date를 쓰면 JPA와 매핑해줘야 하는데, 자바 8 이상에서 LocalDateTime 을 사용하면 알아서 매핑이 된다.
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; 		// 주문상태 (ORDER, CANCEL)
	
	// == 연관관계 메서드 == //
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public static Order createOrder(Member m, Delivery d, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(m);
		order.setDelivery(d);
		for(OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	// 주문 취소
	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송완료된 상품입니다.(취소 불가)");
		}
		
		this.setStatus(OrderStatus.CANCEL);
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem oi : orderItems) {
			totalPrice += oi.getOrderPrice();
		}
		
		return totalPrice;
	}
}
