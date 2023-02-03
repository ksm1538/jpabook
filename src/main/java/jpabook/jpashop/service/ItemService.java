package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	
	@Transactional
	public void saveItem(Item i) {
		itemRepository.save(i);
	}
	
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}

	// dirty Check.
	// 영속성 컨텍스트에서 조회 후, 변경할 부분만 변경해주면 알아서 변경됨.
	public void updateItem(Long itemId, String name, int price, int stockQuantity) {
		Item item = itemRepository.findOne(itemId);
		
		item.setName(name);
		item.setPrice(price);
		item.setStockQuantity(stockQuantity);
	}
}
