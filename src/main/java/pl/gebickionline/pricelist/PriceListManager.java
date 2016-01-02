package pl.gebickionline.pricelist;

import pl.gebickionline.communication.*;
import pl.gebickionline.communication.Service;

import java.io.*;
import java.util.*;
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
                .collect(Collectors.toMap(g -> g.id(), g -> new ManageableGroup(g.id(), g.groupName(), g.visible(), g.ordinal())));

        if (groups.isEmpty())
            return new ManageablePriceList(emptyList());

        communicationManager
                .getServices()
                .forEach(s -> groups.get(s.groupID()).addService(s));
        return new ManageablePriceList(groups.values());

    }

    public ManageablePriceList updateManageablePriceList(ManageablePriceList priceList) {
        List<ManageableGroup> groups = priceList.asList();

        List<pl.gebickionline.communication.Group> newGroupList = groups.stream()
                .map(g -> new pl.gebickionline.communication.Group.Builder()
                        .withId(g.id())
                        .withGroupName(g.groupName())
                        .withOrdinal(g.ordinal())
                        .withVisible(g.visible())
                        .build())
                .collect(Collectors.toList());

        communicationManager.updateGroupList(newGroupList);
        Map<Long, pl.gebickionline.communication.Group> updatedGroupList = communicationManager.getGroups().stream().collect(Collectors.toMap(g -> g.ordinal(), g -> g));

        groups.stream()
                .filter(g -> g.id() == null)
                .forEach(g -> g.id(updatedGroupList.get(g.ordinal()).id()));

        List<Service> newServiceList = groups.stream()
                .collect(Collectors.toMap(g -> g.id(), g -> g.services()
                        .stream()
                        .map(s -> new Service.Builder()
                                .withId(s.id())
                                .withServiceName(s.serviceName())
                                .withMaxPrice(s.maxPrice())
                                .withMinPrice(s.minPrice())
                                .withPrice(s.price())
                                .withVisible(s.isVisible())
                                .withOrdinal(s.ordinal())
                                .build())
                        .collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(e -> e.getValue()
                        .stream()
                        .map(s -> new Service.Builder(s)
                                .withGroupID(e.getKey())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        communicationManager.updateServiceList(newServiceList);
        return manageablePriceList();
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

}
