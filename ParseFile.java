package LogisticRegression;


import java.io.*;
import java.util.ArrayList;

public class ParseFile {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<TrainingExample> examples = new ArrayList<TrainingExample>();
        try {
            String trainingSetPath = br.readLine();
            FileReader fileReader = new FileReader(new File(trainingSetPath));

            br = new BufferedReader(fileReader);
            String line = null;

            int index = 1;
            int classifyLabel = 1;

            while ((line = br.readLine()) != null) {
                String perLine[] = line.split(",");

                TrainingExample trainingExample = new TrainingExample();
                trainingExample.setIndex(index);
                int[] values = new int[perLine.length - 1];

                for (int i = 0; i < perLine.length; i++) {
                    if (i == perLine.length - 1) {
                        if ((Integer.parseInt(perLine[i]) != classifyLabel)) {
                            trainingExample.setLabel(0);
                        } else {
                            trainingExample.setLabel(classifyLabel);
                        }
                    } else {
                        values[i] = Integer.parseInt(perLine[i]);
                    }
                }
                index++;
                trainingExample.setValues(values);
                examples.add(trainingExample);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(examples.size());

    }
}
