package LogisticRegression;

import java.util.*;

public class LogisticTrain {

    double weightVector[];
    double gradientVector[];
    private double learningRate;
    double lastWeightVector[];
    HashMap<String, ArrayList<Double>> modelsMap;
    double previousCost;


    public LogisticTrain(int length, double learningRate, Data data) {
        weightVector = new double[length];
        gradientVector = new double[length];
        lastWeightVector = new double[length];
        this.learningRate = learningRate;
        modelsMap = new HashMap<String, ArrayList<Double>>();
    }

    public void train(ArrayList<Example> dataSet, String label) {

        System.out.println("Learning Rate = " + learningRate);
        initializeVectorToZERO(gradientVector);
        initializeVectorToZERO(weightVector);

        int iter = 1;
        while (true) {
            initializeVectorToZERO(gradientVector);
//            printVector(iter,weightVector);
            storeLastWeightVector(lastWeightVector, weightVector);

            double cost = 0.0;

            for (Example trainingExample : dataSet) {
                double probabilityPerExample = calculateSigmoid(trainingExample.getValues());

                cost = calculateCostForInstance(cost, probabilityPerExample, trainingExample.getLabel(), dataSet.size());

                double error = trainingExample.getLabel() - probabilityPerExample;

                for (int i = 0; i < trainingExample.getValues().length; i++) {
                    gradientVector[i] += error * trainingExample.values[i];
                }

            }
            System.out.println(iter + "  " + cost);
            updateWeightVector();

            double diff = getCostDifference(previousCost, cost);
            if (Double.isNaN(cost) || (diff >= 0.001 && diff <= 0.002) || (iter == 5000)) {
                break;
            }

            iter++;
            previousCost = cost;
        }
        ArrayList<Double> list = createList(weightVector);
        modelsMap.put(label, list);
        initializeVectorToZERO(weightVector);

    }

    private ArrayList<Double> createList(double[] weightVector) {
        ArrayList<Double> list = new ArrayList<Double>();

        for (double v : weightVector) {
            list.add(v);
        }
        return list;
    }

    private double calculateCostForInstance(double cost, double probabilityPerExample, int label, int size) {

        double penalty = (-label * Math.log(probabilityPerExample)) - ((1 - label) * Math.log(1 - probabilityPerExample)) / size;

        return penalty + cost;
    }

    private void storeLastWeightVector(double[] lastWeightVector, double[] weightVector) {
        for (int i = 0; i < weightVector.length; i++) {
            lastWeightVector[i] = weightVector[i];
        }
    }


    private boolean calculateDifference(double[] weightVector, double[] lastWeightVector) {
        int count = 0;
        for (int i = 0; i < weightVector.length; i++) {
//            System.out.println(Math.abs(weightVector[i] - lastWeightVector[i]));
            if (Math.abs(weightVector[i] - lastWeightVector[i]) < 0.01) {
                count++;
            }
        }
        return (count == weightVector.length * 0.8);
    }

    private void printVector(int i, double[] weightVector) {
        System.out.print("weight => " + i + " ");
        for (double aWeightVector : weightVector) {
            System.out.print(aWeightVector + ",");
        }
        System.out.println();
    }

    private double getCostDifference(double previousCost, double currentCost) {
        return Math.abs(currentCost - previousCost);
    }

    private void updateWeightVector() {
        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] += learningRate * gradientVector[i];
        }
    }

    private void initializeVectorToZERO(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0.0;
        }

    }

    private double calculateSigmoid(double[] values) {
        double dotProduct = 0;
        for (int i = 0; i < weightVector.length; i++) {
            dotProduct += values[i] * weightVector[i];
        }
        return 1 / (1 + Math.exp(-dotProduct));
    }

    public double classify(ArrayList<Example> testDataSet, HashMap<String, ArrayList<Double>> model) {
        double truePositive = 0;
        double trueNegative = 0;
        double falsePositive = 0;
        double falseNegative = 0;

        int count = 0;
        for (Example example : testDataSet) {
            Map<String, Double> predictionMap = new HashMap<String, Double>();

            for (String s : modelsMap.keySet()) {

                ArrayList<Double> modelWeights = modelsMap.get(s);
                double prediction = predictValue(example.getValues(), modelWeights);

                predictionMap.put(s, prediction);
            }
            Map<String, Double> highestMap = getMax(predictionMap);
            Set<String> keys = highestMap.keySet();

            for (String key : keys) {
                example.setPredictedLabel(key);
            }
            if (example.predictedLabel.equals(example.actualLabel)) {
                count++;
            }
            System.out.println(example.actualLabel + "    " + example.getPredictedLabel());

        }
        System.out.println((double) count / testDataSet.size() * 100);

        return ((double) count) / testDataSet.size() * 100;
//        calculateAccuracy(truePositive, falsePositive, trueNegative, falseNegative);
//        createConfusionMatrix(truePositive, falsePositive, trueNegative, falseNegative);

    }

    private Map<String, Double> getMax(Map<String, Double> predictionMap) {
        Map<String, Double> map = new HashMap<String, Double>();
        String key = "";
        double max = -Double.MIN_VALUE;

        for (String s : predictionMap.keySet()) {
            if (predictionMap.get(s) > max) {
                max = predictionMap.get(s);
                key = s;
            }
        }
        map.put(key, max);
        return map;
    }

    private double predictValue(double[] values, ArrayList<Double> modelWeights) {
        double dotProduct = 0;
        for (int i = 0; i < modelWeights.size(); i++) {
            dotProduct += values[i] * modelWeights.get(i);
        }
        return 1 / (1 + Math.exp(-dotProduct));
    }

    private void calculateAccuracy(double truePositive, double falsePositive, double trueNegative, double falseNegative) {
        double accuracy = (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative) * 100;

        System.out.println("Accuracy =  " + accuracy);
    }

    private void createConfusionMatrix(double truePositive, double falsePositive, double trueNegative, double falseNegative) {
//        printVector(i, weightVector);
        System.out.println("Confusion Matrix ");


        System.out.println("--------------------------------------------------");
        System.out.println("                  Actual                          ");
        System.out.println("--------------------------------------------------");
        System.out.println("           0           |           1              ");
        System.out.println("--------------------------------------------------");
        System.out.println("| P |   |              |                           ");
        System.out.println("| R |   |              |                           ");
        System.out.println("| E | 0 |   " + trueNegative + "       |  " + falseNegative + "                         ");
        System.out.println("| D |   |              |                           ");
        System.out.println("| I |---|-----------------------------------------");
        System.out.println("| C |   |              |                           ");
        System.out.println("| T |   |              |                          ");
        System.out.println("| E | 1 |   " + falsePositive + "        |   " + truePositive + "                        ");
        System.out.println("| D |   |              |                           ");
    }

    public void printModels() {
        for (String s : modelsMap.keySet()) {
            System.out.println(s + " =>");
            ArrayList<Double> weights = modelsMap.get(s);
            for (Double weight : weights) {
                System.out.print(weight + ",");
            }
            System.out.println("");
        }
    }
}
