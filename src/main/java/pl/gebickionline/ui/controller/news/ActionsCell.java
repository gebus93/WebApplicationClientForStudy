package pl.gebickionline.ui.controller.news;

import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pl.gebickionline.ui.controller.ImageLoader;

class ActionsCell extends TableCell<NewsVO, Long> {
    @Override
    protected void updateItem(Long newsID, boolean empty) {
        if (newsID != null) {
            HBox box = new HBox();
            box.getStyleClass().addAll("center");

            VBox editButton = getImageButton("edit.png", "Edytuj wpis");
            VBox deleteButton = getImageButton("delete.png", "Usuń wpis");

            box.getChildren().addAll(editButton, deleteButton);
            setGraphic(box);
        }
    }

    private VBox getImageButton(String imageFile, String tooltip) {
        ImageView imageView = new ImageView(ImageLoader.load(imageFile));
        imageView.setCursor(Cursor.HAND);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(25);
        Tooltip.install(imageView, new FastTooltip(tooltip));
        VBox vBox = new VBox();
        vBox.getStyleClass().add("padding-horizontal");
        vBox.getChildren().add(imageView);
        return vBox;
    }
}