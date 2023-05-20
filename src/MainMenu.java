import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MainMenu extends JFrame {

    JButton btnViewListOfEmployees;
    JPanel searchPanel;
    JButton btnExit;

    public MainMenu() {
        super("MotorPH Employee App");
        setSize(450, 350);

        btnExit = new JButton("Logout");
        btnViewListOfEmployees = new JButton("View All Employees");
        searchPanel = new EmployeeSearch();

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1, 10, 20));
        panel.add(btnViewListOfEmployees);
        panel.add(searchPanel);
        panel.add(btnExit);

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnViewListOfEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmployeesList employeesList = new EmployeesList();
                employeesList.setVisible(true);
            }
        });

        add(panel);

    }

}
