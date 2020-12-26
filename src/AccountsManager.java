import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class AccountsManager extends JFrame {
    private JList<String> accList;
    DefaultListModel<String> accounts = new DefaultListModel<>();
    private JPanel panel1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Accounts Manager");
        frame.setContentPane(new AccountsManager().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }

    public AccountsManager() {
        //Adding  list elements from file
        Path file = Paths.get("/home/leonardo/IdeaProjects/PasswordManagerApp/src/testfile.txt");
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        accList.setModel(accounts);
        while (true) {
            assert fileReader != null;
            if (!fileReader.hasNextLine()) break;
            accounts.addElement("Name: " + fileReader.nextLine() + " Password: " + fileReader.nextLine());
            accList.updateUI();
        }
    }
}
