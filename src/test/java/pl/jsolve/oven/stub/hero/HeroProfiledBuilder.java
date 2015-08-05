package pl.jsolve.oven.stub.hero;

import static pl.jsolve.oven.stub.hero.HeroWithAliasesBuilder.aHero;
import pl.jsolve.sweetener.builder.Builder;

public class HeroProfiledBuilder extends Builder<HeroWithAliasesBuilder> {

	public static HeroWithAliasesBuilder aCaptainAmerica() {
		return aHero().withFirstName("Steve").withLastName("Rogers").withNickname("captainAmerica");
	}

	public static HeroWithAliasesBuilder aRedScull() {
		return aHero().withFirstName("Johann").withLastName("Schmidt").withNickname("redScull");
	}

	public static HeroWithAliasesBuilder anIronMan() {
		return aHero().withFirstName("Anthony").withLastName("Stark").withNickname("ironMan");
	}

	public static HeroWithAliasesBuilder aHulk() {
		return aHero().withFirstName("Bruce").withLastName("Banner").withNickname("hulk");
	}
}
