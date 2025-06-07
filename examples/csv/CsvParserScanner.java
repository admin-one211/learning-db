import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvParserScanner {
    public static void main(String[] args) {
        String filePath = "./piyushCsvFile.csv";
        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int count = 0;
            while((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                for (String value : values) {
                    System.out.print(value + " ");
                }
                count++;
                System.out.println();
            }
            System.out.print(count);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
