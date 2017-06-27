package xyz.dominion.evolution.gene.function;

import be.aga.dominionSimulator.enums.DomBotFunction;
import xyz.dominion.evolution.gene.DominionGene;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionFunction implements DominionGene {

    private final DomBotFunction function;

    public DominionFunction(DomBotFunction function) {
        this.function = function;
    }

    public DomBotFunction getFunction() {
        return function;
    }
}
