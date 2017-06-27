package xyz.dominion.evolution.gene.card;

import be.aga.dominionSimulator.enums.DomCardName;
import org.jenetics.util.ISeq;
import xyz.dominion.evolution.gene.GeneUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by brettklugman on 1/14/17.
 */
public class DominionCardFactory {

    private static DominionCardFactory instance;


    public static DominionCardFactory getInstance() {
        if (instance == null) {
            instance = new DominionCardFactory();
        }
        return instance;
    }

    public ISeq<DominionCard> getDominionCardsToUse(String pathToFile, int replicationFactor) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(pathToFile).getFile());
        List<DomCardName> domCardNames = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    domCardNames.add(DomCardName.valueOf(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GeneUtility.getAlleles(domCardNames.toArray(new DomCardName[domCardNames.size()]), DominionCard::new, replicationFactor);
    }
}
