package pl.jsolve.oven.stub.hero;

import pl.jsolve.oven.annotationdriven.annotation.Alias;
import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableToAlias;
import pl.jsolve.oven.annotationdriven.annotation.Mappings;

@Alias("Hero")
@MappableToAlias({ "HeroSnapshot", "HeroDTO" })
public class HeroWithAlias {

	@Map
	private Long id;
	private String firstName;
	private String lastName;
	@Mappings({
		@Map(to = "name", of = HeroSnapshotWithAlias.class),
		@Map(to = "nickname", of = HeroDTOWithAlias.class),
	})
	private String nickname;

	public HeroWithAlias() {
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
