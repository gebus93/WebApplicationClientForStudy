package pl.gebickionline.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.pricelist.PriceList;
import pl.gebickionline.ui.controller.pricelist.*;

/**
 * Created by Łukasz on 2016-01-17.
 */
public class PriceListController {
    @FXML
    public SplitPane splitPane;

    private PriceListContainerVBox priceListContainer = new PriceListContainerVBox();


    public PriceListManagerVBox priceListManagerBox;

    @FXML
    private void initialize() {
        PriceList priceList = PriceList.getInstance();
        priceList.setPriceListController(this);

        priceListManagerBox = new PriceListManagerVBox();
        splitPane.getItems().addAll(
                priceListContainer.getWrappedByScrollPane(),
                priceListManagerBox
        );
        splitPane.setDividerPositions(0.75f);

        ManageablePriceList manageablePriceList = PriceListManager.getInstance().manageablePriceList();
        priceList.changeContent(manageablePriceList.asList());
    }

    public void hideManagementMenu() {
        priceListManagerBox.hideManagementMenu();
    }

    public void showManagementMenu(ManageableService service) {
        priceListManagerBox.showManagementMenu(service);
    }

    public PriceListContainerVBox getPriceListContainer() {
        return priceListContainer;
    }

    public void showManagementMenu(ManageableGroup group) {
        priceListManagerBox.showManagementMenu(group);
    }

    public PriceListManagerVBox getPriceListManagerBox() {
        return priceListManagerBox;
    }
}
