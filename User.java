import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class User {
    private String username;
    private String password;
    private List<Movie> watchList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.watchList = new ArrayList<>();
    }
    
    public static boolean register (String username, String password) {
        if(checkUserExists(username)) {
            System.out.println("User already exists. Please choose another username.");
            return false;
        }

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

    public void addToWatchList(Movie selectedMovie) {
        watchList.add(selectedMovie);
    }

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

    public List<Movie> getWatchList() {
        watchList = MovieDatabase.loadFromFile("DataBase/"+username+".dat");
        return watchList;
    }

    public String getUsername() {
        return username;
    }
   
}
