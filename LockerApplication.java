import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LockerApplication extends JFrame {
    private JButton enterButton;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    private String password;
    private boolean isPasswordSet;

    public LockerApplication() {
        setTitle("Locker Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        passwordField = new JPasswordField(10);
        enterButton = new JButton("Enter");
        statusLabel = new JLabel();

        // Create the number pad buttons
        JPanel numberPadPanel = createNumberPadPanel();

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPasswordSet) {
                    setPassword();
                } else {
                    verifyPassword();
                }
            }
        });

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(new JLabel("Enter Password:"));
        passwordPanel.add(passwordField);
        passwordPanel.add(enterButton);

        add(passwordPanel, BorderLayout.NORTH);
        add(numberPadPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void setPassword() {
        char[] enteredPassword = passwordField.getPassword();
        password = new String(enteredPassword);
        isPasswordSet = true;
        statusLabel.setText("Password Set");
        passwordField.setText("");
    }

    private void verifyPassword() {
        char[] enteredPassword = passwordField.getPassword();
        String enteredPasswordString = new String(enteredPassword);

        if (enteredPasswordString.equals(password)) {
            statusLabel.setText("Correct Password");
        } else {
            statusLabel.setText("Incorrect Password");
        }

        passwordField.setText("");
    }

    private JPanel createNumberPadPanel() {
        JPanel numberPadPanel = new JPanel(new GridLayout(4, 3));
        
        // Button labels for the number pad
        String[] buttonLabels = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "*", "0", "#"
        };

        // Create and add number pad buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new NumberPadActionListener());
            numberPadPanel.add(button);
        }

        return numberPadPanel;
    }

    private class NumberPadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            if (buttonText.equals("*")) {
                passwordField.setText("");
            } else if (buttonText.equals("#")) {
                enterButton.doClick();
            } else {
                passwordField.setText(passwordField.getText() + buttonText);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LockerApplication();
            }
        });
    }
}