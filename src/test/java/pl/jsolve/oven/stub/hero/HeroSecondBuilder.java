package pl.jsolve.oven.stub.hero;

import pl.jsolve.sweetener.builder.Builder;

public class HeroSecondBuilder extends Builder<HeroSecond> {

	public static HeroSecondBuilder aHero() {
		return new HeroSecondBuilder();
	}

	public HeroSecondBuilder withFirstName(String firstName) {
		getBuiltObject().setFirstName(firstName);
		return this;
	}

	public HeroSecondBuilder withLastName(String lastName) {
		getBuiltObject().setLastName(lastName);
		return this;
	}

	public HeroSecondBuilder withNickname(String nickname) {
		getBuiltObject().setNickname(nickname);
		return this;
	}

	public HeroSecondBuilder withId(Long id) {
		getBuiltObject().setId(id);
		return this;
	}
}
