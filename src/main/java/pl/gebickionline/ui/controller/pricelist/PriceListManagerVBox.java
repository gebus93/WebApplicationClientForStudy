package pl.gebickionline.ui.controller.pricelist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Łukasz on 2016-01-17.
 */
public class PriceListManagerVBox extends VBox {
    public PriceListManagerVBox() {
        super();
        getStyleClass().addAll("pricelist-wrapper");

        Label createNewGroup = createLabelButton("Dodaj grupę usług");
        Label createNewService = createLabelButton("Dodaj usługę");

        getChildren().addAll(createNewGroup, createNewService);
    }

    @NotNull
    private Label createLabelButton(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("pricelist-label-button");
        return label;
    }

}
