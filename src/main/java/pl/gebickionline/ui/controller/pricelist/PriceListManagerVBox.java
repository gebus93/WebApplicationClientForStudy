package pl.gebickionline.ui.controller.pricelist;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;
import pl.gebickionline.pricelist.*;

import java.io.*;
import java.util.Optional;

/**
 * Created by Łukasz on 2016-01-17.
 */
public class PriceListManagerVBox extends VBox {
    private final ObservableList<ManageableGroup> groups;

    public PriceListManagerVBox(ObservableList<ManageableGroup> groups) {
        super();
        this.groups = groups;
        getStyleClass().addAll("pricelist-wrapper");

        Label createNewGroup = createLabelButton("Dodaj grupę usług");
        Label createNewService = createLabelButton("Dodaj usługę");
        Label generatePriceList = createLabelButton("Generuj cennik (PDF)");

        createNewGroup.setOnMouseClicked(event -> showCreateGroupDialog());
        generatePriceList.setOnMouseClicked(event -> generatePriceList());

        getChildren().addAll(createNewGroup, createNewService, generatePriceList);
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

    @NotNull
    private Label createLabelButton(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("pricelist-label-button");
        return label;
    }

}
