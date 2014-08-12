package pl.jsolve.oven;

import org.junit.Test;
import pl.jsolve.oven.complex.ComplexMapper;
import pl.jsolve.oven.complex.MappingStrategy;
import pl.jsolve.oven.stub.hero.Hero;
import pl.jsolve.oven.stub.hero.HeroSnapshot;

import static org.fest.assertions.Assertions.assertThat;
import static pl.jsolve.oven.stub.hero.HeroProfiledBuilder.aCaptainAmerica;

public class ComplexMapperTest {

	private static final String SPACE = " ";
	private static final int ANY_NUMBER = 1337;
	private static final Long ID = 2L;

	@Test
	public void shouldMapHeroToHeroSnapshot() {
		// given
		Hero captainAmerica = aCaptainAmerica().withId(ID).build();
		ComplexMapper<Hero, HeroSnapshot> heroToHeroSnapshotMapper = new ComplexMapper<Hero, HeroSnapshot>(new MappingStrategy<Hero, HeroSnapshot>() {

			@Override
			public HeroSnapshot map(Hero source, HeroSnapshot target) {
				target.setName(source.getFirstName() + SPACE + source.getLastName());
				return target;
			}
		});

		// when
		HeroSnapshot result = heroToHeroSnapshotMapper.map(captainAmerica);

		// then
		assertThat(result.getId()).as("id field has mapping annotations").isEqualTo(captainAmerica.getId());
		assertThat(result.getName()).isEqualTo(captainAmerica.getFirstName() + SPACE + captainAmerica.getLastName());
	}

	@Test
	public void shouldMapHeroToHeroSnapshotWithComplexIdMapping() {
		// given
		Hero captainAmerica = aCaptainAmerica().withId(ID).build();
		ComplexMapper<Hero, HeroSnapshot> heroToHeroSnapshotMapper = new ComplexMapper<Hero, HeroSnapshot>(new MappingStrategy<Hero, HeroSnapshot>() {
			@Override
			public HeroSnapshot map(Hero source, HeroSnapshot target) {
				target.setId(source.getId() + ANY_NUMBER);
				return target;
			}
		});
		// when
		HeroSnapshot result = heroToHeroSnapshotMapper.map(captainAmerica);

		// then
		assertThat(result.getId()).isEqualTo(captainAmerica.getId() + ANY_NUMBER);
	}

	@Test
	public void shouldMapHeroSnapshotToHero() {
		// given
		HeroSnapshot heroSnapshot = new HeroSnapshot();
		heroSnapshot.setId(ID);
		heroSnapshot.setName("Johann Schmidt");
		ComplexMapper<HeroSnapshot, Hero> heroSnapshotToHeroMapper = new ComplexMapper<HeroSnapshot, Hero>(new MappingStrategy<HeroSnapshot, Hero>() {

			@Override
			public Hero map(HeroSnapshot source, Hero target) {
				target.setFirstName(source.getName().split(SPACE)[0]);
				target.setLastName(source.getName().split(SPACE)[1]);
				return target;
			}
		});

		// when
		Hero result = heroSnapshotToHeroMapper.map(heroSnapshot);

		// then
		assertThat(result.getId()).as("id field has mapping annotations").isEqualTo(heroSnapshot.getId());
		assertThat(result.getFirstName() + SPACE + result.getLastName()).isEqualTo(heroSnapshot.getName());
	}
}