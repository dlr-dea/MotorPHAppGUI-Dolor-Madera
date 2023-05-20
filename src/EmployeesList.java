import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EmployeesList extends JFrame {

    ArrayList<Employee> employees;

    public EmployeesList() {
        super("MotorPH Employees");
        setSize(500, 300);

        JPanel container = new JPanel();
        GridLayout containerLayout = new GridLayout(0, 1, 0, 25);
        container.setLayout(containerLayout);
        JScrollPane scrPane = new JScrollPane(container);

        EmployeeFileReader employeeFileReader = new EmployeeFileReader();
        ArrayList<Employee> employees = employeeFileReader.getEmployees();

        for (Employee employee : employees) {
            EmployeeProfile employeeProfilePanel = new EmployeeProfile(employee);
            container.add(employeeProfilePanel);
        }

        add(scrPane);

        pack();
    }
}
