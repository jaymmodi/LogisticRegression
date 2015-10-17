package LogisticRegression;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class ParseFile {

    public static void main(String[] args) {
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(new File("input.txt"));
            br = new BufferedReader(fr);

            String trainingSetPath = br.readLine();
            String testSetPath = br.readLine();

            HashSet<Integer> uniqueLabels = readAllClassifyLabels(br);

            fr.close();
            br.close();

            for (Integer uniqueLabel : uniqueLabels) {

                int classifyLabel = uniqueLabel;

                ArrayList<Example> trainingDataSet = new ArrayList<Example>();
                trainingDataSet = createDataSet(trainingDataSet, classifyLabel, trainingSetPath);

                System.out.println(trainingDataSet.size());
                LogisticTrain logisticTrain = new LogisticTrain(trainingDataSet.get(0).getValues().length);
                logisticTrain.train(trainingDataSet);

                ArrayList<Example> testDataSet = new ArrayList<Example>();
                testDataSet = createDataSet(testDataSet, classifyLabel, testSetPath);

                logisticTrain.classify(testDataSet);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashSet<Integer> readAllClassifyLabels(BufferedReader br) {
        String line;
        HashSet<Integer> uniqueLabels = null;
        try {
            while ((line = br.readLine()) != null) {
                String[] perLine = line.split(",");
                int label = Integer.parseInt(perLine[perLine.length]);
                uniqueLabels.add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueLabels;
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
