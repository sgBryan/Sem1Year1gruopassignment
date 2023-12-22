/* LoginFrame class responsible for the user login interface. It extends JFrame and includes components
 for entering a username, password, and displaying error messages. It provides functionality for performing login,
creating UserInterface or AdminInterface instances based on the entered credentials, and resetting the login frame.*/


//package OOP_Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginFrame extends JFrame {
    private JTextField txtUsername; // Text field for entering the username
    private JPasswordField txtPassword; // Password field for entering the password
    private JLabel lblErrorMessage; // Label for displaying error messages
    private static AdminInterface adminInterfaceInstance; // Static reference to the AdminInterface instance

    // Constructor for LoginFrame, initializes and sets up the login interface
    public LoginFrame() {
        setTitle("User Login"); // Set the title of the frame
        setSize(400, 200); // Set the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        setLayout(new GridLayout(4, 2)); // Use a 4x2 grid layout

        // Add components to the frame: labels, text fields, error message label, and login button
        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        lblErrorMessage = new JLabel("");
        lblErrorMessage.setForeground(Color.RED);
        add(lblErrorMessage);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        add(btnLogin);

        setVisible(true); // Set the frame as visible
    }

    // Method to perform login based on entered credentials
    private void performLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        do {
            if (username.equals("user") && password.equals("password")) {
                UserInterface userInterface = new UserInterface(this); 
                this.dispose(); // Close the current login frame
                break;
            } else if (username.equals("admin") && password.equals("password")) {
                adminInterfaceInstance = new AdminInterface(this);
                this.dispose(); // Close the current login frame
                break;
            } else {
                lblErrorMessage.setText("Incorrect username or password.");
            }
        } while (false);
    }

    // Method to reset the login frame by clearing input fields and error messages
    public void resetLoginFrame() {
        txtUsername.setText("");  
        txtPassword.setText("");  
        lblErrorMessage.setText("");
        setVisible(true); // Set the login frame as visible
    }
}