package LogisticRegression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jay on 11/28/15.
 */
public class CrossValidation {

    String pathToFile;
    ArrayList<Example> foldTestList;

    public CrossValidation(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public ArrayList<Example> runCrossValidation(int folds, Data data, int foldIndex, boolean isCrossValidation) {
        ArrayList<Example> allExamples = new ArrayList<Example>();
        BufferedReader br;

        Set<String> distinctLabels = new HashSet<String>();
        try {
            br = new BufferedReader(new FileReader(pathToFile));
            setDataSpecificInformation(data, br);
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {
                String perLine[] = line.split(",");

                Example trainingExample = new Example();
                trainingExample.setIndex(index);

                double[] values = new double[perLine.length - 1];

                for (int i = 0; i < perLine.length; i++) {
                    if (i == perLine.length - 1) {
                        trainingExample.setActualLabel(perLine[i]);
                        distinctLabels.add(perLine[i]);
                    } else {
                        values[i] = Double.parseDouble(perLine[i]);
                    }
                }

                index++;
                trainingExample.setValues(values);
                allExamples.add(trainingExample);
            }
            br.close();
            data.setNumberOfClassLabels(distinctLabels.size());
            data.setDistinctLabels(distinctLabels);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return makeDataSet(allExamples, folds, foldIndex);
    }

    private ArrayList<Example> makeDataSet(ArrayList<Example> allExamples, int folds, int foldIndex) {
        if (folds == 1) {
            return allExamples;
        }

        List<ArrayList<Example>> listOfSets = new ArrayList<ArrayList<Example>>();

        divideSets(listOfSets, allExamples, folds);


        this.foldTestList = listOfSets.get(foldIndex);

        int trainIndex = 0;
        ArrayList<Example> trainFold = new ArrayList<Example>();
        for (ArrayList<Example> examples : listOfSets) {

            if (trainIndex != foldIndex) {
                trainFold.addAll(examples);
            }
            trainIndex++;
        }

        return trainFold;
    }

    private void divideSets(List<ArrayList<Example>> listOfSets, ArrayList<Example> allExamples, int folds) {

        int foldSize = allExamples.size() / folds;


        int start = 0;
        int end = foldSize;

        while (start < allExamples.size()) {
            ArrayList<Example> foldList = new ArrayList<Example>();
            for (int i = start; i < end; i++) {
                foldList.add(allExamples.get(i));
            }
            listOfSets.add(foldList);
            start = start + foldSize;
            end = end + foldSize;
        }
    }


    private static void setDataSpecificInformation(Data data, BufferedReader br) {
        String str;
        try {
            str = br.readLine();
            String[] perLine = str.split(",");

            data.setNumberOfCols(perLine.length - 1);

            String[] colNames = new String[perLine.length - 1];
            for (int j = 0; j < perLine.length - 1; j++) {
                colNames[j] = perLine[j];
            }

            data.setNamesOfColumns(colNames);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
