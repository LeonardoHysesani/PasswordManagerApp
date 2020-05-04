import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;

public class PasswordManager extends JFrame {
    //variables
    static String originalPassword;
    String originalPasswordConfirmation;
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
        JFrame frame = new JFrame("PasswordManager");
        frame.setContentPane(new PasswordManager().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }


    //getting/checking/setting passwords and adjusting fields' visibility
    public PasswordManager() {
        //button action
        DONEButton.addActionListener(e ->{
            //current password field
            originalPasswordConfirmation = String.valueOf((passwordField1.getPassword()));
            //added to help if trying to debug
            System.out.println("Current password field : " + originalPasswordConfirmation);

            //new password field
            newPassword = String.valueOf((passwordField2.getPassword()));
            //added to help if trying to debug
            System.out.println("New password field : " + newPassword);

            //new password confirmation field
            newPasswordConfirmation = String.valueOf((passwordField3.getPassword()));
            //added to help if trying to debug
            System.out.println("New password confirmation field : " + newPasswordConfirmation);


            //file management and final steps to changing password

            //getting original password from stored file
            Path file = Paths.get("C:\\Users\\user\\IdeaProjects\\testgui\\src\\password.txt");
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //setting original password's value to variable
            assert fileReader != null;
            try {
                originalPassword = fileReader.next();
            }
            catch (Exception e1){
                originalPassword = "";
            }
            //added to help if trying to debug
            System.out.println("Original password retrieved from file : " + originalPassword);


            //confirmation process
            //checking if current password field is correct
            if (!originalPasswordConfirmation.equals(originalPassword)) {
                JOptionPane.showMessageDialog(null, "Please check current password field.");
            }
            //checking if both new password fields have the same password
            else if (!newPassword.equals(newPasswordConfirmation)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please check again.");
            }
            //checking if new password is the same as old one
            else if (newPassword.equals(originalPassword)) {
                JOptionPane.showMessageDialog(null, "New password can't be old password.");
            }
            else {
                //finally changing password if everything is correct
                try {
                    Files.write(file, Collections.singleton(newPassword));
                    //password changed message
                    JOptionPane.showMessageDialog(null, "Password changed succesfully. New password is : " + newPassword);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //fields' visibility
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
    }
}