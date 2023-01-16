package jpabook.jpashop.projectSetting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member_2 {
	@Id @GeneratedValue
	private Long id;
	private String username;
	
	
}
