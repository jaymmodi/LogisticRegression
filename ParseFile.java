package LogisticRegression;


import java.io.*;
import java.util.ArrayList;

public class ParseFile {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String trainingSetPath = br.readLine();
            br.close();

            int classifyLabel = 1;

            ArrayList<TrainingExample> dataSet = createDataSet(classifyLabel, trainingSetPath);

            System.out.println(dataSet.size());
            LogisticTrain logisticTrain = new LogisticTrain(dataSet.get(0).getValues().length);
            logisticTrain.train(dataSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<TrainingExample> createDataSet(int classifyLabel, String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        int index = 1;
        ArrayList<TrainingExample> examples = new ArrayList<TrainingExample>();

        while ((line = br.readLine()) != null) {
            String perLine[] = line.split(",");

            TrainingExample trainingExample = new TrainingExample();
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
