package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable			// 어디에서 내장타입으로 사용될 수 있는 클래스라는 의미
@Getter @Setter
public class Address {
	private String city;
	private String strret;
	private String zipcode;
}
