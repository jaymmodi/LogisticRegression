package LogisticRegression;


public class Example {

    int index;
    double values[];
    int label;
    String actualLabel;
    String predictedLabel;

    public String getPredictedLabel() {
        return predictedLabel;
    }

    public void setPredictedLabel(String predictedLabel) {
        this.predictedLabel = predictedLabel;
    }

    public Example() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getActualLabel() {
        return actualLabel;
    }

    public void setActualLabel(String actualLabel) {
        this.actualLabel = actualLabel;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
