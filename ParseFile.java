package LogisticRegression;


import java.io.*;
import java.util.ArrayList;

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
            br.close();

            int classifyLabel = 1;

            ArrayList<Example> dataSet = createDataSet(classifyLabel, trainingSetPath);

            LogisticTrain logisticTrain = new LogisticTrain(dataSet.get(0).getValues().length,learningRate);
            logisticTrain.train(dataSet);

            ArrayList<Example> testDataSet = createDataSet(classifyLabel, testSetPath);
            logisticTrain.classify(testDataSet);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public static ArrayList<Example> createDataSet(int classifyLabel, String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int index = 1;
        ArrayList<Example> examples = new ArrayList<Example>();

        while ((line = br.readLine()) != null) {
            String perLine[] = line.split(",");

            Example trainingExample = new Example();
            trainingExample.setIndex(index);
            double[] values = new double[perLine.length - 1];

            for (int i = 0; i < perLine.length; i++) {
                if (i == perLine.length - 1) {
                    if ((Double.parseDouble(perLine[i]) != classifyLabel)) {
                        trainingExample.setLabel(0);
                    } else {
                        trainingExample.setLabel(classifyLabel);
                    }
                } else {
                    values[i] = Double.parseDouble(perLine[i]);
                }
            }
            index++;
            trainingExample.setValues(values);
            examples.add(trainingExample);
        }
        br.close();
        return examples;
    }

}
