import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class EmployeesList extends JFrame {

    String selectedId = "";
    Employee selectedEmployee;

    public EmployeesList() {
        super("MotorPH Employees");
        setPreferredSize(new Dimension(800, 300));

        EmployeeFileReader employeeFileReader = new EmployeeFileReader();

        String columns[] = {
                "Employee Number",
                "Last Name",
                "First Name",
                "SSS",
                "PhilHealth",
                "TIN",
                "Pagibig"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        ArrayList<Employee> employees = employeeFileReader.getEmployees();

        for (Employee employee : employees) {
            Object[] objEmployee = {
                    String.valueOf(employee.getId()),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getSssNo(),
                    employee.getPhilhealthNo(),
                    employee.getTin(),
                    employee.getPagIbigNo()
            };

            tableModel.addRow(objEmployee);
        }

        JTable employeeTable = new JTable(tableModel);

        JButton btnViewMoreData = new JButton("View More Data");
        btnViewMoreData.setEnabled(false);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                String selected = employeeTable.getValueAt(employeeTable.getSelectedRow(), 0).toString();
                EmployeesList.this.selectedId = selected;

                if (!selected.isEmpty()) {
                    btnViewMoreData.setEnabled(true);
                } else {
                    btnViewMoreData.setEnabled(false);
                }

            }
        });

        btnViewMoreData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (Employee emp : employees) {
                    String id = String.valueOf(emp.getId());

                    if (id.matches(selectedId)) {
                        EmployeeProfile employeeProfile = new EmployeeProfile(emp);
                        EmployeePayroll employeePayroll = new EmployeePayroll(emp);

                        JFrame frame = new JFrame(emp.getName());

                        JPanel panel = new JPanel();
                        panel.add(employeeProfile);
                        panel.add(employeePayroll);

                        JScrollPane sp = new JScrollPane(panel);

                        frame.setLayout(new GridLayout(1, 0));
                        frame.add(sp);

                        frame.setVisible(true);
                        frame.pack();
                    }
                }

            }
        });

        JScrollPane sp = new JScrollPane(employeeTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnViewMoreData);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(sp);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
    }

}
