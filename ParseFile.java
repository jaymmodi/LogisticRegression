package LogisticRegression;


import java.io.*;
import java.util.ArrayList;

public class ParseFile {

    public static void main(String[] args) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File("input.txt"));
            br = new BufferedReader(fr);

            String trainingSetPath = br.readLine();
            String testSetPath = br.readLine();

            fr.close();
            br.close();

            int classifyLabel = 1;
            ArrayList<Example> trainingDataSet = new ArrayList<Example>();
            trainingDataSet = createDataSet(trainingDataSet, classifyLabel, trainingSetPath);

            System.out.println(trainingDataSet.size());
            LogisticTrain logisticTrain = new LogisticTrain(trainingDataSet.get(0).getValues().length);
            logisticTrain.train(trainingDataSet);

            ArrayList<Example> testDataSet = new ArrayList<Example>();
            testDataSet = createDataSet(testDataSet, classifyLabel, testSetPath);

            logisticTrain.classify(testDataSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Example> createDataSet(ArrayList<Example> examples, int classifyLabel, String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int index = 1;

        while ((line = br.readLine()) != null) {
            String perLine[] = line.split(",");

            Example example = new Example();
            example.setIndex(index);
            double[] values = new double[perLine.length - 1];

            for (int i = 0; i < perLine.length; i++) {
                if (i == perLine.length - 1) {
                    if ((Double.parseDouble(perLine[i]) != classifyLabel)) {
                        example.setLabel(0);
                    } else {
                        example.setLabel(classifyLabel);
                    }
                } else {
                    values[i] = Double.parseDouble(perLine[i]);
                }
            }
            index++;
            example.setValues(values);
            examples.add(example);
        }
        br.close();
        return examples;
    }

}
