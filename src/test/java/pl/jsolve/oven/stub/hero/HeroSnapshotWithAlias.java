package pl.jsolve.oven.stub.hero;

import pl.jsolve.oven.annotationdriven.annotation.Alias;
import pl.jsolve.oven.annotationdriven.annotation.Map;
import pl.jsolve.oven.annotationdriven.annotation.MappableToAlias;

@Alias("HeroSnapshot")
@MappableToAlias("Hero")
public class HeroSnapshotWithAlias {

	@Map
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}