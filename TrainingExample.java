package LogisticRegression;


public class TrainingExample {

    int index;
    Integer values[];
    int label;

    public TrainingExample(int index, Integer[] values, int label) {
        this.index = index;
        this.values = values;
        this.label = label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer[] getValues() {
        return values;
    }

    public void setValues(Integer[] values) {
        this.values = values;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
