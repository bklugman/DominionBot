package xyz.dominion.evolution.gene.operator;

import be.aga.dominionSimulator.enums.DomBotOperator;
import xyz.dominion.evolution.gene.DominionGene;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionOperator implements DominionGene {

    private final DomBotOperator operator;

    public DominionOperator(DomBotOperator operator) {
        this.operator = operator;
    }

    public DomBotOperator getOperator() {
        return operator;
    }
}
