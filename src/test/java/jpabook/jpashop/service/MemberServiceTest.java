package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	public void 가입() throws Exception{
		Member m = new Member();
		
		m.setName("KSM");
		
		Long saveId = memberService.join(m);
		
		assertEquals(m, memberRepository.findOne(saveId));
	}
	
	@Test(expected=IllegalStateException.class)
	public void 중복() throws Exception{
		Member m1 = new Member();
		m1.setName("KSM");
		
		Member m2 = new Member();
		m2.setName("KSM");
		
		memberService.join(m1);
		memberService.join(m2);		// 예외 발생 예정
		
		fail("예외 발생했어야 함. 중복 거르기 실패");
		
	}
}
