package pl.gebickionline.ui.controller.news;

import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pl.gebickionline.communication.News;
import pl.gebickionline.news.NewsManager;
import pl.gebickionline.ui.Main;
import pl.gebickionline.ui.controller.*;

import java.util.Optional;

class ActionsCell extends TableCell<NewsVO, Long> {
    @Override
    protected void updateItem(Long newsID, boolean empty) {
        if (newsID != null) {
            HBox box = new HBox();
            box.getStyleClass().addAll("center");

            VBox editButton = getImageButton("edit.png", "Edytuj wpis");
            VBox deleteButton = getImageButton("delete.png", "Usuń wpis");
            News news = NewsManager.getInstance().getNews(newsID);
            editButton.setOnMouseClicked(event -> Main.getInstance().setCenter(new NewsEditor(news)));
            deleteButton.setOnMouseClicked(event -> {
                Optional<ButtonType> buttonType = new ConfirmationAlert(
                        String.format("Usuwanie wpisu \"%s\"", news.title()),
                        "Czy na pewno chcesz usunąć wpis?"
                ).showAndWait();

                if (buttonType.get() == ButtonType.YES) {
                    NewsManager.getInstance().deleteNews(newsID);
                    Main.getInstance().setCenter(new NewsList());
                }
            });
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