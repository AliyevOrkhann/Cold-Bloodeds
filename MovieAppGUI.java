import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MovieAppGUI {
    private static JFrame frame;
    private static JPanel userPanel;
    private static User currentUser;

    public MovieAppGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("ColdBloodex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel upperPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(173,216,230));
                g.setFont(new Font("Arial", Font.BOLD, 30));

                int textPadding = 40;
                int textYCoordinate = getHeight() / 4 + textPadding;
                g.drawString("ColdBloodex                                                                                   Login / Register ", 30, textYCoordinate);
            }
        };
        upperPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() / 5));
        

        
        JPanel lowerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("Verdana", Font.PLAIN, 12));

                FontMetrics metrics = g.getFontMetrics();
                String text = "@2023 ColdBloodex. All Rights Reserved. | Created by Orkhan, Babek & Ismayil";
                int textWidth = metrics.stringWidth(text);
                int textHeight = metrics.getHeight();

                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() - textHeight) / 2 + metrics.getAscent();

                g.drawString(text, x, y);
                
            }
        };
        lowerPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() / 12 + 5));

        JPanel centerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("BackgroundPhotos\\background-gs7hjuwvv2g0e9fj.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

                g.setColor(new Color(0,0,0,150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(true);

        

        JPanel authPanel = createUserPanel();
        GridBagConstraints authPanelConstraints = new GridBagConstraints();
        authPanelConstraints.gridx = 0;
        authPanelConstraints.gridy = 0;
        authPanelConstraints.weightx = 1.0;
        authPanelConstraints.weighty = 1.0;
        authPanelConstraints.fill = GridBagConstraints.BOTH;

        centerPanel.add(Box.createGlue(), authPanelConstraints);
        authPanelConstraints.gridy++;
        centerPanel.add(authPanel, authPanelConstraints);
        centerPanel.add(Box.createGlue(), authPanelConstraints);

           
        
        mainPanel.add(upperPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createUserPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        Dimension buttonSize = new Dimension(350, 40);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

        loginButton.setBackground(new Color(173, 216, 230));
        loginButton.setForeground(Color.BLACK);
        registerButton.setBackground(new Color(173, 216, 230));
        registerButton.setForeground(Color.BLACK);
        
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setPreferredSize(buttonSize);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setPreferredSize(buttonSize);

        JTextField usernameField = new JTextField(20);
        usernameField.setText("Username");
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE);
        usernameField.add(usernameLabel);  

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setText("Password");
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        passwordField.add(passwordLabel);

        Dimension textFieldSize = new Dimension(350, 40);
        usernameField.setPreferredSize(textFieldSize);
        passwordField.setPreferredSize(textFieldSize);

        usernameField.setForeground(Color.GRAY);
        usernameField.setText("Username");
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.WHITE);
            }
        }
       
          
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Username");
                }
            }
        });

        passwordField.setForeground(Color.GRAY);
        passwordField.setText("Password");
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new FocusListener() {
             @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Password");
                }
            }
        });
        
            

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(usernameField, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy++;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        panel.add(registerButton, gbc);

        JPanel shadedPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0,0,0,150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        GridBagConstraints shadedConstraints = new GridBagConstraints();

        shadedConstraints.gridx = 0;
        shadedConstraints.gridy = 0;
        shadedConstraints.gridwidth = GridBagConstraints.REMAINDER;
        shadedConstraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(shadedPanel, shadedConstraints);
        

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            boolean loggedIn = User.login(username, password);
            currentUser = new User(username, password);

            if(loggedIn) {
                openUserLoginWindow(currentUser);
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password");
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            try {
                boolean registered = User.register(username, password);

                if (registered) {
                    JOptionPane.showMessageDialog(frame, "User registered succesfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "User already exits");
                }
            } catch (IllegalArgumentException er) {
                JOptionPane.showMessageDialog(frame, er.getMessage());
            }

        });

        return panel;
    }

    private static void openUserLoginWindow(User currentUser) {
        EventQueue.invokeLater(() -> {
            new UserLoginWindow(currentUser);
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieAppGUI::new);
    }
}