package LogisticRegression;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LogisticTrain {

    double weightVector[];
    double gradientVector[];
    private double learningRate;

    List<double[]> allWeightVectorList;

    public LogisticTrain(int length) {
        weightVector = new double[length];
        gradientVector = new double[length];
        allWeightVectorList = new ArrayList<double[]>();
        this.learningRate = 0.001;
    }

    public void train(ArrayList<Example> dataSet) {
        initializeVectorsToZERO();
        printVector(weightVector);
        for (Example example : dataSet) {
            double probabilityPerExample = calculateSigmoid(example.getValues(), weightVector);

            double error = example.getLabel() - probabilityPerExample;

            for (int i = 0; i < example.getValues().length; i++) {
                gradientVector[i] += error * example.values[i];
            }

            updateWeightVector(gradientVector);
            printVector(weightVector);
        }
        addToListOfAllWeightVectors(weightVector);
    }

    private void addToListOfAllWeightVectors(double[] weightVector) {
        allWeightVectorList.add(weightVector);
    }

    private void printVector(double[] weightVector) {
        System.out.print("weight ->> ");
        for (double aWeightVector : weightVector) {
            System.out.print(roundTo2Decimals(aWeightVector) + ",");
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
            if (calculateSigmoid(testExample.values, weightVector) >= 0.5) {
                System.out.println("Classified as " + 1 + "  Actual value " + testExample.getLabel());
            } else {
                System.out.println("Classified as " + 0 + "  Actual value " + testExample.getLabel());
            }

        }
    }
}
