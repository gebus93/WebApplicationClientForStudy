package pl.gebickionline.pricelist;

import pl.gebickionline.communication.*;

import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class PriceListManager {
    private static PriceListManager instance;
    private final CommunicationManager communicationManager;

    private PriceListManager() {
        communicationManager = CommunicationManagerImpl.getInstance();
    }

    public static PriceListManager getInstance() {
        if (instance == null)
            instance = new PriceListManager();
        return instance;
    }

    public ManageablePriceList manageablePriceList() {
        Map<Long, ManageableGroup> groups = communicationManager.getGroups()
                .stream()
                .collect(Collectors.toMap(g -> g.id(), g -> new ManageableGroup(g.id(), g.groupName(), g.ordinal())));

        if (groups.isEmpty())
            return new ManageablePriceList(emptyList());

        communicationManager
                .getServices()
                .forEach(s -> groups.get(s.groupID()).addService(s));
        return new ManageablePriceList(groups.values());

    }

    public PriceList priceList() {
        Map<Long, Group> groups = communicationManager.getVisibleGroups()
                .stream()
                .collect(Collectors.toMap(g -> g.id(), g -> new Group(g.groupName(), g.ordinal())));

        if (groups.isEmpty())
            return new PriceList(emptyList());

        communicationManager
                .getVisibleServices()
                .forEach(s -> groups.get(s.groupID()).addService(s));
        return new PriceList(groups.values());
    }


    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("test.pdf");
        fos.write(getInstance().priceList().asPdf());
        fos.close();
    }
}
