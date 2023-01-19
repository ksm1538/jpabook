package jpabook.jpashop.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@Service
@Transactional(readOnly=true)		 // 만약 읽기 전용의 경우, readOnly=true로 설정하면 약간의 성능 향상이 가능 (em flush X)
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Transactional
	public Long join(Member m) {
		validateDuplicateMember(m);		// 중복검사
		memberRepository.save(m);
		
		return m.getId();
	}
	
	private void validateDuplicateMember(Member m) {
		List<Member> findMembers = memberRepository.findByName(m.getName());
		
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
