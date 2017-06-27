package xyz.dominion.evolution.gene;

import org.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by brettklugman on 1/14/17.
 */
public class GeneUtility {

    public static <P, Q> ISeq<Q> getAlleles(P[] values, Function<P, Q> mapper,  int replicationFactor) {
        List<Q> operators = new ArrayList<>();
        for (int i = 0; i < replicationFactor; ++i) {
            operators.addAll(Arrays.stream(values)
                    .map(mapper)
                    .collect(Collectors.toList()));
        }
        return ISeq.of(operators);
    }
}
