package logisticspipes.utils.tuples;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.jspecify.annotations.Nullable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Triplet<T1 extends @Nullable Object, T2 extends @Nullable Object, T3 extends @Nullable Object> extends Pair<T1, T2> {

	protected T3 value3;

	public Triplet(T1 value1, T2 value2, T3 value3) {
		super(value1, value2);
		this.value3 = value3;
	}

	@Override
	public Triplet<T1, T2, T3> copy() {
		return new Triplet<>(value1, value2, value3);
	}
}
