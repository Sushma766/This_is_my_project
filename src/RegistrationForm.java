import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationForm extends JDialog {
    private JTextField tfname;
    private JTextField tfemail;
    private JTextField tfaddress;
    private JTextField tfphone;
    private JPasswordField pfpassword;
    private JPasswordField pfconfirmpassword;
    private JButton btnregister;
    private JButton btncancel;
    private JPanel registerPanel;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 475));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = tfname.getText();
        String email = tfemail.getText();
        String address = tfaddress.getText();
        String phone = tfphone.getText();
        String password = String.valueOf(pfpassword.getPassword());
        String confirmPassword = String.valueOf(pfconfirmpassword.getPassword());

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Confirm Password does not match", "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public User user;

    private User addUserToDatabase(String name, String email, String address, String phone, String password) {
        User user = null;
        final String DB_URL ="jdbc:MySQL://localhost/Mystore ?server aTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stml = conn.createStatement();
            String sql = "INSERT INTO users (name,email,address,phone,password)" + "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, password);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.address = address;
                user.phone = phone;
                user.password = password;
            }
            stml.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        RegistrationForm myForm = new RegistrationForm(null);
        User user = myForm.user;
        if (user!=null)
        {
            System.out.println("Successful registration of:" + user.name);
        }
     else{
            System.out.println("Registration canceled");
        }
    }
}











