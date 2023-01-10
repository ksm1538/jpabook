package jpabook.jpashop.projectSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
	@PersistenceContext
	private EntityManager em;

	public Long save(Member member) {
		em.persist(member);
		
		// member를 반환하지 않고, member의 id(PK)를 반환하는 이유
		// 해당 member를 이용해서 수정이나 이런 것을 진행하는 경우, 의도치 않게 DB에 반영이 될 수 있기 때문 
		return member.getId();		
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
}
