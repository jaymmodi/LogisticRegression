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
        initializeVectorsToZERO();
        printVector(weightVector);
        boolean flag = false;

        while (true) {
//            storeLastWeightVector(lastWeightVector,weightVector);
            for (Example trainingExample : dataSet) {
                double probabilityPerExample = calculateSigmoid(trainingExample.getValues(), weightVector);

                double error = trainingExample.getLabel() - probabilityPerExample;

                for (int i = 0; i < trainingExample.getValues().length; i++) {
                    gradientVector[i] += error * trainingExample.values[i];
                }
                storeLastWeightVector(lastWeightVector, weightVector);
                updateWeightVector(gradientVector);
//                printVector(weightVector);


                if (Math.abs(calculateDifference(weightVector, lastWeightVector)) <= 0.0001) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }

        }
    }

    private void storeLastWeightVector(double[] lastWeightVector, double[] weightVector) {
        for (int i = 0; i < weightVector.length; i++) {
            lastWeightVector[i] = weightVector[i];
        }
    }


    private double calculateDifference(double[] weightVector, double[] lastWeigthVector) {
        double difference = 0;
        for (int i = 0; i < weightVector.length; i++) {
            difference = difference + (weightVector[i] - lastWeigthVector[i]);
        }
        System.out.println(difference);
        return difference;
    }

    private void printVector(double[] weightVector) {
        System.out.print("weight  ");
        for (double aWeightVector : weightVector) {
            System.out.print(aWeightVector + ",");
        }
        System.out.println();
    }

    private void updateWeightVector(double[] gradientVector) {
        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] += learningRate * gradientVector[i];
        }
    }

    private void initializeVectorsToZERO() {
        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] = 0.0;
        }

        for (int i = 0; i < gradientVector.length; i++) {
            gradientVector[i] = 0.0;
        }
    }

    private double calculateSigmoid(double[] values, double[] weight) {
        double dotProduct = 0;
        for (int i = 0; i < weight.length; i++) {
            dotProduct += values[i] * weight[i];
        }

        return 1 / (1 + Math.exp(-dotProduct));
    }

    double roundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    public void classify(ArrayList<Example> testDataSet) {
        for (Example testExample : testDataSet) {
            double probabilityOfClassification = calculateSigmoid(testExample.values, weightVector);
            if (probabilityOfClassification >= 0.5) {
                System.out.println("Classified as " + 1 + "  Actual value " + testExample.getLabel());
            } else {
                System.out.println("Classified as " + 0 + "  Actual value " + testExample.getLabel());
            }
        }
    }

}
