package br.ufscar.hcga.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author gean_
 */
public class CountRules {

public ArrayList<String> readRulesFile(String rulesFile) {
    ArrayList<String> result = new ArrayList<>();

    try {
        FileReader reader = new FileReader(rulesFile);
        BufferedReader buffReader = new BufferedReader(reader);

        String line = null;
        while ((line = buffReader.readLine()) != null) {
            if (!line.isEmpty()) {
                result.add(line);
            }
        }

        buffReader.close();
        reader.close();

    } catch (IOException ioe) {
        ioe.printStackTrace();
    }

    return result;
}

public ArrayList<String> readRulesFileClus(String rulesFile) {
    ArrayList<String> result = new ArrayList<>();
    String wholeRule = "";
    try {
        FileReader reader = new FileReader(rulesFile);
        BufferedReader buffReader = new BufferedReader(reader);

        String line = null;
        while ((line = buffReader.readLine()) != null) {
            if (!line.isEmpty()) {
                wholeRule += line;

                if (line.contains("THEN")) {
                    result.add(wholeRule);
                    wholeRule = "";
                }
            }
        }

        buffReader.close();
        reader.close();

    } catch (IOException ioe) {
        ioe.printStackTrace();
    }

    for (int i = 0; i < result.size(); i++) {
        String aux = result.get(i);
        aux = aux.replaceAll("=", "");
        aux = aux.replace(":", "=");
        aux = aux.replace("IF", "");
        aux = aux.replace("[", "");
        int index = aux.indexOf("]");
        aux = aux.substring(0, index);
        aux = aux.trim();
        result.remove(i);
        result.add(i, aux);
    }

    return result;
}

public int countTestsInRules(ArrayList<String> rulesFile) {
    int cont = 0;
    int ini;

    while (!rulesFile.isEmpty()) {
        String line = rulesFile.remove(0).trim();

        if (!line.isEmpty()) {
            ini = 0;

            if (line.indexOf("AND") == -1) {
                cont++;

            } else {
                cont++;

                while ((ini = line.indexOf("AND", ini)) != -1) {
                    cont++;
                    ini += 3;
                }
            }
        }
    }

    return cont;
}

public void run(String pathRules, String dataset, int folds, int numberOfTrainIntances) {

    double meanNumberOfRules = 0, meanTestsInRules = 0;
    double meanAverageTestPerRule = 0, meanAverageCoveragePerRule = 0;

    System.out.println("=====================================================");
    System.out.println("dataset: " + dataset);

    for (int i = 1; i <= folds; i++) {
//        ArrayList<String> rules = readRulesFileClus(pathRules + dataset + i + "Rules.txt");
//        ArrayList<String> rules = readRulesFile(pathRules + dataset + i + "rules.txt");
        ArrayList<String> rules = readRulesFile(pathRules + "rules.txt");

        int numberOfRules = rules.size();
        meanNumberOfRules += numberOfRules;

        int testsInRules = countTestsInRules(rules);
        meanTestsInRules += testsInRules;

        Double averageTestPerRule = (double) testsInRules / (double) numberOfRules;
        meanAverageTestPerRule += averageTestPerRule;

        Double averageCoveragePerRule = (double) numberOfTrainIntances / (double) numberOfRules;
        meanAverageCoveragePerRule += averageCoveragePerRule;

        System.out.println("fold " + i);
        System.out.println("numberOfRules: " + numberOfRules);
        System.out.println("numberOfActiveTests: " + testsInRules);
        System.out.println("averageTestPerRule: " + averageTestPerRule);
        System.out.println("averageCoveragePerRule: " + averageCoveragePerRule + "\n");
    }

    System.out.println("\nmeanNumberOfRules: " + meanNumberOfRules / folds);
    System.out.println("meanNumberOfActiveTests: " + meanTestsInRules / folds);
    System.out.println("meanAverageTestPerRule: " + meanAverageTestPerRule / folds);
    System.out.println("meanAverageCoveragePerRule: " + meanAverageCoveragePerRule / folds);
    System.out.println("=====================================================\n\n");
}

public void runOneFold(String pathRules, String dataset, int whichFold, int numberOfTrainIntances) {

//    double meanNumberOfRules = 0, meanTestsInRules = 0;
//    double meanAverageTestPerRule = 0, meanAverageCoveragePerRule = 0;
    System.out.println("=====================================================");
    System.out.println("dataset: " + dataset);

//    for (int i = 1; i <= folds; i++) {
//        ArrayList<String> rules = readRulesFileClus(pathRules + dataset + i + "Rules.txt");
//        ArrayList<String> rules = readRulesFile(pathRules + dataset + i + "rules.txt");
    ArrayList<String> rules = readRulesFile(pathRules + "rules.txt");

    int numberOfRules = rules.size();
//        meanNumberOfRules += numberOfRules;

    int testsInRules = countTestsInRules(rules);
//        meanTestsInRules += testsInRules;

    Double averageTestPerRule = (double) testsInRules / (double) numberOfRules;
//        meanAverageTestPerRule += averageTestPerRule;

    Double averageCoveragePerRule = (double) numberOfTrainIntances / (double) numberOfRules;
//        meanAverageCoveragePerRule += averageCoveragePerRule;

    System.out.println("fold " + whichFold);
    System.out.println("numberOfRules: " + numberOfRules);
    System.out.println("numberOfActiveTests: " + testsInRules);
    System.out.println("averageTestPerRule: " + averageTestPerRule);
    System.out.println("averageCoveragePerRule: " + averageCoveragePerRule + "\n");
//    }
//
//    System.out.println("\nmeanNumberOfRules: " + meanNumberOfRules / folds);
//    System.out.println("meanNumberOfActiveTests: " + meanTestsInRules / folds);
//    System.out.println("meanAverageTestPerRule: " + meanAverageTestPerRule / folds);
//    System.out.println("meanAverageCoveragePerRule: " + meanAverageCoveragePerRule / folds);
    System.out.println("=====================================================\n\n");
}

public static void main(String[] args) {
    CountRules c = new CountRules();
    c.runOneFold("C:\\Users\\gean_\\Dropbox\\posGrad\\Experimentos\\HC-GA\\TEs\\Mips\\"
            + "testeFitnessFmeasure\\Results\\SingleLabel\\Tree\\mips1testatt.arff\\Run1\\",
            "mips", 1, 16805);
//    c.run("C:\\Users\\gean_\\Dropbox\\posGrad\\Experimentos\\HC-LGA\\TEs\\REPBASE\\"
//            + "config16\\exec2_min07&08max08&09_paramDouble_pareto_semiOff\\fold10\\obj1\\",
//            "repbase", 1, 31091);
}
}
