package pl.gebickionline.ui.controller;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.pricelist.*;

import java.util.List;

/**
 * Created by Łukasz on 2016-01-17.
 */
public class PriceListController {
    @FXML
    public SplitPane splitPane;

    private PriceListContainerVBox priceListContainer = new PriceListContainerVBox();

    private ObservableList<ManageableGroup> groups;
    public PriceListManagerVBox priceListManagerBox;

    @FXML
    private void initialize() {
        groups = FXCollections.observableArrayList();

        priceListManagerBox = new PriceListManagerVBox(groups);
        splitPane.getItems().addAll(
                priceListContainer.getWrappedByScrollPane(),
                priceListManagerBox
        );
        splitPane.setDividerPositions(0.75f);

        ManageablePriceList manageablePriceList = PriceListManager.getInstance().manageablePriceList();

        groups.addListener((ListChangeListener) c -> updateGroupsContainer(c.getList()));
        changeGroupList(manageablePriceList.asList());
    }

    private void changeGroupList(List<ManageableGroup> manageableGroups) {
        groups.clear();
        groups.addAll(manageableGroups);
        groups.sort((o1, o2) -> Long.valueOf(o1.ordinal()).compareTo(o1.ordinal()));
    }

    private void updateGroupsContainer(List<ManageableGroup> list) {
        priceListContainer.clear();
        list.forEach(this::addGroupToView);
    }

    private void addGroupToView(ManageableGroup group) {
        Label groupNameLabel = priceListContainer.addGroup(group.groupName());
        groupNameLabel.setOnMouseClicked(event -> changeSelectedGroup(groupNameLabel, group.id()));

        group.services().forEach(this::addServiceToView);
    }

    private void addServiceToView(ManageableService service) {
        HBox serviceBox = priceListContainer.addService(service);
        serviceBox.setOnMouseClicked(event -> changeSelectedService(serviceBox, service.id()));

    }

    private void changeSelectedService(HBox serviceBox, Long serviceId) {
        priceListContainer.changeSelectedItem(serviceBox);

    }

    private void changeSelectedGroup(Label selectedGroupName, Long groupId) {
        priceListContainer.changeSelectedItem(selectedGroupName);
    }
}
