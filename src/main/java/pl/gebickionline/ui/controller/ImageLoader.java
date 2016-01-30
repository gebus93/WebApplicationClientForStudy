package pl.gebickionline.ui.controller;

import javafx.scene.image.Image;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-30.
 */
public class ImageLoader {
    private ImageLoader() {

    }

    public static Image load(String name) {
        return new Image(Main.class.getResourceAsStream("view/images/" + name));
    }
}
