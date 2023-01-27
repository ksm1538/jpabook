package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void 상품주문() throws Exception{
		Member m = createMember();
		Item i = createBook("JPA 고수의 길", 99999, 10);
		int orderCnt = 5;
		
		Long orderId = orderService.order(m.getId(), i.getId(), orderCnt);
		
		Order order = orderRepository.findOne(orderId);
		
		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
		assertEquals("주문한 상품 종류의 갯수는 정확해야 한다.", 1, order.getOrderItems().size());
		assertEquals("주문 가격은 가격 * 수량이다.", 99999 * 5, order.getTotalPrice());
		assertEquals("주문 수량만큼 재고의 수량이 줄어들어야 한다..", 5, i.getStockQuantity());
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() throws Exception{
		Member m = createMember();
		Item i = createBook("재고수량 초과 책", 10000, 10);
		
		int orderCnt = 11;
		
		orderService.order(m.getId(), i.getId(), orderCnt);
		
		fail("재고 수량 부족 예외 발생 필요");
	}
	
	@Test
	public void 주문취소() {
		Member m = createMember();
		Item i = createBook("JPA 고수의 길", 99999, 10);
		int orderCnt = 5;
		
		Long orderId = orderService.order(m.getId(), i.getId(), orderCnt);
		
		orderService.cancelOrder(orderId);
		
		Order order = orderRepository.findOne(orderId);
		
		assertEquals("주문 취소 시 상태는 CANCEL", OrderStatus.CANCEL, order.getStatus());
		assertEquals("주문 취소 시, 재고는 그만큼 증가해야 한다.", 10, i.getStockQuantity());
		
	}
	
	private Member createMember() {
		Member m = new Member();
		m.setName("회원");
		m.setAddress(new Address("서울", "한강", "11111"));
		em.persist(m);
		
		return m;
	}
	
	private Book createBook(String name, int price, int cnt) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(cnt);
		
		em.persist(book);
		return book;
	}
}
