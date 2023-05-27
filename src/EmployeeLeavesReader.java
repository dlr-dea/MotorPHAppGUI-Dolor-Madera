import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException; 

import java.util.ArrayList;

public class EmployeeLeavesReader {

    private ArrayList<Leave> leaves;

    public ArrayList<Leave> getLeaves(Integer id, String type) {
        ArrayList<Leave> tempLeaves = new ArrayList<Leave>();

        for (Leave leave : leaves) {
            if(leave.getId().equals(id)) {
                tempLeaves.add(leave);
            }
        }

        return tempLeaves;
    }

    public void addLeave(Leave leave) {
        this.leaves.add(leave);
    }

    public EmployeeLeavesReader() {
        this.leaves = new ArrayList<Leave>();
        this.readCSVContents();
    }

    private void readCSVContents() {

        String basePath = new File("src").getAbsolutePath();
        String pathToFile = basePath + "/leaves.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                Leave leave = new Leave(values[0], values[1], values[2],values[3]);

                this.addLeave(leave);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
