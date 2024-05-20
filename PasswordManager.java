import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class PasswordManager extends dbscn {
    int option = 0;
    private passwordgenerator pg = new passwordgenerator();
    private Test test;

    public PasswordManager() {
        this.test = new Test();
    }

    public String hidePassword() {
        String hiddenpassword = test.something();
        return hiddenpassword;
    }

    public static void main(String[] args) {
        PasswordManager pm = new PasswordManager();
        System.err.println();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("-----------WELCOME TO PASSWORD MANAGER------------");
        pm.menu();

    }

    public void menu() {
        System.out.println("----------------------MENU-----------------------");
        System.out.println("1. Add a new password");
        System.out.println("2. View all passwords");
        System.out.println("3. Delete a password");
        System.out.println("4. Update a password");
        System.out.println("5. Generate a password");
        System.out.println("6. Exit");
        while (option != 6) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            option = sc.nextInt();
            System.out.println();
            switch (option) {
                case 1:
                    System.out.println("Enter the website: ");
                    String website = sc.next();
                    sc.nextLine();
                    System.out.println("Enter the username: ");
                    String username = sc.nextLine();
                    // System.out.println("Enter the password: ");
                    // String password = sc.nextLine();
                    String password = hidePassword();
                    addPassword(website, username, password);
                    break;
                case 2:
                    viewPassword();
                    break;
                case 3:
                    deletePassword(sc);
                    break;
                case 4:
                    updatePassword(sc);
                    break;
                case 5:
                    String pgpassword = generatePassword(sc);
                    System.out.println("Do you want to use this password? (y/n)");
                    String choice = sc.next();
                    // String y = "y";
                    if (choice.equals("y")) {
                        System.out.println("Enter the website: ");
                        String pgwebsite = sc.next();
                        sc.nextLine();

                        System.out.println("Enter the username: ");
                        String pgusername = sc.nextLine();
                        addPassword(pgwebsite, pgusername, pgpassword);
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public static boolean check(String website, String username, String password) {
        String sql = "Select * from passwordmanager where website ='" + website + "' and username = '" + username
                + "' and password = '" + password + "'";
        try {
            Connection conn = dbscn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return false;
    }

    static void addPassword(String website, String username, String password) {
        String sql = "INSERT INTO passwordmanager(username, password, website) VALUES ('" + username + "', '" + password
                + "', '" + website + "')";

        try {
            Connection connection = dbscn.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Password added successfully");
        } catch (SQLException e) {
            System.out.println("Failed to add password");
            e.printStackTrace();
        }
    }

    static void viewPassword() {
        String sql = "SELECT * FROM passwordmanager ORDER by website and username ASC";
        try {
            Connection connection = dbscn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Website: " + rs.getString("website"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Failed to view password");
            e.printStackTrace();
        }
    }

    static void deletePassword(Scanner sc) {
        System.out.println("Enter the website: ");
        String website = sc.next();
        sc.nextLine();

        System.out.println("Enter the username: ");
        String usename = sc.nextLine();

        // System.out.println("Enter the password: ");
        // String password = sc.nextLine();

        String password = new PasswordManager().hidePassword();

        boolean check = check(website, usename, password);
        if (check == true) {
            String sql = "DELETE FROM passwordmanager WHERE website = '" + website + "' AND username = '" + usename
                    + "' and password = '" + password + "'";
            try {
                Connection connection = dbscn.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                System.out.println("Password deleted successfully");
            } catch (SQLException e) {
                System.out.println("Failed to delete password");
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect password");
        }
    }

    static void updatePassword(Scanner scan) {
        System.out.println("Enter the website: ");
        String Website = scan.next();
        scan.nextLine();

        System.out.println("Enter the username: ");
        String username = scan.nextLine();

        // System.out.println("Enter the password: ");
        // String password = scan.nextLine();

        String password = new PasswordManager().hidePassword();

        boolean check = check(Website, username, password);

        if (check == true) {
            String newpassword = new PasswordManager().hidePassword();

            String sql = "UPDATE passwordmanager SET password = '" + newpassword + "' WHERE website = '" + Website
                    + "' AND username = '" + username + "'";

            try {
                Connection connection = dbscn.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                System.out.println("Password updated successfully");
            } catch (SQLException e) {
                System.out.println("Failed to update password");
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect website or username. Please try again.");
        }

    }

    public String generatePassword(Scanner scan) {
        System.out.println("Enter the length of letters: ");
        int charlength = scan.nextInt();
        System.out.println("Enter the length of number: ");
        int numberlength = scan.nextInt();
        System.out.println("Enter the length of symbols: ");
        int symbollength = scan.nextInt();

        System.out.println("Generating password...");
        String pw = pg.generatePassword(charlength, numberlength, symbollength);
        System.out.println("The password is " + pw);
        return pw;
    }
}