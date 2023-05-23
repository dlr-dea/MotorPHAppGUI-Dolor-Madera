import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.swing.table.TableModel;

public class EmployeesList extends JFrame {

    String selectedId = "";
    Employee selectedEmployee;
    ArrayList<Employee> employees;
    JTable employeeTable;

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
        this.employees = employeeFileReader.getEmployees();

        for (Employee employee : this.employees) {
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

        employeeTable = new JTable(tableModel);

        JButton btnViewMoreData = new JButton("View More Data");
        JButton btnDeleteEmployee = new JButton("Delete");
        JButton btnUpdateEmployee = new JButton("Update");
        btnViewMoreData.setEnabled(false);
        btnDeleteEmployee.setEnabled(false);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                String selected = employeeTable.getValueAt(employeeTable.getSelectedRow(), 0).toString();
                EmployeesList.this.selectedId = selected;

                if (!selected.isEmpty()) {
                    btnViewMoreData.setEnabled(true);
                    btnDeleteEmployee.setEnabled(true);
                } else {
                    btnDeleteEmployee.setEnabled(false);
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
                        JPanel profilePanel = new JPanel();
                        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

                        profilePanel.add(employeeProfile);
                        profilePanel.add(btnUpdateEmployee);

                        btnUpdateEmployee.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Employee updatedEmp = employeeProfile.getUpdatedEmployee();
                                Object[] updatedValues = {
                                        String.valueOf(updatedEmp.getId()),
                                        updatedEmp.getLastName(),
                                        updatedEmp.getFirstName(),
                                        updatedEmp.getSssNo(),
                                        updatedEmp.getPhilhealthNo(),
                                        updatedEmp.getTin(),
                                        updatedEmp.getPagIbigNo()
                                };

                                for (int i = 0; i < updatedValues.length; i++) {
                                    Object newValue = updatedValues[i];
                                    tableModel.setValueAt(newValue, employeeTable.getSelectedRow(), i);
                                }

                                ArrayList<Employee> updatedEmployees = new ArrayList<Employee>();
                                for (Employee emp : EmployeesList.this.employees) {
                                    String id = String.valueOf(emp.getId());
                                    if (id.matches(EmployeesList.this.selectedId)) {
                                        updatedEmployees.add(updatedEmp);
                                    } else {
                                        updatedEmployees.add(emp);
                                    }
                                }
                                EmployeeFileWriter writer = new EmployeeFileWriter();
                                writer.write(updatedEmployees);

                                employees = updatedEmployees;

                                JOptionPane.showMessageDialog(frame, "Employee updated!");

                            }
                        });

                        panel.add(profilePanel);
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

        btnDeleteEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Employee> updatedEmployees = new ArrayList<Employee>();
                // remove all
                if (employeeTable.getSelectedRow() != -1) {
                    try {
                        tableModel.removeRow(employeeTable.getSelectedRow());

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(EmployeesList.this, "Selected row deleted successfully");
                }

                for (Employee emp : EmployeesList.this.employees) {
                    String id = String.valueOf(emp.getId());
                    if (!id.matches(EmployeesList.this.selectedId)) {
                        updatedEmployees.add(emp);
                    }
                }
                btnDeleteEmployee.setEnabled(false);
                btnViewMoreData.setEnabled(false);

                EmployeeFileWriter writer = new EmployeeFileWriter();
                writer.write(updatedEmployees);
            }
        });

        JScrollPane sp = new JScrollPane(employeeTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnViewMoreData);
        buttonPanel.add(btnDeleteEmployee);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(sp);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
    }

}
