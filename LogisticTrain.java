package LogisticRegression;

import java.util.ArrayList;

public class LogisticTrain {

    double weightVector[];
    double gradientVector[];
    private double learningRate;

    public LogisticTrain(int length) {
        weightVector = new double[length];
        gradientVector = new double[length];
        this.learningRate = 0.001;
    }

    public void train(ArrayList<TrainingExample> dataSet) {
        initializeVectorsToZERO();
        for (TrainingExample trainingExample : dataSet) {
            double probabilityPerExample = calculateSigmoid(trainingExample.getValues(), weightVector);
            System.out.println("probabilityPerExample = " + probabilityPerExample);
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
}
