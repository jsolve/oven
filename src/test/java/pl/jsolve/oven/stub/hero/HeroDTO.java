package pl.jsolve.oven.stub.hero;

import pl.jsolve.oven.annotationdriven.annotation.Alias;

@Alias("HeroDto")
public class HeroDTO {

	private Long id;
	private String nickname;

	public Long getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}
}