package xyz.dominion.evolution.gene.card;

import be.aga.dominionSimulator.enums.DomCardName;
import xyz.dominion.evolution.gene.DominionGene;

/**
 * Created by brettklugman on 1/14/17.
 */
public class DominionCard implements DominionGene {

    private final DomCardName dominionCardName;

    public DominionCard(DomCardName dominionCardName) {
        this.dominionCardName = dominionCardName;
    }

    public DomCardName getDominionCardName() {
        return dominionCardName;
    }
}
