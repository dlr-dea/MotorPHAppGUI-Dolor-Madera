import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EmployeeLeavesWriter {
    String basePath = new File("src").getAbsolutePath();
    String pathToFile = basePath + "/leaves.csv";

    public void write(Leave leave) {
        File file = new File(pathToFile);
        FileWriter writer;
        try {
            writer = new FileWriter(file, true);
            writer.write(leave.getCSVValue());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
