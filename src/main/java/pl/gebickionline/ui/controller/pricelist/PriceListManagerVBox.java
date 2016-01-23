package pl.gebickionline.ui.controller.pricelist;

import javafx.collections.ObservableList;
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
    private final ObservableList<ManageableGroup> groups;
    private Optional<VBox> managementMenu = Optional.empty();

    public PriceListManagerVBox(ObservableList<ManageableGroup> groups) {
        super();
        this.groups = groups;
        getStyleClass().addAll("pricelist-wrapper");

        Label createNewGroup = new LabelButton("Dodaj grupę usług");
        Label createNewService = new LabelButton("Dodaj usługę");
        Label generatePriceList = new LabelButton("Generuj cennik (PDF)");

        createNewGroup.setOnMouseClicked(event -> showCreateGroupDialog());
        generatePriceList.setOnMouseClicked(event -> generatePriceList());

        getChildren().addAll(
                new SideBarHeaderLabel("Opcje podstawowe"),
                createNewGroup,
                createNewService,
                generatePriceList
        );
    }

    private void showCreateGroupDialog() {
        ServiceGroupDialog serviceGroupDialog = new ServiceGroupDialog(groups);
        serviceGroupDialog.setTitle("Tworzenie nowej grupy usług");
        Optional<ManageableGroup> manageableGroup = serviceGroupDialog.showAndWait();

        if (manageableGroup.isPresent()) {
            final Long[] newOrdinal = {1L};
            ManageableGroup newGroup = manageableGroup.get();
            groups.add((int) newGroup.ordinal(), newGroup);
            groups.stream().forEachOrdered(g -> g.ordinal(newOrdinal[0]++));
            PriceListManager.getInstance().updateManageablePriceList(new ManageablePriceList(groups));
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

        managementMenu = Optional.of(new ServiceManagementMenuVBox(service));
        getChildren().add(managementMenu.get());
    }

    public void hideManagementMenu() {
        if (!managementMenu.isPresent())
            return;

        getChildren().remove(managementMenu.get());
    }
}
