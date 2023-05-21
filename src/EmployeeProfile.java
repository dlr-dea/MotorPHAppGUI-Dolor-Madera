import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EmployeeProfile extends JPanel{
    JLabel lblId;
    JTextField txtId;

    JPanel panelPersonal;
    JLabel lblFirstName;
    JTextField txtFirstName;

    JLabel lblLastName;
    JTextField txtLastName;

    JLabel lblAddress = new JLabel("Address");
    JTextField txtAddress;
    JLabel lblPhoneNumber = new JLabel("Phone Number");
    JTextField txtPhoneNumber;



    JPanel panelGovernment;
    JLabel lblSss;
    JLabel lblPhilhealth;
    JLabel lblTin;
    JLabel lblPagibig;
    JTextField txtSss;
    JTextField txtPhilhealth;
    JTextField txtTin;
    JTextField txtPagibig;

    JTextField txtStatus;
    JTextField txtSupervisor;
    JTextField txtPosition;


    public EmployeeProfile(Employee employee) {
        setSize(100, 200);

        setLayout(new GridLayout(0, 1));

        lblId = new JLabel("Employee No.");
        lblFirstName = new JLabel("First Name");
        lblLastName = new JLabel("Last Name");

        Integer id = employee.getId();
        String strId = String.valueOf(id);

        txtFirstName = new JTextField(employee.getFirstName());
        txtLastName = new JTextField(employee.getLastName());
        txtAddress = new JTextField(employee.getAddress());
        txtPhoneNumber = new JTextField(employee.getPhoneNumber());

        GridLayout personalLayout = new GridLayout(0,2);
        panelPersonal = new JPanel(personalLayout);
        JPanel panelPersonal = this.createPanel("Personal Data", personalLayout);
        panelPersonal.add(lblFirstName);
        panelPersonal.add(txtFirstName);
        panelPersonal.add(lblLastName);
        panelPersonal.add(txtLastName);
        panelPersonal.add(lblPhoneNumber);
        panelPersonal.add(txtPhoneNumber);
        panelPersonal.add(lblAddress);
        panelPersonal.add(txtAddress);

        GridLayout governmentLayout = new GridLayout(0,2);
        JPanel panelGovernment = this.createPanel("Government Numbers", governmentLayout);

        lblSss = new JLabel("SSS");
        lblPagibig = new JLabel("Pag-ibig");
        lblTin = new JLabel("TIN");
        lblPhilhealth = new JLabel("Philhealth");
        txtSss = new JTextField(employee.getSssNo());
        txtPhilhealth = new JTextField(employee.getPhilhealthNo());
        txtTin = new JTextField(employee.getTin());
        txtPagibig = new JTextField(employee.getPagIbigNo());

        txtStatus = new JTextField(employee.getStatus());
        txtPosition = new JTextField(employee.getPosition());
        txtSupervisor = new JTextField(employee.getSuperVisor());

        GridLayout employmentLayout = new GridLayout(0,2);
        JPanel employmentPanel = this.createPanel("Employment Data", employmentLayout);
        employmentPanel.add(this.createLabel("Status"));
        employmentPanel.add(txtStatus);
        employmentPanel.add(this.createLabel("Position"));
        employmentPanel.add(txtPosition);
        employmentPanel.add(this.createLabel("Immediate Supervisor"));
        employmentPanel.add(txtSupervisor);

        panelGovernment.add(lblSss);
        panelGovernment.add(txtSss);
        panelGovernment.add(lblPagibig);
        panelGovernment.add(txtPagibig);
        panelGovernment.add(lblTin);
        panelGovernment.add(txtTin);
        panelGovernment.add(lblPhilhealth);
        panelGovernment.add(txtPhilhealth);

        JPanel salaryPanel = this.createPanel("Salary Data", new GridLayout(0,2));
        salaryPanel.add(this.createLabel("Basic Salary"));
        salaryPanel.add(this.createTextField(String.valueOf(employee.getBasicSalary())));
        salaryPanel.add(this.createLabel("Rice Subsidy"));
        salaryPanel.add(this.createTextField(String.valueOf(employee.getRiceSubsidy())));
        salaryPanel.add(this.createLabel("Phone Allowance"));
        salaryPanel.add(this.createTextField(String.valueOf(employee.getPhoneAllowance())));
        salaryPanel.add(this.createLabel("Clothing Allowance"));
        salaryPanel.add(this.createTextField(String.valueOf(employee.getClothingAllowance())));
        salaryPanel.add(this.createLabel("Gross Semi-monthly Rate"));
        salaryPanel.add(this.createTextField(String.valueOf(employee.getGrossSemiMonthlyRate())));
        salaryPanel.add(this.createLabel("Hourly Rate"));
        salaryPanel.add(this.createTextField(String.valueOf(employee.getHourlyRate())));

        
        JPanel panel = this.createPanel("Employee No. "+ strId, new GridLayout(0,1, 0, 10));
      
        panel.add(panelPersonal);
        panel.add(employmentPanel);
        panel.add(salaryPanel);
        panel.add(panelGovernment);
        add(panel);
    }

    private JPanel createPanel(String title, GridLayout layout) {
        JPanel panel = new JPanel(layout);
        Border border = BorderFactory.createTitledBorder(title);
        panel.setBorder(border);
        return panel;
    }
    
    private JLabel createLabel(String label) {
        return new JLabel(label);
    }

    private JTextField createTextField(String value) {
        return new JTextField(value);
    }
}
