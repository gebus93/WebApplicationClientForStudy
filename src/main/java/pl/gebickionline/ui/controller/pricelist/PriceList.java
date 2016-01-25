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
        groupNameLabel.setOnMouseClicked(event -> changeSelectedGroup(groupNameLabel, group.id()));

        group.services()
                .stream()
                .sorted()
                .forEachOrdered(this::addServiceToView);
    }

    private void changeSelectedGroup(Label selectedGroupName, Long groupId) {
        getPriceListContainerVBox().changeSelectedItem(selectedGroupName);
    }

    private void addServiceToView(ManageableService service) {
        HBox serviceBox = getPriceListContainerVBox().addService(service);
        serviceBox.setOnMouseClicked(event -> changeSelectedService(serviceBox, service));
    }

    public void changeContent(List<ManageableGroup> manageableGroups) {
        groups.clear();
        groups.addAll(manageableGroups);
        groups.sort((o1, o2) -> Long.valueOf(o1.ordinal()).compareTo(o1.ordinal()));

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
        final Long[] newOrdinal = {1L};
        groups.add((int) newGroup.ordinal(), newGroup);
        groups.stream().forEachOrdered(g -> g.ordinal(newOrdinal[0]++));
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
}
