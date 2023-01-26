package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {
	@Id @GeneratedValue
	@Column(name="order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;		// 주문상품
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;	// 주문
	
	private int orderPrice;	// 주문 가격
	private int count;		// 주문 수량
	
	public static OrderItem createOrderItem(Item i, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(i);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		i.removeStock(count);
		return orderItem;
	}
	
	public void cancel() {
		getItem().addStock(count);
	}
	
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
}
