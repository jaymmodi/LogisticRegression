package LogisticRegression;

import java.util.Map;
import java.util.Set;

/**
 * Created by jay on 11/25/15.
 */
public class Data {

    int numberOfCols;
    int numberOfClassLabels;
    int[] distinctValuesPerColumn;
    String[] namesOfColumns;
    Set<String> distinctLabels;
    Map<String,Integer> labelCount;

    public Map<String, Integer> getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(Map<String, Integer> labelCount) {
        this.labelCount = labelCount;
    }


    public Set<String> getDistinctLabels() {
        return distinctLabels;
    }

    public void setDistinctLabels(Set<String> distinctLabels) {
        this.distinctLabels = distinctLabels;
    }

    public String[] getNamesOfColumns() {
        return namesOfColumns;
    }

    public void setNamesOfColumns(String[] namesOfColumns) {
        this.namesOfColumns = namesOfColumns;
    }


    public int getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(int numberOfCols) {
        this.numberOfCols = numberOfCols;
        this.distinctValuesPerColumn = new int[numberOfCols];
    }

    public int getNumberOfClassLabels() {
        return numberOfClassLabels;
    }

    public void setNumberOfClassLabels(int numberOfClassLabels) {
        this.numberOfClassLabels = numberOfClassLabels;
    }

    public int[] getDistinctValuesPerColumn() {
        return distinctValuesPerColumn;
    }

    public void setDistinctValuesPerColumn(int[] distinctValuesPerColumn) {
        this.distinctValuesPerColumn = distinctValuesPerColumn;
    }
}
