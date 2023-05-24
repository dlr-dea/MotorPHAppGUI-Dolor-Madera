import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;

public class PasswordPrompt extends JFrame {
    private JLabel lblUsername, lblPassword;
    private JTextField tfUsername;
    private JPasswordField tfPassword;

    private JButton btnSubmit;

    private Integer tries;
    private String adminUsername;
    private String adminPassword;

    public PasswordPrompt() {
        super("MotorPH Employee App");
        setSize(300, 180);
        this.tries = 0;

        lblUsername = new JLabel("Username or Employee ID:");
        lblPassword = new JLabel("Password:");

        tfUsername = new JTextField(20);
        tfPassword = new JPasswordField(20);
        btnSubmit = new JButton("Submit");

        JPanel panel = new JPanel();
        panel.setSize(300, 300);

        panel.add(lblUsername);
        panel.add(tfUsername);
        panel.add(lblPassword);
        panel.add(tfPassword);
        panel.add(btnSubmit);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        add(panel);

        this.adminUsername = "admin";
        this.adminPassword = "admin123";

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PasswordPrompt.this.tries++;

                if (PasswordPrompt.this.tries == 4) {
                    JOptionPane.showMessageDialog(PasswordPrompt.this,
                            "Incorrect username/password credentials. Will now exit.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                String username = tfUsername.getText();
                String password = String.valueOf(tfPassword.getPassword());

                tfUsername.setText("");
                tfPassword.setText("");

                if (username.matches(PasswordPrompt.this.adminUsername)
                        && password.matches(PasswordPrompt.this.adminPassword)) {

                    MainMenu mainMenu = new MainMenu();
                    mainMenu.setVisible(true);
                    PasswordPrompt.this.setVisible(false);

                } else {

                    AccountFileReader accountFileReader = new AccountFileReader();
                    if (accountFileReader.isAccountPasswordCombinationCorrect(username, password)) {
                        EmployeeLeave employeeLeave = new EmployeeLeave(Integer.parseInt(username));
                        employeeLeave.setVisible(true);
                        PasswordPrompt.this.setVisible(false);
                    } else {

                        JOptionPane.showMessageDialog(PasswordPrompt.this, "Incorrect username/password credentials",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }
        });

    }

}
