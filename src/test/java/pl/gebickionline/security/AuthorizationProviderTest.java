package pl.gebickionline.security;

import org.junit.*;
import pl.gebickionline.communication.CommunicationManagerImpl;
import pl.gebickionline.exception.AuthorizationException;

import static org.junit.Assert.*;

public class AuthorizationProviderTest {

    private AuthorizationProvider provider;
    private AuthorizationCleaner authorizationCleaner;

    @Before
    public void setUp() throws Exception {
        provider = AuthorizationProvider.getInstance();
        authorizationCleaner = (AuthorizationCleaner) CommunicationManagerImpl.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        provider.clear();
        authorizationCleaner.clearAuthorizationData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullUsername_whenLogin_throwsException() throws Exception {
        provider.login(null, "pass");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullPassword_whenLogin_throwsException() throws Exception {
        provider.login("username", null);
    }

    @Test
    public void givenWrongPassword_whenLogin_thanUserIsNotLoggedIn() throws Exception {
        provider.login("admin", "wrong pass");
        assertFalse(provider.isLoggedIn());
    }

    @Test
    public void givenWrongUsername_whenLogin_thanUserIsNotLoggedIn() throws Exception {
        provider.login("wrong login", "zaq12wsx");
        assertFalse(provider.isLoggedIn());
    }

    @Test
    public void givenCorrectUsernameAndPassword_whenLogin_thanUserIsLoggedIn() throws Exception {
        provider.login("admin", "zaq12wsx");
        assertTrue(provider.isLoggedIn());
    }

    @Test(expected = AuthorizationException.class)
    public void givenUnauthorizedUser_whenLogout_throwsException() throws Exception {
        provider.logout();
    }

    @Test
    public void whenLogout_thanUserIsNotLoggedIn() throws Exception {
        provider.login("admin", "zaq12wsx");
        assertTrue(provider.isLoggedIn());
        provider.logout();
        assertFalse(provider.isLoggedIn());
    }
}