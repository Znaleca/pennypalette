package penny_palette_tracker;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SignInSignUpGUI extends JFrame {

    private JTextField signUpUsernameField;
    private JButton signUpButton;
    private JTextField signInUsernameField;
    private JPasswordField signUpPasswordField;
    private JPasswordField signInPasswordField;
    private JButton signInButton;
    private UsersDatabase usersDatabase;

    public SignInSignUpGUI() {
        initComponents();
        usersDatabase = new UsersDatabase();
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.caretForeground", Color.YELLOW);
        UIManager.put("ComboBox.foreground", Color.YELLOW);
        UIManager.put("ComboBox.selectionForeground", new Color(255, 255, 255));
        UIManager.put("ComboBox.selectionBackground", Color.YELLOW);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(134, 190, 60));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.BLACK);
        setTitle("PennyPalette"); // Set the title here
        getContentPane().setBackground(new Color(150, 186, 220));
        UIManager.put("Table.foreground", Color.BLACK);
        UIManager.put("Table.selectionForeground", Color.BLACK);

        Font customFont = new Font("Arial", Font.PLAIN, 20);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Button.font", customFont);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        JLabel headerLabel = new JLabel("PennyPalette");
        Font headerFont = new Font("Arial", Font.BOLD, 30);
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(new Color(204, 0, 0));

        // Create components for Sign In panel
        JLabel signInLabel = new JLabel("Sign In");
        JLabel signInUsernameLabel = new JLabel("Username:");
        JLabel signInPasswordLabel = new JLabel("Password:");
        signInUsernameField = new JTextField(20);
        signInPasswordField = new JPasswordField(20);
        signInButton = new JButton("Sign In");

        // Create components for Sign Up panel
        JLabel signUpLabel = new JLabel("Sign Up");
        JLabel signUpUsernameLabel = new JLabel("Username:");
        JLabel signUpPasswordLabel = new JLabel("Password:");
        signUpUsernameField = new JTextField(20);
        signUpPasswordField = new JPasswordField(20);
        signUpButton = new JButton("Sign Up");

        // Set layout
            GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(headerLabel)  // Add the header label
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(signInLabel)
                                        .addComponent(signInUsernameLabel)
                                        .addComponent(signInUsernameField)
                                        .addComponent(signInPasswordLabel)
                                        .addComponent(signInPasswordField)
                                        .addComponent(signInButton))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(signUpLabel)
                                        .addComponent(signUpUsernameLabel)
                                        .addComponent(signUpUsernameField)
                                        .addComponent(signUpPasswordLabel)
                                        .addComponent(signUpPasswordField)
                                        .addComponent(signUpButton))
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(headerLabel)  // Add the header label
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signInLabel)
                                .addComponent(signUpLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signInUsernameLabel)
                                .addComponent(signUpUsernameLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signInUsernameField)
                                .addComponent(signUpUsernameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signInPasswordLabel)
                                .addComponent(signUpPasswordLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signInPasswordField)
                                .addComponent(signUpPasswordField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signInButton)
                                .addComponent(signUpButton))
        );

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signInActionPerformed(evt);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signUpActionPerformed(evt);
            }
        });

        pack();
    }

   private void signInActionPerformed(ActionEvent evt) {
    // Implement sign-in logic here
    String signInUsername = signInUsernameField.getText();
    char[] signInPasswordChars = signInPasswordField.getPassword();
    String signInPassword = new String(signInPasswordChars);

    // Check if the entered credentials match any user in the database
    if (usersDatabase.authenticateUser(signInUsername, signInPassword)) {
        JOptionPane.showMessageDialog(this, "Sign-in successful. Redirecting...");

        // Here you can navigate to the next screen
        // Assuming ExpensesIncomesTracker is your next screen
        ExpensesIncomesTracker expensesIncomesTracker = new ExpensesIncomesTracker();
        expensesIncomesTracker.setLocationRelativeTo(null);
        expensesIncomesTracker.setVisible(true);

        // Close the current sign-in/sign-up window
        this.dispose(); // This will close the current JFrame
    } else {
        JOptionPane.showMessageDialog(this, "Invalid username or password. Try again.");
    }
}


    private void signUpActionPerformed(ActionEvent evt) {
        // Implement sign-up logic here
        String signUpUsername = signUpUsernameField.getText();
        char[] signUpPasswordChars = signUpPasswordField.getPassword();
        String signUpPassword = new String(signUpPasswordChars);

        if (usersDatabase.isUserExists(signUpUsername)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Choose a different one.");
        } else {
            usersDatabase.addUser(new User(signUpUsername, signUpPassword));
            JOptionPane.showMessageDialog(this, "Sign-up successful. You can now sign in.");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignInSignUpGUI().setVisible(true);
            }
        });
    }

    private class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    private class UsersDatabase {
        private List<User> users;

        public UsersDatabase() {
            this.users = new ArrayList<>();
        }

        public boolean isUserExists(String username) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
            return false;
        }

        public boolean authenticateUser(String username, String password) {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true; // Authentication successful
                }
            }
            return false; // Authentication failed
        }

        public void addUser(User user) {
            users.add(user);
        }
    }
}
