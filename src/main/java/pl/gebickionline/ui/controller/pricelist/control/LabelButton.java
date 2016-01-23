package pl.gebickionline.ui.controller.pricelist.control;

import javafx.scene.control.Label;

/**
 * Created by Łukasz on 2016-01-23.
 */
public class LabelButton extends Label {
    public LabelButton(String text) {
        super(text);
        getStyleClass().add("pricelist-label-button");
    }
}
