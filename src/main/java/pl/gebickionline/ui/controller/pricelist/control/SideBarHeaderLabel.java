package pl.gebickionline.ui.controller.pricelist.control;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 * Created by Łukasz on 2016-01-23.
 */
public class SideBarHeaderLabel extends Label {
    public SideBarHeaderLabel(String label) {
        super(label);
        getStyleClass().add("sidebar-header");
        setTextAlignment(TextAlignment.CENTER);
        setAlignment(Pos.CENTER);
    }
}
