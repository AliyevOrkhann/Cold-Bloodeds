import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;


public class UserTest {
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

        assertFalse(User.login("invalidUser", "somePassword"));

        assertFalse(User.login(testUsername, "wrongPassword"));

        assertFalse(User.login("invalidUser", "wrongPassword"));

    }

    private void createTestUserEntry(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_users_database.txt", true))) {
            writer.write(username + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

