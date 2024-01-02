import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user in the movie management system.
 * It provides functionalities for user registration, login, managing a watchlist, and file operations.
 */
public class User {
    private String username;
    private String password;
    private List<Movie> watchList;

    /**
     * Constructs a new User with the specified username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.watchList = new ArrayList<>();
    }
    
    /**
     * Registers a new user with the given username and password.
     *
     * @param username The username of the user to be registered.
     * @param password The password of the user to be registered.
     * @return {@code true} if the registration is successful, {@code false} otherwise.
     * @throws IllegalArgumentException If the password length is less than 6 or if the password is the same as the username.
     */
    public static boolean register (String username, String password) {
        if(checkUserExists(username)) {
            System.out.println("User already exists. Please choose another username.");
            return false;
        }

        if(password.length()<6)throw new IllegalArgumentException("Password Length Must Be Greater Than Six Characters");
        else if(password.equals(username))throw new IllegalArgumentException("Password Cannot Be Same With Username");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users_database.txt", true))) {
            writer.write(username + "," + password + "\n");
            System.out.println("User registered successfully.");
            String path="DataBase/"+username+".dat";
            FileOutputStream fos=new FileOutputStream(new File(path));
            return true;
        } catch (IOException e) {
            System.out.println("Error happened during registration");
            e.printStackTrace();
            return false;
        }
    }

    // Test Case
    /**
     * Registers a new user with the given username and password, writing to a specified file.
     *
     * @param username The username of the user to be registered.
     * @param password The password of the user to be registered.
     * @param file     The file to write user information to.
     * @return {@code true} if the registration is successful, {@code false} otherwise.
     * @throws IllegalArgumentException If the password length is less than 6 or if the password is the same as the username.
     */
    public static boolean register (String username, String password, File file) {
        if(checkUserExists(username)) {
            System.out.println("User already exists. Please choose another username.");
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(username + "," + password + "\n");
            System.out.println("User registered successfully.");
            String path="DataBase/"+username+".dat";
            FileOutputStream fos=new FileOutputStream(new File(path));
            return true;
        } catch (IOException e) {
            System.out.println("Error happened during registration");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Logs in a user with the given username and password.
     *
     * @param username The username of the user to be logged in.
     * @param password The password of the user to be logged in.
     * @return {@code true} if the login is successful, {@code false} otherwise.
     */
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

    /**
     * Checks if a user with the given username already exists.
     *
     * @param username The username to check.
     * @return {@code true} if the user exists, {@code false} otherwise.
     */
    public static boolean checkUserExists(String username) {
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

    /**
     * Adds a movie to the user's watchlist.
     *
     * @param selectedMovie The movie to be added to the watchlist.
     */
    public void addToWatchList(Movie selectedMovie) {
        watchList.add(selectedMovie);
    }

    /**
     * Removes a movie from the user's watchlist.
     *
     * @param selectedMovie The movie to be removed from the watchlist.
     */
    public void removeFromWatchlist(Movie selectedMovie) {
        watchList.remove(selectedMovie);
        File path=new File("DataBase/"+username+".dat");
        File temp=new File("DataBase/"+username+"backup.dat");
        try (FileWriter fw=new FileWriter(temp);
            BufferedReader br=new BufferedReader(new FileReader(path))) {
            String line;
            while((line=br.readLine())!=null){
               if((line.contains("Title: " + selectedMovie.getTitle()))){
                    for(int i=0;i<5;i++){
                        line=br.readLine();
                    }
               }
               if(line!=null)fw.write(line+"\n");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        } 
        try (FileWriter fw=new FileWriter(path);BufferedReader br=new BufferedReader(new FileReader(temp))) {
            String line;
            while((line=br.readLine())!=null){
               fw.write(line+"\n");

            }
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        } 
        finally{
            temp.delete();
        }

    }

    /**
     * Retrieves and returns the user's watchlist.
     *
     * @return The watchlist of the user.
     */
    public List<Movie> getWatchList() {
        watchList = MovieDatabase.loadFromFile("DataBase/"+username+".dat");
        return watchList;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's watchlist to the specified list of movies.
     *
     * @param watchList The list of movies to set as the watchlist.
     */
    public void setWatchList(List<Movie> watchList) {
        this.watchList = watchList;
    }
}