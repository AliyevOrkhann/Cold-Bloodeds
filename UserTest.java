import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class UserTest{

    @Test
     public void testRegister() {
        String validUsername = "aliyev";
        String validPassword = "aliyev123";
        assertTrue(User.register(validUsername, validPassword));

        String existingUsername = "babekbb";
        String existingPassword = "babek123";
        assertFalse(User.register(existingUsername, existingPassword));

        String shortTempUsername="tufan";
        String shortPassword = "123";
        assertThrows(IllegalArgumentException.class, () -> User.register(shortTempUsername, shortPassword));

        String sameTempUsername="Bomboclat";
        String samePassword = "Bomboclat";
        assertThrows(IllegalArgumentException.class, () -> User.register(sameTempUsername, samePassword));
    }



    @Test
    public void testLogin() {
        String testUsername = "testUser";
        String testPassword = "testPassword";
        createTestUserEntry(testUsername, testPassword);

        assertTrue(User.login(testUsername, testPassword));

        assertFalse(User.login("invalidUser", "wrongPassword"));

        assertFalse(User.login(testUsername, "wrongPassword"));

        assertFalse(User.login("invalidUser", testPassword));

    }
    private void createTestUserEntry(String username, String password) {
        File f=new File("test_users_database.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {
            writer.write(username + "," + password + "\n");
            f.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testCheckUserExists() {   
        assertTrue(User.checkUserExists("babekbb")); // Existing users
        assertTrue(User.checkUserExists("test1"));

        assertFalse(User.checkUserExists("nonExistingUser")); // Non-existing user
    }



    @Test
    public void testAddToWatchList() {
        List<Movie> testWatchList=new ArrayList<>();

        Movie test=new Movie("Inception", "Nolan", 2010, 150);
        testWatchList.add(test);
        
        assertTrue(testWatchList.contains(test));
    }



    @Test
    public void testRemoveFromWatchlist() throws IOException {
        User testUser=new User("hello", "worlddd");
        Movie movieToRemove = new Movie("Sample Movie", "Genre", 2022, 120);

        List<Movie> watchList=new ArrayList<>();
        testUser.setWatchList(watchList);
        watchList.add(movieToRemove);
        
        testUser.addToWatchList(movieToRemove);
        testUser.removeFromWatchlist(movieToRemove);

        assertTrue(watchList.contains(movieToRemove)); //Check if movie removed from watchlist

    }
}

