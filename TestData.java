package LogisticRegression;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jay on 11/29/15.
 */
public class TestData {

    private String pathToFile;

    public TestData(String pathToFile) {
        this.pathToFile = pathToFile;
    }


    public ArrayList<Example> testData() {

        ArrayList<Example> test = new ArrayList<Example>();
        BufferedReader br;


        try {
            br = new BufferedReader(new FileReader(pathToFile));
            String line;
            String ignoreLine = br.readLine();
            int index = 0;

            while ((line = br.readLine()) != null) {
                String perLine[] = line.split(",");

                Example testExample = new Example();
                testExample.setIndex(index);

                double[] values = new double[perLine.length - 1];

                for (int i = 0; i < perLine.length; i++) {
                    if (i == perLine.length - 1) {
                        testExample.setActualLabel(perLine[i]);
                    } else {
                        values[i] = Double.parseDouble(perLine[i]);
                    }
                }

                index++;
                testExample.setValues(values);
                test.add(testExample);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }
}
