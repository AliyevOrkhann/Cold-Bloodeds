import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        register(username, password);
    }
    
    public static boolean register (String username, String password) {
        if(checkUserExists(username)) {
            System.out.println("User already exists. Please choose another username.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users_database.txt", true))) {
            writer.write(username + "," + password + "\n");
            System.out.println("User registered successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Error happened during registration");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users_database.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if(userInfo.length == 2 && userInfo[0].equals(username) && userInfo[1].equals(password)) {
                    System.out.println("Login successful.");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred during login.");
            e.printStackTrace();
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    private static boolean checkUserExists(String username) {
        try(BufferedReader reader = new BufferedReader (new FileReader("users_database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if(userInfo.length > 0 && userInfo[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
