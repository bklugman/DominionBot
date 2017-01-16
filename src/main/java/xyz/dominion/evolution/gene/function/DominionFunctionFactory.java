package xyz.dominion.evolution.gene.function;

import be.aga.dominionSimulator.enums.DomBotFunction;
import org.jenetics.util.ISeq;
import xyz.dominion.evolution.gene.GeneUtility;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionFunctionFactory {

    public static ISeq<DominionFunction> getDominionFunctions(int replicationFactor) {
        return GeneUtility.getAlleles(DomBotFunction.values(), DominionFunction::new, replicationFactor);
    }
}
