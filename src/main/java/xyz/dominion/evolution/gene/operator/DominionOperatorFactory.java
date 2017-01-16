package xyz.dominion.evolution.gene.operator;

import be.aga.dominionSimulator.enums.DomBotOperator;
import org.jenetics.util.ISeq;
import xyz.dominion.evolution.gene.GeneUtility;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionOperatorFactory {

    public static ISeq<DominionOperator> getDominionOperators(int replicationFactor) {
        return GeneUtility.getAlleles(DomBotOperator.values(), DominionOperator::new, replicationFactor);
    }
}
