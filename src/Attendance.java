import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attendance {
    private Integer id;
    private Date date;

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getTimeInHours() {
        return timeInHours;
    }

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public Integer getTimeOutHours() {
        return timeOutHours;
    }

    public Integer getTimeOutMinutes() {
        return timeOutMinutes;
    }

    private Integer timeInHours;
    private Integer timeInMinutes;
    private Integer timeOutHours;
    private Integer timeOutMinutes;

    public Attendance(String id, String date, String timeIn, String timeOut) {
        this.id = Integer.parseInt(id);
        try {
            this.date = new SimpleDateFormat("MM/dd/yyyy").parse(date);

            String[] timeComponentsIn = timeIn.split(":");
            this.timeInHours = Integer.parseInt(timeComponentsIn[0]);
            this.timeInMinutes = Integer.parseInt(timeComponentsIn[1]);

            String[] timeComponentsOut = timeOut.split(":");
            this.timeOutHours = Integer.parseInt(timeComponentsOut[0]);
            this.timeOutMinutes = Integer.parseInt(timeComponentsOut[1]);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Double getHoursWorked() {
        double timeIn = (this.timeInHours * 3600) + (this.timeInMinutes * 60);
        double timeOut = (this.timeOutHours * 3600) + (this.timeOutMinutes * 60);
        return (timeOut - timeIn) / 3600 - 1;
    }
}
