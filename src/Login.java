import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Scanner;

public class Login extends JFrame {
    //variables
    static String originalHash;
    String givenPassword;
    String newPassword;
    String newPasswordConfirmation;

    //swing components
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JButton DONEButton;
    private JCheckBox checkBox1;

    //frame management
    public static void main(String[] args) {
        JFrame frame = new JFrame("PasswordManager Login");
        frame.setContentPane(new Login().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }


    //getting/checking/setting passwords and adjusting fields' visibility
    public Login() {

        //button action
        DONEButton.addActionListener(e ->{
            //current password field
            givenPassword = String.valueOf((passwordField1.getPassword()));
            //debugging
            System.out.println("Current password field : " + givenPassword);

            //new password field
            newPassword = String.valueOf((passwordField2.getPassword()));
            //debugging
            System.out.println("New password field : " + newPassword);

            //new password confirmation field
            newPasswordConfirmation = String.valueOf((passwordField3.getPassword()));
            //debugging
            System.out.println("New password confirmation field : " + newPasswordConfirmation);

            //cleaning input
            givenPassword = givenPassword.trim();
            newPassword = newPassword.trim();
            newPasswordConfirmation = newPasswordConfirmation.trim();

            //file management and final steps to changing password

            //getting hash from stored file
            Path file = Paths.get("/home/leonardo/IdeaProjects/PasswordManagerApp/src/password.txt");
            Scanner fileReader2 = null;
            try {
                fileReader2 = new Scanner(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //setting hash's value to variable
            assert fileReader2 != null;
            try {
                originalHash = fileReader2.next();
            }
            catch (Exception e1){
                originalHash = "";
            }
            //debugging
            System.out.println("Hash retrieved from file : " + originalHash);


            //confirmation process
            //checking if current password field is correct
            try {
                boolean allIsValid = true;
                if (givenPassword.equals("") || newPassword.equals("") || newPasswordConfirmation.equals("")) {
                    JOptionPane.showMessageDialog(null, "Password can't be empty");
                    allIsValid = false;
                }
                if (!originalHash.equals(Encrypt.text(givenPassword))) {
                    JOptionPane.showMessageDialog(null, "Please check current password field.");
                    allIsValid = false;
                }
                //checking if both new password fields have the same password
                if (!newPassword.equals(newPasswordConfirmation)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please check again.");
                    allIsValid = false;
                }
                //checking if hashes match
                if (Encrypt.text(newPassword).equals(originalHash)) {
                    JOptionPane.showMessageDialog(null, "New password can't be old password.");
                    allIsValid = false;
                }
                //finally changing password if everything is correct
                if (allIsValid) {
                    try {
                        Files.write(file, Collections.singleton(Encrypt.text(newPassword)));
                        //Clear fields
                        passwordField1.setText("");
                        passwordField2.setText("");
                        passwordField3.setText("");
                        //password changed message
                        JOptionPane.showMessageDialog(null, "Password changed succesfully. New password is : " + newPassword);
                        new AccountsManager();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            }
        });

        //passwords' visibility
        checkBox1.addActionListener(e -> {
            if(checkBox1.isSelected()) {
                passwordField1.setEchoChar((char) 0);
                passwordField2.setEchoChar((char) 0);
                passwordField3.setEchoChar((char) 0);
            }
            else {
                passwordField1.setEchoChar((char) 0x2022);
                passwordField2.setEchoChar((char) 0x2022);
                passwordField3.setEchoChar((char) 0x2022);
            }
        });

        //Key listeners
        passwordField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    passwordField2.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        passwordField2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    passwordField3.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    passwordField1.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        passwordField3.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    passwordField2.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    DONEButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }
}