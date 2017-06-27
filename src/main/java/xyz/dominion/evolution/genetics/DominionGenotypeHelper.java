package xyz.dominion.evolution.genetics;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardType;
import org.jenetics.EnumGene;
import org.jenetics.Genotype;
import org.jenetics.PermutationChromosome;
import org.jenetics.util.ISeq;
import xyz.dominion.evolution.gene.DominionGene;
import xyz.dominion.evolution.gene.card.DominionCard;
import xyz.dominion.evolution.gene.card.DominionCardFactory;
import xyz.dominion.evolution.gene.comparator.DominionComparator;
import xyz.dominion.evolution.gene.comparator.DominionComparatorFactory;
import xyz.dominion.evolution.gene.constants.DominionConstant;
import xyz.dominion.evolution.gene.constants.DominionConstantFactory;
import xyz.dominion.evolution.gene.function.DominionFunction;
import xyz.dominion.evolution.gene.function.DominionFunctionFactory;
import xyz.dominion.evolution.gene.operator.DominionOperator;
import xyz.dominion.evolution.gene.operator.DominionOperatorFactory;

/**
 * 
 * Created by brettklugman on 1/15/17.
 */
public class DominionGenotypeHelper {

    private final int replicationFactor;
    private final double minimumConstantValue;
    private final double maximumConstantValue;
    private final double incrementValue;
    private final String filePath;
    private final int maximumNumberOfConditions;

    private DominionGenotypeHelper(int replicationFactor, double minimumConstantValue, double maximumConstantValue, 
                                  double incrementValue, String filePath, int maximumNumberOfConditions) {
        this.replicationFactor = replicationFactor;
        this.minimumConstantValue = minimumConstantValue;
        this.maximumConstantValue = maximumConstantValue;
        this.incrementValue = incrementValue;
        this.filePath = filePath;
        this.maximumNumberOfConditions = maximumNumberOfConditions;
    }

    public Genotype<EnumGene<DominionGene>> getGenotypeFactory() {
        int copiesOfEachGene = replicationFactor * maximumNumberOfConditions;
        ISeq<DominionCard> dominionCards = DominionCardFactory.getInstance().getDominionCardsToUse(filePath, copiesOfEachGene);
        ISeq<DominionConstant> conditionsLimits = DominionConstantFactory.getDominionConstants(0, maximumNumberOfConditions, 1, replicationFactor);
        ISeq<DominionComparator> dominionComparators = DominionComparatorFactory.getDominionComparators(copiesOfEachGene);
        ISeq<DominionConstant> dominionConstants = DominionConstantFactory.getDominionConstants(minimumConstantValue, maximumConstantValue, incrementValue, copiesOfEachGene);
        ISeq<DominionFunction> dominionFunctions = DominionFunctionFactory.getDominionFunctions(copiesOfEachGene);
        ISeq<DominionOperator> dominionOperators = DominionOperatorFactory.getDominionOperators(copiesOfEachGene);
        PermutationChromosome[] dominionGenes = getPermutationChromosomes(copiesOfEachGene, dominionCards, conditionsLimits, dominionComparators, dominionConstants, dominionFunctions, dominionOperators);
        return Genotype.of(PermutationChromosome.of(dominionCards, replicationFactor), dominionGenes);
    }

    private PermutationChromosome[] getPermutationChromosomes(int copiesOfEachGene, ISeq<DominionCard> dominionCards, ISeq<DominionConstant> conditionsLimits, ISeq<DominionComparator> dominionComparators, ISeq<DominionConstant> dominionConstants, ISeq<DominionFunction> dominionFunctions, ISeq<DominionOperator> dominionOperators) {
        PermutationChromosome[] dominionGenes = new PermutationChromosome[DominionGeneIndex.values().length - 1];
        dominionGenes[DominionGeneIndex.Comparator.index - 1] = PermutationChromosome.of(dominionComparators, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.ConditionLimit.index - 1] = PermutationChromosome.of(conditionsLimits, replicationFactor);
        dominionGenes[DominionGeneIndex.LeftCard.index - 1] = PermutationChromosome.of(dominionCards, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.LeftFunction.index - 1] = PermutationChromosome.of(dominionFunctions, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.LeftConstant.index - 1] = PermutationChromosome.of(dominionConstants, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.RightCard.index - 1] = PermutationChromosome.of(dominionCards, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.RightFunction.index - 1] = PermutationChromosome.of(dominionFunctions, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.RightConstant.index - 1] = PermutationChromosome.of(dominionConstants, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.Operator.index - 1] = PermutationChromosome.of(dominionOperators, copiesOfEachGene);
        dominionGenes[DominionGeneIndex.OperatorConstant.index - 1] = PermutationChromosome.of(dominionConstants, copiesOfEachGene);
        return dominionGenes;
    }
    
    public DomPlayer getPlayerFromGenotype(Genotype<EnumGene<DominionGene>> dominionGenotype) {
        DomPlayer botPlayer = new DomPlayer("the best player", "the best authors", "one bot to rule them all");
        for (int i = 0; i < replicationFactor; ++i) {
            DominionCard cardBeingPurchased = (DominionCard) dominionGenotype.get(DominionGeneIndex.BuyCard.index, i).getAllele();
            DomBuyRule domBuyRule = new DomBuyRule(cardBeingPurchased.getDominionCardName().name(), null, null);
            DominionConstant conditionsLimit = (DominionConstant) dominionGenotype.get(DominionGeneIndex.ConditionLimit.index, i).getAllele();
            int numberOfConditions = (int) conditionsLimit.getConstantValue();
            for (int j = 0; j < numberOfConditions; ++j) {
                DominionFunction leftFunction = (DominionFunction) dominionGenotype.get(DominionGeneIndex.LeftFunction.index, j).getAllele();
                DominionCard leftCard = (DominionCard) dominionGenotype.get(DominionGeneIndex.LeftCard.index, j).getAllele();
                DominionConstant leftConstant = (DominionConstant) dominionGenotype.get(DominionGeneIndex.LeftConstant.index, j).getAllele();
                DominionComparator comparators = (DominionComparator) dominionGenotype.get(DominionGeneIndex.Comparator.index, j).getAllele();
                DominionFunction rightFunction = (DominionFunction) dominionGenotype.get(DominionGeneIndex.RightFunction.index, j).getAllele();
                DominionCard rightCard = (DominionCard) dominionGenotype.get(DominionGeneIndex.RightCard.index, j).getAllele();
                DominionConstant rightConstant = (DominionConstant) dominionGenotype.get(DominionGeneIndex.RightConstant.index, j).getAllele();
                DominionOperator operator = (DominionOperator) dominionGenotype.get(DominionGeneIndex.Operator.index, j).getAllele();
                DominionConstant operatorConstant = (DominionConstant) dominionGenotype.get(DominionGeneIndex.OperatorConstant.index, j).getAllele();
                DomBuyCondition buyCondition = new DomBuyCondition(leftFunction.getFunction(), leftCard.getDominionCardName(), DomCardType.Action,
                        String.valueOf(leftConstant.getConstantValue()), comparators.getComparator(), rightFunction.getFunction(), rightCard.getDominionCardName(),
                        DomCardType.Action, String.valueOf(rightConstant.getConstantValue()), operator.getOperator(), String.valueOf(operatorConstant.getConstantValue()));
                domBuyRule.addCondition(buyCondition);
                domBuyRule.setBaneCard(cardBeingPurchased.getDominionCardName());
            }
            botPlayer.addBuyRule(domBuyRule);
        }    
        return botPlayer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int replicationFactor = 0;
        private double minimumConstantValue = 0;
        private double maximumConstantValue = 0;
        private double incrementValue = 0;
        private String filePath = "";
        private int maximumNumberOfConditions = 0;

        public Builder setReplicationFactor(int replicationFactor) {
            this.replicationFactor = replicationFactor;
            return this;
        }

        public Builder setMinimumConstantValue(double minimumConstantValue) {
            this.minimumConstantValue = minimumConstantValue;
            return this;
        }

        public Builder setMaximumConstantValue(double maximumConstantValue) {
            this.maximumConstantValue = maximumConstantValue;
            return this;
        }

        public Builder setIncrementValue(double incrementValue) {
            this.incrementValue = incrementValue;
            return this;
        }

        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public DominionGenotypeHelper build() {
            return new DominionGenotypeHelper(replicationFactor, minimumConstantValue, maximumConstantValue, incrementValue, filePath, maximumNumberOfConditions);
        }

        public Builder setMaximumNumberOfConditions(int maximumNumberOfConditions) {
            this.maximumNumberOfConditions = maximumNumberOfConditions;
            return this;
        }
    }
    
    private enum DominionGeneIndex {
        BuyCard(0),
        ConditionLimit(1),
        LeftFunction(2),
        LeftCard(3),
        LeftConstant(4),
        Comparator(5),
        RightFunction(6),
        RightCard(7),
        RightConstant(8),
        Operator(9),
        OperatorConstant(10);
        
        private final int index;

        DominionGeneIndex(int index) {
            this.index = index;
        }
    }
}
