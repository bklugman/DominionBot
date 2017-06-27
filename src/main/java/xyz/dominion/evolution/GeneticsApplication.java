package xyz.dominion.evolution;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import org.jenetics.EnumGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;
import org.jenetics.util.ISeq;
import xyz.dominion.evolution.gene.DominionGene;
import xyz.dominion.evolution.genetics.DominionGenotypeHelper;

import java.util.ArrayList;

/**
 * Created by brettklugman on 1/14/17.
 */
public class GeneticsApplication {

    private static int replicationFactor = -1;
    private static int numberOfGames = 10;
    private static int maxWins = -1;
    private static DominionGenotypeHelper genotypeHelper;

    private static Integer eval(Genotype<EnumGene<DominionGene>> dominionGeneGenotype) {
        DomPlayer botPlayer = genotypeHelper.getPlayerFromGenotype(dominionGeneGenotype);
        DomEngine engine = new DomEngine();
        ArrayList<DomPlayer> players = new ArrayList<>(2);
        players.add(botPlayer);
        players.add((DomPlayer) engine.getBotArray()[0]);
        engine.startSimulation(players, false, numberOfGames, false);
        int wins = botPlayer.getWins();
        if (wins > maxWins) {
            maxWins = wins;
            System.out.println("max wins: " + wins);
        }
        maxWins = Math.max(maxWins, wins);
        return wins;
    }

    public static void main(String[] commandLineArguments) {
        long startTime = System.currentTimeMillis();
        replicationFactor = Integer.valueOf(commandLineArguments[0]);
        double minimumConstantValue = Double.valueOf(commandLineArguments[1]);
        double maximumConstantValue = Double.valueOf(commandLineArguments[2]);
        double incrementValue = Double.valueOf(commandLineArguments[3]);
        String filePath = commandLineArguments[4];
        numberOfGames = Integer.valueOf(commandLineArguments[5]);

        final int maximumNumberOfConditions = 3;
        genotypeHelper = DominionGenotypeHelper.builder()
                .setReplicationFactor(replicationFactor)
                .setMinimumConstantValue(minimumConstantValue)
                .setMaximumConstantValue(maximumConstantValue)
                .setIncrementValue(incrementValue)
                .setFilePath(filePath)
                .setMaximumNumberOfConditions(maximumNumberOfConditions)
                .build();

        Factory<Genotype<EnumGene<DominionGene>>> dominionBuyRules = genotypeHelper.getGenotypeFactory();
        Engine<EnumGene<DominionGene>, Integer> engine = Engine
                .builder(GeneticsApplication::eval, dominionBuyRules)
                .build();
        Genotype<EnumGene<DominionGene>> result = engine.stream()
                .limit(1000)
                .collect(EvolutionResult.toBestGenotype());
        System.out.println("Duration: " + (System.currentTimeMillis() - startTime) / (1000.0 * 60.0) + " minutes");
        System.out.println("Max wins: " + maxWins);
    }

    private static ISeq<DomCardName> getDominionCardsToUse(String[] args) {
        return null;
    }
}
