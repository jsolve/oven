package pl.jsolve.oven;

import org.junit.Test;
import pl.jsolve.oven.complex.ComplexMapper;
import pl.jsolve.oven.complex.MappingStrategy;
import pl.jsolve.oven.stub.hero.HeroWithAlias;
import pl.jsolve.oven.stub.hero.HeroSnapshotWithAlias;

import static org.fest.assertions.Assertions.assertThat;
import static pl.jsolve.oven.stub.hero.HeroProfiledBuilder.aCaptainAmerica;

public class ComplexMapperTest {

	private static final String SPACE = " ";
	private static final int ANY_NUMBER = 1337;
	private static final Long ID = 2L;

	@Test
	public void shouldMapHeroToHeroSnapshot() {
		// given
		HeroWithAlias captainAmerica = aCaptainAmerica().withId(ID).build();
		ComplexMapper<HeroWithAlias, HeroSnapshotWithAlias> heroToHeroSnapshotMapper = new ComplexMapper<HeroWithAlias, HeroSnapshotWithAlias>(new MappingStrategy<HeroWithAlias, HeroSnapshotWithAlias>() {

			@Override
			public HeroSnapshotWithAlias map(HeroWithAlias source, HeroSnapshotWithAlias target) {
				target.setName(source.getFirstName() + SPACE + source.getLastName());
				return target;
			}
		});

		// when
		HeroSnapshotWithAlias result = heroToHeroSnapshotMapper.map(captainAmerica);

		// then
		assertThat(result.getId()).as("id field has mapping annotations").isEqualTo(captainAmerica.getId());
		assertThat(result.getName()).isEqualTo(captainAmerica.getFirstName() + SPACE + captainAmerica.getLastName());
	}

	@Test
	public void shouldMapHeroToHeroSnapshotWithComplexIdMapping() {
		// given
		HeroWithAlias captainAmerica = aCaptainAmerica().withId(ID).build();
		ComplexMapper<HeroWithAlias, HeroSnapshotWithAlias> heroToHeroSnapshotMapper = new ComplexMapper<HeroWithAlias, HeroSnapshotWithAlias>(new MappingStrategy<HeroWithAlias, HeroSnapshotWithAlias>() {
			@Override
			public HeroSnapshotWithAlias map(HeroWithAlias source, HeroSnapshotWithAlias target) {
				target.setId(source.getId() + ANY_NUMBER);
				return target;
			}
		});
		// when
		HeroSnapshotWithAlias result = heroToHeroSnapshotMapper.map(captainAmerica);

		// then
		assertThat(result.getId()).isEqualTo(captainAmerica.getId() + ANY_NUMBER);
	}

	@Test
	public void shouldMapHeroSnapshotToHero() {
		// given
		HeroSnapshotWithAlias heroSnapshotWithAlias = new HeroSnapshotWithAlias();
		heroSnapshotWithAlias.setId(ID);
		heroSnapshotWithAlias.setName("Johann Schmidt");
		ComplexMapper<HeroSnapshotWithAlias, HeroWithAlias> heroSnapshotToHeroMapper = new ComplexMapper<HeroSnapshotWithAlias, HeroWithAlias>(new MappingStrategy<HeroSnapshotWithAlias, HeroWithAlias>() {

			@Override
			public HeroWithAlias map(HeroSnapshotWithAlias source, HeroWithAlias target) {
				target.setFirstName(source.getName().split(SPACE)[0]);
				target.setLastName(source.getName().split(SPACE)[1]);
				return target;
			}
		});

		// when
		HeroWithAlias result = heroSnapshotToHeroMapper.map(heroSnapshotWithAlias);

		// then
		assertThat(result.getId()).as("id field has mapping annotations").isEqualTo(heroSnapshotWithAlias.getId());
		assertThat(result.getFirstName() + SPACE + result.getLastName()).isEqualTo(heroSnapshotWithAlias.getName());
	}
}