package LogisticRegression;


import java.io.*;

public class ParseFile {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String trainingSetPath = br.readLine();
            FileReader fileReader = new FileReader(new File(trainingSetPath));

            br = new BufferedReader(fileReader);
            String line = null;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
