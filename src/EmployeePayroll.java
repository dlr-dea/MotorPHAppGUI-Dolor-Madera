import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EmployeePayroll extends JPanel {

    public EmployeePayroll(Employee employee) {
        setLayout(new GridLayout(0, 1));
        Border border = BorderFactory.createTitledBorder("Payroll");
        setBorder(border);

        String[] choices = {
                "September 2022 W1",
                "September 2022 W2",
                "September 2022 W3",
                "September 2022 W4",
                "September 2022 W5",
                "October 2022 W1",
                "October 2022 W2",
                "October 2022 W3",
                "October 2022 W4",
                "October 2022 W5",
                "November 2022 W1",
                "November 2022 W2",
                "November 2022 W3",
                "November 2022 W4",
                "November 2022 W5",
                "December 2022 W1",
                "December 2022 W2",
                "December 2022 W3",
                "December 2022 W4",
                "December 2022 W5",
        };
        final JComboBox<String> cbPayrollPeriod = new JComboBox<String>(choices);

        JButton btnSubmit = new JButton("View");

        JPanel payrollPeriodPanel = new JPanel(new FlowLayout());

        payrollPeriodPanel.add(this.createLabel("Payroll Period"));
        payrollPeriodPanel.add(cbPayrollPeriod);
        payrollPeriodPanel.add(btnSubmit);

        add(payrollPeriodPanel);

        JTextField tfHoursWorked = this.createTextField("");
        JTextField tfGrossPay = this.createTextField("");
        JTextField tfDeductions = this.createTextField("");
        JTextField tfAllowances = this.createTextField("");
        JTextField tfNetPay = this.createTextField("");

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                DateTimeFormatter format = new DateTimeFormatterBuilder()
                        .appendPattern("MMMM uuuu 'W'W") // or use corresponding appends
                        .parseDefaulting(ChronoField.DAY_OF_WEEK, DayOfWeek.SUNDAY.getValue())
                        .toFormatter()
                        .withLocale(Locale.US);

                String selectedPeriod = String.valueOf(cbPayrollPeriod.getSelectedItem());
                LocalDate date = LocalDate.parse(selectedPeriod, format);

                LocalDate localStartDate = date;
                LocalDate localEndDate = date.plusDays(6);

                EmployeeAttendanceReader reader = new EmployeeAttendanceReader();
                ArrayList<Attendance> attendances = reader.getAttendance(employee.getId());
                Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                Double hoursWorked = 0.0;
                for (Attendance att : attendances) {
                    Date currentDate = att.getDate();

                    if (currentDate.after(startDate) && currentDate.before(endDate)) {
                        hoursWorked += att.getHoursWorked();
                    }
                }

                Double grossPay = hoursWorked * employee.getHourlyRate();
                Double allowance = employee.getWeeklyAllowance();
                Double deductions = employee.getWeeklyDeductions();
                Double netPay = (grossPay + allowance) - deductions;
                if (grossPay.equals(0.0)) {
                    deductions = 0.0;
                    allowance = 0.0;
                    netPay = 0.0;
                }

                if (netPay < 0) {
                    deductions = 0.0;
                }

                tfHoursWorked.setText(EmployeePayroll.this.currency(hoursWorked));
                tfGrossPay.setText(EmployeePayroll.this.currency(grossPay));
                tfDeductions.setText(EmployeePayroll.this.currency(deductions));
                tfAllowances.setText(EmployeePayroll.this.currency(allowance));
                tfNetPay.setText(EmployeePayroll.this.currency(netPay));
            }
        });

        add(this.createLabel("Hours Worked"));
        add(tfHoursWorked);

        add(this.createLabel("Gross Pay"));
        add(tfGrossPay);

        add(this.createLabel("Deductions"));
        add(tfDeductions);

        add(this.createLabel("Allowances"));
        add(tfAllowances);

        add(this.createLabel("Net Pay"));
        add(tfNetPay);
    }

    private JLabel createLabel(String label) {
        return new JLabel(label);
    }

    private JTextField createTextField(String value) {
        JTextField tf = new JTextField(value);
        tf.setEnabled(false);
        return tf;
    }

    private String currency(double amount) {
        return String.format("%.2f", amount);
    }

}
