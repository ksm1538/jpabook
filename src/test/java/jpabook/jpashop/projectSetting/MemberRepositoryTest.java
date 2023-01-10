package jpabook.jpashop.projectSetting;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)		// Spring으로 실행한다.		
@SpringBootTest						// SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired MemberRepository memberRepository;
	
	@Test
	@Transactional			// Spring에서 제공하는 Transactional 사용을 추천함 (옵션이 더 많음)
	@Rollback(false)		// Test가 끝나면 Rollback하는 기능을 끔
	public void testMember() throws Exception{
		Member member = new Member();
		member.setUsername("yappi yappi");
		
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.find(saveId);
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
		
		System.out.println("findMember == member? : " + (findMember == member));
		
	}
}

 