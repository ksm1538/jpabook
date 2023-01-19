package jpabook.jpashop.projectSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;

@Repository
public class MemberRepository_2 {
	@PersistenceContext
	private EntityManager em;

	public Long save(Member_2 m) {
		em.persist(m);
		
		// member를 반환하지 않고, member의 id(PK)를 반환하는 이유
		// 해당 member를 이용해서 수정이나 이런 것을 진행하는 경우, 의도치 않게 DB에 반영이 될 수 있기 때문 
		return m.getId();		
	}
	
	public Member_2 find(Long id) {
		return em.find(Member_2.class, id);
	}
	
}
