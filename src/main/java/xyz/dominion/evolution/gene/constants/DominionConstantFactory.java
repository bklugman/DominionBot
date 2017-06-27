package xyz.dominion.evolution.gene.constants;

import org.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionConstantFactory {

    public static ISeq<DominionConstant> getDominionConstants(double minimumConstantValue, double maximumConstantValue, double incrementRate, int replicationFactor) {
        List<DominionConstant> dominionConstants = new ArrayList<>();
        for (double value = minimumConstantValue; Double.compare(value, maximumConstantValue) <= 0; value += incrementRate) {
            for (int i = 0; i < replicationFactor; ++i) {
                dominionConstants.add(new DominionConstant(value));
            }
        }
        return ISeq.of(dominionConstants);
    }
}
