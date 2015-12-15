package LogisticRegression;


import java.io.*;
import java.util.*;

public class ParseFile {

    public static void main(String[] args) {
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(new File("logistic_input.txt"));
            br = new BufferedReader(fr);

            String trainingSetPath = br.readLine();
            String testSetPath = br.readLine();
            double learningRate = Double.parseDouble(br.readLine());
            boolean isCrossValidation = Boolean.parseBoolean(br.readLine());
            int folds = Integer.parseInt(br.readLine());

            br.close();

            Data data = new Data();
            CrossValidation crossValidation = new CrossValidation(trainingSetPath);

            List<Double> accuracyList = new ArrayList<Double>();

            for (int i = 0; i < folds; i++) {
                ArrayList<Example> examples = crossValidation.runCrossValidation(folds, data, i, isCrossValidation);

                LogisticTrain logisticTrain = new LogisticTrain(data.getNumberOfCols(), learningRate, data);

                for (String label : data.getDistinctLabels()) {
                    System.out.println("Building a model for " + label);
                    ArrayList<Example> trainingExamples = makeTrainData(examples, label, data);
                    logisticTrain.train(trainingExamples, label);
                }

                logisticTrain.printModels();

                if (isCrossValidation) {
                    double acc = logisticTrain.classify(crossValidation.foldTestList, new HashMap<String, ArrayList<Double>>());
                    accuracyList.add(acc);
                } else {
                    TestData testData = new TestData(testSetPath);
                    ArrayList<Example> testExamples = testData.testData();
                    double acc = logisticTrain.classify(testExamples, new HashMap<String, ArrayList<Double>>());
                    accuracyList.add(acc);
                }
            }

            printAllAccuracy(accuracyList);


        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private static void printAllAccuracy(List<Double> accuracyList) {
        double sum = 0;
        for (Double aDouble : accuracyList) {
            System.out.println(aDouble);
            sum = sum + aDouble;
        }
        System.out.println("Accuracy = " + sum / accuracyList.size());
        System.out.println("Error = " + (100 - (sum / accuracyList.size())));
    }

    private static ArrayList<Example> makeTrainData(ArrayList<Example> dataSet, String label, Data data) {

        for (Example example : dataSet) {
            if (example.getActualLabel().equals(label)) {
                example.setLabel(1);
            } else {
                example.setLabel(0);
            }
        }
        return dataSet;
    }

    private static void findDistinctValuesInCols(Data data, ArrayList<Example> dataSet) {

        Set<Double> uniQueValues = new HashSet<Double>();
        for (int i = 0; i < data.getNumberOfCols(); i++) {

            for (Example example : dataSet) {
                double value = example.getValues()[i];
                uniQueValues.add(value);
            }
            data.getDistinctValuesPerColumn()[i] = uniQueValues.size();
            uniQueValues.clear();
        }
    }

}
