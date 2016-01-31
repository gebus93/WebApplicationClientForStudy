package pl.gebickionline.ui.controller.pricelist;

import javafx.collections.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.PriceListController;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static pl.gebickionline.ui.controller.pricelist.MovementDirection.UP;
import static pl.gebickionline.ui.controller.pricelist.PriceListContainerVBox.SelectionType.SELECTED;

/**
 * Created by Łukasz on 2016-01-23.
 */
public class PriceList {
    private static PriceList instance;
    private final PriceListManager priceListManager = PriceListManager.getInstance();

    private ObservableList<ManageableGroup> groups;
    private Optional<PriceListController> priceListController = Optional.empty();
    private Optional<PriceListContainerVBox> priceListContainer = Optional.empty();

    private PriceList() {
        groups = FXCollections.observableArrayList();
        groups.addListener((ListChangeListener) c -> updateGroupsContainer(c.getList()));

    }

    public void setPriceListController(PriceListController priceListController) {
        this.priceListController = Optional.ofNullable(priceListController);
        this.priceListContainer = Optional.ofNullable(priceListController.getPriceListContainer());
    }

    public static PriceList getInstance() {
        if (instance == null) {
            instance = new PriceList();
        }
        return instance;
    }

    private void updateGroupsContainer(List<ManageableGroup> list) {
        getPriceListContainerVBox().clear();

        list.stream()
                .sorted((g1, g2) -> Long.valueOf(g1.ordinal()).compareTo(g2.ordinal()))
                .forEachOrdered(this::addGroupToView);
    }

    private PriceListContainerVBox getPriceListContainerVBox() {
        return priceListContainer.get();
    }

    private void addGroupToView(ManageableGroup group) {
        Label groupNameLabel = getPriceListContainerVBox().addGroup(group.groupName());
        groupNameLabel.setOnMouseClicked(event -> changeSelectedGroup(groupNameLabel, group));

        group.services()
                .stream()
                .sorted()
                .forEachOrdered(this::addServiceToView);
    }

    private void changeSelectedGroup(Label selectedGroupName, ManageableGroup group) {
        PriceListContainerVBox.SelectionType selectionType = getPriceListContainerVBox().changeSelectedItem(selectedGroupName);

        if (selectionType == SELECTED)
            getPriceListController().showManagementMenu(group);
        else
            getPriceListController().hideManagementMenu();
    }

    private void addServiceToView(ManageableService service) {
        HBox serviceBox = getPriceListContainerVBox().addService(service);
        serviceBox.setOnMouseClicked(event -> changeSelectedService(serviceBox, service));
    }

    public void changeContent(List<ManageableGroup> manageableGroups) {
        groups.clear();
        sortGroups();
        groups.addAll(manageableGroups);
        priceListManager.updateManageablePriceList(asManageablePriceList());

    }

    public void changeSelectedService(HBox serviceBox, ManageableService service) {
        PriceListContainerVBox.SelectionType selectionType = getPriceListContainerVBox().changeSelectedItem(serviceBox);

        if (selectionType == SELECTED)
            getPriceListController().showManagementMenu(service);
        else
            getPriceListController().hideManagementMenu();

    }

    private PriceListController getPriceListController() {
        return priceListController.get();
    }

    public void addGroup(ManageableGroup newGroup) {
        groups.add((int) newGroup.ordinal(), newGroup);
        recalculateOrdinals();
    }

    public ManageablePriceList asManageablePriceList() {
        return new ManageablePriceList(groups);
    }

    public List<ManageableGroup> getGroupList() {
        return groups.stream().sorted().collect(toList());
    }

    public void moveService(ManageableService service, MovementDirection direction) {
        Optional<ManageableGroup> groupOptional = groups.stream().filter(g -> g.services().contains(service)).findFirst();

        if (!groupOptional.isPresent()) {
            throw new RuntimeException("Grupa nie została znaleziona");
        }

        ManageableGroup group = groupOptional.get();
        group.moveService(service, direction);
        changeContent(new ArrayList<>(groups));
    }

    public void moveGroup(ManageableGroup group, MovementDirection direction) {
        long newOrdinal;
        sortGroups();
        if (direction == UP) {
            if (group.ordinal() == 1)
                return;
            newOrdinal = group.ordinal() - 1;
            int index = (int) newOrdinal - 1;
            groups.get(index).ordinal(group.ordinal());
        } else {
            if (group.ordinal() == groups.size())
                return;

            newOrdinal = group.ordinal() + 1;
            groups.get((int) newOrdinal - 1).ordinal(group.ordinal());
        }

        group.ordinal(newOrdinal);
        changeContent(new ArrayList<>(groups));
    }

    private void sortGroups() {
        groups.sort((o1, o2) -> Long.valueOf(o1.ordinal()).compareTo(o2.ordinal()));
    }

    public void addService(ManageableService manageableService) {
        groups.stream()
                .filter(g -> Objects.equals(g.id(), manageableService.groupId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Grupa nie istnieje"))
                .addService(manageableService);

        updateGroupsContainer(groups);
    }

    public void removeService(ManageableService service) {
        groups.stream()
                .filter(g -> Objects.equals(g.id(), service.groupId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Grupa nie istnieje"))
                .removeService(service);

        priceListManager.updateManageablePriceList(asManageablePriceList());
        updateGroupsContainer(groups);
    }

    public void removeGroup(ManageableGroup group) {
        ManageableGroup groupInstance = findGroupInList(group);

        groups.remove(groupInstance);
        recalculateOrdinals();
        priceListManager.updateManageablePriceList(asManageablePriceList());
    }

    private ManageableGroup findGroupInList(ManageableGroup group) {
        return groups.stream()
                .filter(g -> Objects.equals(g.id(), group.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Grupa nie istnieje"));
    }

    public void updateGroup(ManageableGroup group) {
        ManageableGroup groupInstance = findGroupInList(group);
        groupInstance.merge(group);
        recalculateOrdinals();
        updateGroupsContainer(groups);
    }

    private void recalculateOrdinals() {
        final Long[] newOrdinal = {1L};
        groups.stream().forEachOrdered(g -> g.ordinal(newOrdinal[0]++));
    }
}
