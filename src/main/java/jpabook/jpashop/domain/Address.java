package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable			// 어디에서 내장타입으로 사용될 수 있는 클래스라는 의미
@Getter
// 내장클래스는 값을 변경할 수 없게 Setter를 만들지 않는다.
public class Address {
	private String city;
	private String street;
	private String zipcode;
	
	// 임베디드 타입은 자바 기본 생성자를 protected로 두는 것이 안전하다.
	protected Address() {
	}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
