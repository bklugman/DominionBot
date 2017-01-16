package xyz.dominion.evolution.gene.constants;

import xyz.dominion.evolution.gene.DominionGene;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionConstant implements DominionGene {
    private final double constantValue;

    public DominionConstant(double constantValue) {
        this.constantValue = constantValue;
    }

    public double getConstantValue() {
        return constantValue;
    }
}
