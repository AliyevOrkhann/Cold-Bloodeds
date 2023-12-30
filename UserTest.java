import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class UserTest{
    
    @Test
    public void testRegister() {
        File pathname=new File("usersTest_database.txt");
        String validUsername = "aliyev";
        String validPassword = "aliyev123";
        assertTrue(User.register(validUsername, validPassword,pathname));
        
        User.register("babekbb", "babek123");
        String existingUsername = "babekbb";
        String existingPassword = "babek123";
        assertFalse(User.register(existingUsername, existingPassword));

        String shortTempUsername="tufan";
        String shortPassword = "123";
        assertThrows(IllegalArgumentException.class, () -> User.register(shortTempUsername, shortPassword));

        String sameTempUsername="user123";
        String samePassword = "user123";
        assertThrows(IllegalArgumentException.class, () -> User.register(sameTempUsername, samePassword));
        pathname.delete();
    }



    @Test
    public void testLogin() {
        String testUsername = "testUser";
        String testPassword = "testPassword";
        User.register(testUsername, testPassword);

        assertTrue(User.login(testUsername, testPassword));

        assertFalse(User.login("invalidUser", "wrongPassword"));

        assertFalse(User.login(testUsername, "wrongPassword"));

        assertFalse(User.login("invalidUser", testPassword));
    }


    @Test
    public void testCheckUserExists() {   
        User.register("babekbb", "babek123");
        User.register("test1", "test123");
        assertTrue(User.checkUserExists("babekbb"));
        assertTrue(User.checkUserExists("test1"));

        assertFalse(User.checkUserExists("nonExistingUser"));
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
        Movie movieToRemove = new Movie("MovieTest", "Orxan", 2022, 120);

        List<Movie> watchList=new ArrayList<>();
        watchList.add(movieToRemove);
        testUser.setWatchList(watchList);
        
        testUser.removeFromWatchlist(movieToRemove);
        assertFalse(watchList.contains(movieToRemove)); //Check if movie removed from watchlist
        
    }
}

