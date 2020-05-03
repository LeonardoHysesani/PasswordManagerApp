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
    Integer selectedAccIndex = 0;
    String[] accs = {"sdvcs", "vfv", "dfb", "andd","bdsfb"};


    //swing components
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JButton DONEButton;
    private JCheckBox checkBox1;
    private JLabel accName;
    private JList<String> list1;
    private DefaultListModel<String> listModel;

    //frame management
    public static void main(String[] args) {
        JFrame frame = new JFrame("PasswordManager");
        frame.setContentPane(new PasswordManager().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

    }



    /*
    TODO Add a list with different accounts to choose from and store every password on a separate line in password.txt.
     Add an option to store a new password and prompt a window to enter the account's name and the corresponding password.
     Check this for help:
     https://examples.javacodegeeks.com/desktop-java/swing/java-swing-list-example/
     Undo changes if needed from github.
     */


    //getting/checking/setting passwords and adjusting fields' visibility
    public PasswordManager() {

        accName.setText(selectedAccIndex.toString());

        listModel = new DefaultListModel<>();
        listModel.addElement("Jasmine Mehra");
        listModel.addElement("Ankit Mishra");
        listModel.addElement("Madhuri Sanghvi");
        listModel.addElement("Alok Kumar");
        listModel.addElement("Rohit Bothra");
        listModel.addElement("Rahul Aggarwal");

        list1 = new JList<>(listModel);
        list1.setSelectedIndex(0);
        list1.setVisible(true);
        //list1.addListSelectionListener();

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
            Path file = Paths.get("/home/leonardo/IdeaProjects/PasswordManagerApp/src/password.txt");
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