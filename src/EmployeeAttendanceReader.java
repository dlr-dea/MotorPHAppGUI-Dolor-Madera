import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EmployeeAttendanceReader {

    private ArrayList<Attendance> attendance;

    public ArrayList<Attendance> getAttendance(Integer id) {
        ArrayList<Attendance> filteredList = new ArrayList<Attendance>();

        for(Attendance att: this.attendance) {
            if(id.equals(att.getId())) {
                filteredList.add(att);
            }
        }
        return filteredList;
    }

    public EmployeeAttendanceReader() {
        this.attendance = new ArrayList<Attendance>();
        this.readCSVContents();
    }

    private void readCSVContents() {

        String basePath = new File("src").getAbsolutePath();
        String pathToFile = basePath + "/attendance.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                Attendance attendance = new Attendance(
                    values[0],
                    values[3],
                    values[4],
                    values[5]
                );

                this.attendance.add(attendance);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
