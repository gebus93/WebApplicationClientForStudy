package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pl.gebickionline.pricelist.*;
import pl.gebickionline.ui.controller.pricelist.control.*;

import java.io.*;
import java.util.Optional;

/**
 * Created by Łukasz on 2016-01-17.
 */
public class PriceListManagerVBox extends VBox {
    private Optional<VBox> managementMenu = Optional.empty();

    public PriceListManagerVBox() {
        super();
        getStyleClass().addAll("pricelist-wrapper");

        Label createNewGroup = new LabelButton("Dodaj grupę usług");
        Label createNewService = new LabelButton("Dodaj usługę");
        Label generatePriceList = new LabelButton("Generuj cennik (PDF)");

        createNewGroup.setOnMouseClicked(event -> showCreateGroupDialog());
        createNewService.setOnMouseClicked(event -> showCreateServiceDialog());
        generatePriceList.setOnMouseClicked(event -> generatePriceList());

        getChildren().addAll(
                new SideBarHeaderLabel("Opcje podstawowe"),
                createNewGroup,
                createNewService,
                generatePriceList
        );
    }

    private void showCreateGroupDialog() {
        ServiceGroupDialog serviceGroupDialog = new ServiceGroupDialog();
        serviceGroupDialog.setTitle("Tworzenie nowej grupy usług");
        Optional<ManageableGroup> manageableGroup = serviceGroupDialog.showAndWait();

        if (manageableGroup.isPresent()) {

            PriceList priceList = PriceList.getInstance();
            priceList.addGroup(manageableGroup.get());
            PriceListManager.getInstance().updateManageablePriceList(priceList.asManageablePriceList());

        }
    }


    private void showCreateServiceDialog() {
        ServiceDialog serviceDialog = new ServiceDialog();
        serviceDialog.setTitle("Tworzenie nowej usługi");
        Optional<ManageableService> manageableService = serviceDialog.showAndWait();

        if (manageableService.isPresent()) {

            PriceList priceList = PriceList.getInstance();
            priceList.addService(manageableService.get());
            PriceListManager.getInstance().updateManageablePriceList(priceList.asManageablePriceList());
        }
    }

    private void generatePriceList() {
        Optional<File> fileLocation = choosePriceListLocation();

        if (!fileLocation.isPresent())
            return;

        byte[] pdf = PriceListManager.getInstance().priceList().asPdf();
        File file = fileLocation.get();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(pdf);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<File> choosePriceListLocation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(null);
        return Optional.ofNullable(file);
    }

    public void showManagementMenu(ManageableService service) {
        if (managementMenu.isPresent())
            hideManagementMenu();

        managementMenu = Optional.of(new ServiceManagementMenuVBox(this, service));
        getChildren().add(managementMenu.get());
    }

    public void hideManagementMenu() {
        if (!managementMenu.isPresent())
            return;

        getChildren().remove(managementMenu.get());
    }

    public void showManagementMenu(ManageableGroup group) {
        if (managementMenu.isPresent())
            hideManagementMenu();

        managementMenu = Optional.of(new GroupManagementMenuVBox(this,group));
        getChildren().add(managementMenu.get());

    }
}
