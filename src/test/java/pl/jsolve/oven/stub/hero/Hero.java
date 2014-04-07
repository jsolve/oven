package pl.jsolve.oven.stub.hero;

import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableTo;
import pl.jsolve.oven.annotationdriven.annotation.Mappings;

@MappableTo({ HeroSnapshot.class, HeroDTO.class })
public class Hero {

	@Map
	private Long id;
	private String firstName;
	private String lastName;
	@Mappings({
		@Map(to = "name", of = HeroSnapshot.class),
		@Map(to = "nickname", of = HeroDTO.class),
	})
	private String nickname;

	public Hero() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
