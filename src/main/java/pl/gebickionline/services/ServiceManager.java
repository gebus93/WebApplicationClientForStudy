package pl.gebickionline.services;

import pl.gebickionline.communication.*;
import pl.gebickionline.services.pricelist.*;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class ServiceManager {
    private static ServiceManager instance;
    private final CommunicationManager communicationManager;

    private ServiceManager() {
        communicationManager = CommunicationManagerImpl.getInstance();
    }

    public static ServiceManager getInstance() {
        if (instance == null)
            instance = new ServiceManager();
        return instance;
    }

    public PriceList priceList() {
        Map<Long, pl.gebickionline.services.pricelist.Group> groups = communicationManager.getVisibleGroups()
                .stream()
                .collect(Collectors.toMap(Group::id, g -> new pl.gebickionline.services.pricelist.Group(g.groupName(), g.ordinal())));

        if (groups.isEmpty())
            return new EmptyPriceList();

        communicationManager
                .getVisibleServices()
                .forEach(s -> groups.get(s.groupID()).addService(s));
        return new PriceList(groups.values());
    }

    public static void main(String[] args) {
        ServiceManager instance = ServiceManager.getInstance();
        System.out.println(instance.priceList().asList());
    }

}
