package LogisticRegression;

import java.util.ArrayList;

public class LogisticTrain {

    double weightVector[];
    double gradientVector[];
    private double learningRate;

    double lastWeightVector[];


    public LogisticTrain(int length, double learningRate) {
        weightVector = new double[length];
        gradientVector = new double[length];
        lastWeightVector = new double[length];
        this.learningRate = learningRate;
    }

    public void train(ArrayList<Example> dataSet) {

        System.out.println("Learning Rate = " + learningRate);
        initializeVectorToZERO(gradientVector);
        initializeVectorToZERO(weightVector);

        while (true) {
            initializeVectorToZERO(gradientVector);
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

    public void classify(ArrayList<Example> testDataSet) {
        double truePositive = 0;
        double trueNegative = 0;
        double falsePositive = 0;
        double falseNegative = 0;

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
                    trueNegative++;
                }
            }
        }
        calculateAccuracy(truePositive, falsePositive, trueNegative, falseNegative);
        createConfusionMatrix(truePositive, falsePositive, trueNegative, falseNegative);
    }

    private void calculateAccuracy(double truePositive, double falsePositive, double trueNegative, double falseNegative) {
        double accuracy = (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative) * 100;

        System.out.println("Accuracy =  " + accuracy);
    }

    private void createConfusionMatrix(double truePositive, double falsePositive, double trueNegative, double falseNegative) {
        printVector(weightVector);
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

}
