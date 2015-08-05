package pl.jsolve.oven.stub.hero;

import pl.jsolve.sweetener.builder.Builder;

public class HeroWithAliasesBuilder extends Builder<HeroWithAlias> {

    public static HeroWithAliasesBuilder aHero() {
        return new HeroWithAliasesBuilder();
    }

    public HeroWithAliasesBuilder withFirstName(String firstName) {
        getBuiltObject().setFirstName(firstName);
        return this;
    }

    public HeroWithAliasesBuilder withLastName(String lastName) {
        getBuiltObject().setLastName(lastName);
        return this;
    }

    public HeroWithAliasesBuilder withNickname(String nickname) {
        getBuiltObject().setNickname(nickname);
        return this;
    }

    public HeroWithAliasesBuilder withId(Long id) {
        getBuiltObject().setId(id);
        return this;
    }
}
