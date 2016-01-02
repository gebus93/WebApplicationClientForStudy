package pl.gebickionline.pricelist;

import org.junit.*;
import pl.gebickionline.security.AuthorizationProvider;

import java.io.FileOutputStream;
import java.util.*;

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

        priceListManager.updateManageablePriceList(new ManageablePriceList(Collections.emptyList()));

        System.out.println(priceListManager.manageablePriceList().asList());
        priceListManager.updateManageablePriceList(newPriceList());

        System.out.println(priceListManager.manageablePriceList().asList());
    }

    private ManageablePriceList newPriceList() {
        ManageableGroup group1 = new ManageableGroup(null, "Nowa grupa 1", true, 1);
        ManageableGroup group2 = new ManageableGroup(null, "Nowa grupa 2", true, 2);

        group1.services(Collections.singletonList(serviceWithConcretePrice("Usługa 1", 20000, true, 1)));
        List<ManageableService> list = new ArrayList<>();
        list.add(serviceWithConcretePrice("Usługa 2", 30000, true, 2));
        list.add(serviceWithPriceRange("Usługa 3", 10000, 50000, true, 3));
        group2.services(list);

        return new ManageablePriceList(Arrays.asList(group1, group2));
    }

    private ManageableService serviceWithPriceRange(String name, long minPrice, long maxPrice, boolean visible, long ordinal) {
        return new ManageableService.Builder()
                .withId(null)
                .withServiceName(name)
                .withVisible(visible)
                .withMinPrice(minPrice)
                .withMaxPrice(maxPrice)
                .withOrdinal(ordinal)
                .build();
    }

    private ManageableService serviceWithConcretePrice(String name, long price, boolean visible, long ordinal) {
        return new ManageableService.Builder()
                .withId(null)
                .withServiceName(name)
                .withVisible(visible)
                .withPrice(price)
                .withOrdinal(ordinal)
                .build();
    }

    @Test
    public void testPdfPriceList() throws Exception {

        FileOutputStream fos = new FileOutputStream("test.pdf");
        fos.write(priceListManager.priceList().asPdf());
        fos.close();

    }
}