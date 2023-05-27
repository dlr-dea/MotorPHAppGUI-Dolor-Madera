import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EmployeeLeave extends JFrame {
    String[] strMonths = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
    };

    final Integer maxSL = 5;
    final Integer maxVL = 10;
    final Integer maxEL = 5;

    Integer id;

    public EmployeeLeave(Integer id) {
        super("Leave Requests for Employee ID: "+id.toString());
        setSize(450, 350);
        this.id = id;

        JLabel labelRemaining = new JLabel("");

        String[] strStartYears = { "2023", "2024", "2025" };
        JComboBox<String> startMonth = new JComboBox<String>(strMonths);
        JComboBox<String> startDate = new JComboBox<String>();
        this.setDays(startDate, "January", "2023");
        JComboBox<String> startYear = new JComboBox<String>(strStartYears);
        startMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeeLeave.this.setDays(startDate, startMonth.getSelectedItem().toString(),
                        startYear.getSelectedItem().toString());
            }
        });
        startYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeeLeave.this.setDays(startDate, startMonth.getSelectedItem().toString(),
                        startYear.getSelectedItem().toString());
            }
        });

        JComboBox<String> endMonth = new JComboBox<String>(strMonths);
        JComboBox<String> endDate = new JComboBox<String>();
        this.setDays(endDate, "January", "2023");
        JComboBox<String> endYear = new JComboBox<String>(strStartYears);
        endMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeeLeave.this.setDays(endDate, endMonth.getSelectedItem().toString(),
                        endYear.getSelectedItem().toString());
            }
        });
        endYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeeLeave.this.setDays(endDate, endMonth.getSelectedItem().toString(),
                        endYear.getSelectedItem().toString());
            }
        });

        String[] strRequestTypes = { "Sick Leave", "Vacation Leave", "Emergency Leave" };
        JComboBox<String> requestTypes = new JComboBox<String>(strRequestTypes);

        setRemainingTexts(labelRemaining);

        JPanel startDatePanel = new JPanel(new FlowLayout());
        startDatePanel.add(this.createLabel("Start Date"));
        startDatePanel.add(startMonth);
        startDatePanel.add(startDate);
        startDatePanel.add(startYear);

        JPanel endDatePanel = new JPanel(new FlowLayout());
        endDatePanel.add(this.createLabel("End Date"));
        endDatePanel.add(endMonth);
        endDatePanel.add(endDate);
        endDatePanel.add(endYear);

        JPanel requestTypePanel = new JPanel(new FlowLayout());
        requestTypePanel.add(this.createLabel("Request Type"));
        requestTypePanel.add(requestTypes);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strStartDate = startMonth.getSelectedItem().toString() + " "
                        + startDate.getSelectedItem().toString() + ", " + startYear.getSelectedItem().toString();
                String strEndDate = endMonth.getSelectedItem().toString() + " " + endDate.getSelectedItem().toString()
                        + ", " + endYear.getSelectedItem().toString();
                String strRequestType = requestTypes.getSelectedItem().toString();
                Leave leave = new Leave(String.valueOf(EmployeeLeave.this.id), strStartDate, strEndDate,
                        strRequestType);

                        EmployeeLeavesReader employeeLeavesReader = new EmployeeLeavesReader();
                        ArrayList<Leave> leaves = employeeLeavesReader.getLeaves(EmployeeLeave.this.id, "");

                        Integer vl = 0;
                        Integer sl = 0;
                        Integer el = 0;
                        try {
                
                            for (Leave tempLeave : leaves) {
                                if (tempLeave.getType().matches("Vacation Leave")) {
                                    vl += tempLeave.getDays();
                                }
                                if (tempLeave.getType().matches("Sick Leave")) {
                                    sl += tempLeave.getDays();
                                }
                                if (tempLeave.getType().matches("Emergency Leave")) {
                                    el += tempLeave.getDays();
                
                                }
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                Integer allowedDays = maxEL - el;
                if (strRequestType.matches("Vacation Leave")) {
                    allowedDays = maxVL - vl;
                }
                if (strRequestType.matches("Sick Leave")) {
                    allowedDays = maxSL - sl;
                }

                try {
                    if(leave.getDays() <= allowedDays) {
                        EmployeeLeavesWriter writer = new EmployeeLeavesWriter();
                        writer.write(leave);

                        
                        setRemainingTexts(labelRemaining);

                    } else {
                        JOptionPane.showMessageDialog(EmployeeLeave.this, "You exceeded the number of allowed leave count",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


            }
        });

        JPanel panel = new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(startDatePanel);
        panel.add(endDatePanel);
        panel.add(requestTypePanel);

        JPanel endPanel = new JPanel();
        endPanel.add(labelRemaining);
        endPanel.add(btnSubmit);

        panel.add(endPanel);

        add(panel);
    }

    private JLabel createLabel(String label) {
        return new JLabel(label);
    }

    private void setDays(JComboBox<String> combobox, String month, String year) {
        combobox.removeAllItems();

        Integer days = 31;

        if (month.matches("April") ||
                month.matches("June") ||
                month.matches("September") ||
                month.matches("November")) {
            days = 30;
        }

        if (month.matches("February")) {
            days = 28;

            if (Integer.parseInt(year) % 4 == 0) {
                days = 29;
            }

        }

        for (int i = 1; i <= days; i++) {
            combobox.addItem(String.valueOf(i));
        }

    }

    private void setRemainingTexts(JLabel label) {
        EmployeeLeavesReader employeeLeavesReader = new EmployeeLeavesReader();
        ArrayList<Leave> leaves = employeeLeavesReader.getLeaves(EmployeeLeave.this.id, "");
        System.out.println(leaves.size());
        Integer vl = 0;
        Integer sl = 0;
        Integer el = 0;
        try {

            for (Leave leave : leaves) {
                if (leave.getType().matches("Vacation Leave")) {
                    vl += leave.getDays();
                }
                if (leave.getType().matches("Sick Leave")) {
                    sl += leave.getDays();
                }
                if (leave.getType().matches("Emergency Leave")) {
                    el += leave.getDays();

                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String strRemaining = "";
        String strSL = String.valueOf(EmployeeLeave.this.maxSL - sl);
        String strEL = String.valueOf(EmployeeLeave.this.maxEL - el);
        String strVL = String.valueOf(EmployeeLeave.this.maxVL - vl);

        strRemaining += "<html>Sick Leave Remaining: " + strSL + "<br/>";
        strRemaining += "Vacation Leave Remaining: " + strVL + "<br/>";
        strRemaining += "Emergency Leave Remaining: " + strEL + "<br/></html>";

        label.setText(strRemaining);
    }


}
