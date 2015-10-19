package LogisticRegression;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LogisticTrain {

    double weightVector[];
    double gradientVector[];
    private double learningRate;

    double lastWeightVector[];


    public LogisticTrain(int length) {
        weightVector = new double[length];
        gradientVector = new double[length];
        lastWeightVector = new double[length];
        this.learningRate = 0.001;
    }

    public void train(ArrayList<Example> dataSet) {
        initializeVectorsToZERO(gradientVector);
        initializeVectorsToZERO(weightVector);
        while (true) {

            initializeVectorsToZERO(gradientVector);
            storeLastWeightVector(lastWeightVector, weightVector);

            for (Example trainingExample : dataSet) {
                double probabilityPerExample = calculateSigmoid(trainingExample.getValues());

                double error = trainingExample.getLabel() - probabilityPerExample;

                for (int i = 0; i < trainingExample.getValues().length; i++) {
                    gradientVector[i] += error * trainingExample.values[i];
                }

                updateWeightVector();
            }
            if (Math.abs(calculateDifference(weightVector, lastWeightVector)) <= 0.0001) {
                break;
            }
        }
    }

    private void storeLastWeightVector(double[] lastWeightVector, double[] weightVector) {
        for (int i = 0; i < weightVector.length; i++) {
            lastWeightVector[i] = weightVector[i];
        }
    }


    private double calculateDifference(double[] weightVector, double[] lastWeightVector) {
        double difference = 0;
        for (int i = 0; i < weightVector.length; i++) {
            difference = difference + (weightVector[i] - lastWeightVector[i]);
        }
        return difference;
    }

    private void printVector(double[] weightVector) {
        System.out.print("weight => ");
        for (double aWeightVector : weightVector) {
            System.out.print(aWeightVector + ",");
        }
        System.out.println();
    }

    private void updateWeightVector() {
        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] += learningRate * gradientVector[i];
        }
    }

    private void initializeVectorsToZERO(double[] vector) {
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

    public void classify(ArrayList<Example> testDataSet) {
        int truePositive = 0;
        int trueNegaitve = 0;
        int falsePositive = 0;
        int falseNegative = 0;

        for (Example testExample : testDataSet) {
            double probabilityOfClassification = calculateSigmoid(testExample.values);
            if (probabilityOfClassification >= 0.5) {
                if (testExample.getLabel() == 1) {
                    truePositive++;
                } else {
                    falsePositive++;
                }
            } else {
                if (testExample.getLabel() == 1) {
                    falseNegative++;
                } else {
                    trueNegaitve++;
                }
            }
        }

        createConfusionMatrix(truePositive, falsePositive, trueNegaitve, falseNegative);
    }

    private void createConfusionMatrix(int truePositive, int falsePositive, int trueNegaitve, int falseNegative) {
        System.out.println("--------------------------------------------------");
        System.out.println("                  Actual                          ");
        System.out.println("--------------------------------------------------");
        System.out.println("           0           |           1              ");
        System.out.println("--------------------------------------------------");
        System.out.println("| P |   |              |                           ");
        System.out.println("| R |   |              |                           ");
        System.out.println("| E | 0 |   " + trueNegaitve + "         |  " + falseNegative + "                         ");
        System.out.println("| D |   |              |                           ");
        System.out.println("| I |---|-----------------------------------------");
        System.out.println("| C |   |              |                           ");
        System.out.println("| T |   |              |                          ");
        System.out.println("| E | 1 |   " + falsePositive + "          |   " + truePositive + "                        ");
        System.out.println("| D |   |              |                           ");
    }

}
