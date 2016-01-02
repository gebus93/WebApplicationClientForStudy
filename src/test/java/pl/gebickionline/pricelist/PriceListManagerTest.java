package pl.gebickionline.pricelist;

import org.junit.*;
import pl.gebickionline.security.AuthorizationProvider;

/**
 * Created by Łukasz on 2016-01-02.
 */
public class PriceListManagerTest {
    private AuthorizationProvider authorizationProvider;
    private PriceListManager priceListManager;

    @Before
    public void setUp() throws Exception {
        authorizationProvider = AuthorizationProvider.getInstance();
        priceListManager = PriceListManager.getInstance();
    }


    @Test
    public void testManageablePriceList() throws Exception {

        authorizationProvider.login("admin", "zaq12wsx");
        System.out.println(priceListManager.manageablePriceList().asList());
    }
}