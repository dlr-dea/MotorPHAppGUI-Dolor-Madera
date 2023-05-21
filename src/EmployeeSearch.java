import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EmployeeSearch extends JPanel {
    public EmployeeSearch() {
        setLayout(new GridLayout(2, 0));
        setSize(100, 200);
        Border border = BorderFactory.createTitledBorder("Search Employee");
        setBorder(border);

        JButton btnSearch = new JButton("Search");
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JLabel lblEmployeeNo = new JLabel("Employee No:");
        JTextField txtEmployeeNo = new JTextField();
        panel.add(lblEmployeeNo);
        panel.add(txtEmployeeNo);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strEmpNo = txtEmployeeNo.getText().trim();
                if (strEmpNo.isEmpty()) {
                    JOptionPane.showMessageDialog(EmployeeSearch.this, "Employee No. is required",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Boolean noMatchFound = true;

                    EmployeeFileReader employeeFileReader = new EmployeeFileReader();
                    ArrayList<Employee> employees = employeeFileReader.getEmployees();
                    for (Employee emp : employees) {
                        String id = String.valueOf(emp.getId());
                        if (id.matches(strEmpNo)) {
                            noMatchFound = false;

                            EmployeeProfile profile = new EmployeeProfile(emp);
                            JOptionPane.showMessageDialog(EmployeeSearch.this, profile, "Employee Profile",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    }

                    if (noMatchFound) {
                        JOptionPane.showMessageDialog(EmployeeSearch.this, "Please enter a valid employee no.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

                txtEmployeeNo.setText("");

            }
        });

        add(panel);
        add(btnSearch);
    }
}
