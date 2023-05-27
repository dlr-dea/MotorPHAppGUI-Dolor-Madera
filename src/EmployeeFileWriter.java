import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmployeeFileWriter {
    String basePath = new File("src").getAbsolutePath();
    String pathToFile = basePath + "/employees.csv";

    public void write(ArrayList<Employee> employees) {
        File file = new File(pathToFile);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            String csvValue = "";
            for (Employee emp : employees) {
                String value = emp.getCSVValue();
                csvValue += value.trim() + "\n";
            }
            writer.write(csvValue.trim());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
