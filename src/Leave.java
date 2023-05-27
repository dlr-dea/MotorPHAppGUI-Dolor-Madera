import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Leave {
    Integer id;
    Date startDate;
    Date endDate;
    String type;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");

    public Leave(String id, String startDate, String endDate, String type) {
        this.id = Integer.parseInt(id.replaceAll("\"", ""));
        this.startDate = this.stringToDate(startDate);
        this.endDate = this.stringToDate(endDate);
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStringEndDate() {
        return dateFormat.format(endDate);
    }

    public String getStringStartDate() {
        return dateFormat.format(startDate);
    }
    
    public String getType() {
        return type.replaceAll("\"", "");
    }

    public int getDays() throws IOException {
        long days = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());

        return (int) days+1;
    }

    private Date stringToDate(String strDate) {
        Date date;
        try {
            date = dateFormat.parse(strDate.replaceAll("\"", ""));
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;

    }

    public String getCSVValue() {
        return "\"" + String.join("\",\"",
            String.valueOf(this.getId()),
            String.valueOf(this.getStringStartDate()),
            String.valueOf(this.getStringEndDate()),
            this.getType()
        ) + "\"\n";
    }

}
