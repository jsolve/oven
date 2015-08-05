package pl.jsolve.oven.stub.hero;

import pl.jsolve.oven.annotationdriven.annotation.Alias;

@Alias("HeroDTO")
public class HeroDTOWithAlias  {

	private Long id;
	private String nickname;

	public Long getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}
}