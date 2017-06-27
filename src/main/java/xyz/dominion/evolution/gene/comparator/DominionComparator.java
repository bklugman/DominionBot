package xyz.dominion.evolution.gene.comparator;

import be.aga.dominionSimulator.enums.DomBotComparator;
import xyz.dominion.evolution.gene.DominionGene;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionComparator implements DominionGene {

    private final DomBotComparator comparator;

    public DominionComparator(DomBotComparator comparator) {
        this.comparator = comparator;
    }

    public DomBotComparator getComparator() {
        return comparator;
    }
}
