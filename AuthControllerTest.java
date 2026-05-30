import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    private AuthController auth;

    @BeforeEach
    void setUp() {
        auth = new AuthController();
    }

    @Test
    void testLoginBerhasil() {
        User u = auth.login("admin1", "admin123");
        assertNotNull(u);
        assertEquals("admin1", u.getUsername());
    }

    @Test
    void testLoginGagalPasswordSalah() {
        User u = auth.login("admin1", "salah");
        assertNull(u);
    }

    @Test
    void testLoginGagalUsernameTidakAda() {
        User u = auth.login("tidakada", "pass");
        assertNull(u);
    }

    @Test
    void testIsLoggedInSetelahLogin() {
        auth.login("instruktur", "pass123");
        assertTrue(auth.isLoggedIn());
    }

    @Test
    void testLogoutBerhasil() {
        auth.login("admin1", "admin123");
        auth.logout();
        assertFalse(auth.isLoggedIn());
    }

    @Test
    void testGetUserLoginSetelahLogin() {
        auth.login("instruktur", "pass123");
        assertNotNull(auth.getUserLogin());
        assertEquals("instruktur", auth.getUserLogin().getUsername());
    }
}