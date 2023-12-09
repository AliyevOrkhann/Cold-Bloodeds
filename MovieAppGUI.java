import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieAppGUI {
    private static JFrame frame;
    private static JTabbedPane tabbedPane;
    private static User currentUser;

    public MovieAppGUI(User user) {
        MovieAppGUI.currentUser = user;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("ColdBloodex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        JPanel userPanel = createUserPanel();
        tabbedPane.addTab("Login", userPanel);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(loginButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (User.register(username, password)) {
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (User.login(username, password)) {
                    frame.dispose();
                    new UserLoginWindow(currentUser);
                }
            }
        });
        return panel;
    }

    public static void main(String[] args) {
        User user = new User("username", "password");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MovieAppGUI(user);
            }
        });
    }

}