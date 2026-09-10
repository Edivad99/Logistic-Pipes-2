package logisticspipes.utils.tuples;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Data;

import org.jspecify.annotations.Nullable;

import logisticspipes.proxy.computers.interfaces.ILPCCTypeHolder;

@Data
/**
 * A pair whose halves may each be null: the type parameters opt out of the package's non-null
 * default, because a tuple carries whatever it is given and several callers give it nothing.
 */
public class Pair<T1 extends @Nullable Object, T2 extends @Nullable Object> implements ILPCCTypeHolder {

	private final Object[] ccTypeHolder = new Object[1];
	protected T1 value1;
	protected T2 value2;

	public Pair(kotlin.Pair<T1, T2> kotlinPair) {
		this(kotlinPair.component1(), kotlinPair.component2());
	}

	public Pair(T1 value1, T2 value2) {
		this.value1 = value1;
		this.value2 = value2;
	}

	public final T1 component1() {
		return value1;
	}

	public final T2 component2() {
		return value2;
	}

	public Pair<T1, T2> copy() {
		return new Pair<>(value1, value2);
	}

	@Override
	public Object[] getTypeHolder() {
		return ccTypeHolder;
	}

	public static <T1, T2> Collector<Pair<T1, T2>, ?, Map<T1, T2>> toMap() {
		return Collectors.toMap(Pair::getValue1, Pair::getValue2);
	}

	public static <T1, T2> Collector<Pair<T1, T2>, ?, Map<T1, T2>> toMap(BinaryOperator<T2> mergeFunction) {
		return Collectors.toMap(Pair::getValue1, Pair::getValue2, mergeFunction);
	}
}
