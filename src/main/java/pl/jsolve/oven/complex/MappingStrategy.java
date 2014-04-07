package pl.jsolve.oven.complex;

public interface MappingStrategy<S, T> {

	T map(S source, T target);
}