import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieAppGUI {
    private static JFrame frame;
    private static JPanel userPanel;
    private static JTabbedPane tabbedPane;
    private static User currentUser;

    public MovieAppGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("ColdBloodex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        //frame.getContentPane().setBackground(Color.BLACK);

        JLabel background = new JLabel(new ImageIcon("background_photo.jpg"));
        background.setLayout(new GridBagLayout());
        background.setSize(frame.getSize());
        frame.setContentPane(background);
        frame.setLayout(new BorderLayout());
        
        
        //tabbedPane = new JTabbedPane();

        userPanel = createUserPanel();
        background.add(userPanel, BorderLayout.CENTER);
        frame.add(userPanel);
        frame.setVisible(true);
       
    }

    private static JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        panel.setLayout(new GridLayout());
        
        panel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setForeground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(frame.getWidth()/2, frame.getHeight()));

        
        
        JLabel nameLabel = new JLabel("ColdBloodex");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        nameLabel.setForeground(new Color(173, 216, 230));
        panel.add(nameLabel, gbc);

        gbc.gridy++;
        JLabel marketingLabel = new JLabel("Sign In / Register");
        marketingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        marketingLabel.setForeground(new Color(173, 216, 230));
        leftPanel.add(marketingLabel, gbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setForeground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(frame.getWidth()/ 2, frame.getHeight()));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        registerButton.setBackground(new Color(173, 216, 230));
        registerButton.setForeground(Color.BLACK);
        loginButton.setBackground(new Color(173, 216, 230));
        loginButton.setForeground(Color.BLACK);

        Dimension buttonSize = new Dimension(120,30);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);


        
        rightPanel.add(usernameField, gbc);
        gbc.gridy++;
        rightPanel.add(passwordField, gbc);
        gbc.gridy++;
        rightPanel.add(loginButton, gbc);
        gbc.gridy++;
        rightPanel.add(registerButton, gbc);
       

        //Insets textFieldInsets = new Insets(5, 10, 5, 10);
        //usernameField.setMargin(textFieldInsets);
        //passwordField.setMargin(textFieldInsets);
        
        //registerButton.addActionListener(new ActionListener() {
          //  @Override
            //public void actionPerformed(ActionEvent e) {
              //  String username = usernameField.getText();
                //String password = passwordField.getText();

             //   if (User.register(username, password)) {
               //     usernameField.setText("");
                 //   passwordField.setText("");
               // }
           // }
       // });

   //     loginButton.addActionListener(new ActionListener() {
    //        @Override
       //     public void actionPerformed(ActionEvent e) {
       //         String username = usernameField.getText();
        //        String password = passwordField.getText();
        //        MovieAppGUI.currentUser = new User(username, password);

           //     if (User.login(username, password)) {
            //        frame.dispose();
             //       new UserLoginWindow(currentUser);
          //      }
        //    }
    //    });
    
    panel.add(leftPanel);
    panel.add(rightPanel);
    return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MovieAppGUI();
            }
        });
    }

}