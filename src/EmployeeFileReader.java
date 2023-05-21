import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmployeeFileReader {

    private ArrayList<Employee> employees;

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    private String cleanStrValue(String str) {
        String cleanStr = str.replaceAll(",", "");
        cleanStr = cleanStr.replaceAll("\"", "");
        return cleanStr;
    }

    public EmployeeFileReader() {
        this.employees = new ArrayList<Employee>();
        this.readCSVContents();
    }

    private void readCSVContents() {

        String basePath = new File("src").getAbsolutePath();
        String pathToFile = basePath + "/employees.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                Employee employee = new Employee(
                        Integer.parseInt(this.cleanStrValue(values[0])),
                        values[1].replaceAll("\"", ""),
                        values[2].replaceAll("\"", ""),
                        values[3].replaceAll("\"", ""),
                        values[4].replaceAll("\"", ""),
                        values[5].replaceAll("\"", ""),
                        values[6].replaceAll("\"", ""),
                        values[7].replaceAll("\"", ""),
                        values[8].replaceAll("\"", ""),
                        values[9].replaceAll("\"", ""),
                        values[10].replaceAll("\"", ""),
                        values[11].replaceAll("\"", ""),
                        values[12].replaceAll("\"", ""),
                        Double.parseDouble(this.cleanStrValue(values[13])),
                        Double.parseDouble(this.cleanStrValue(values[14])),
                        Double.parseDouble(this.cleanStrValue(values[15])),
                        Double.parseDouble(this.cleanStrValue(values[16])),
                        Double.parseDouble(this.cleanStrValue(values[17])),
                        Double.parseDouble(this.cleanStrValue(values[18]))
                );

                this.addEmployee(employee);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
