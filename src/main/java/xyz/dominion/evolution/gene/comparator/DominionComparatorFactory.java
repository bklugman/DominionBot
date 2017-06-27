package xyz.dominion.evolution.gene.comparator;

import be.aga.dominionSimulator.enums.DomBotComparator;
import org.jenetics.util.ISeq;
import xyz.dominion.evolution.gene.GeneUtility;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionComparatorFactory {

    public static ISeq<DominionComparator> getDominionComparators(int replicationFactor) {
        return GeneUtility.getAlleles(DomBotComparator.values(), DominionComparator::new, replicationFactor);
    }
}
