package pl.gebickionline.ui.controller.news;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pl.gebickionline.communication.NewsVisibility;
import pl.gebickionline.news.NewsManager;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Łukasz on 2016-02-02.
 */
public class NewsVisibilityManagementBar extends HBox {

    private final NewsManager newsManager;

    public NewsVisibilityManagementBar() {
        ChoiceBox choiceBox = new ChoiceBox(observableArrayList("Włączona", "Wyłączona"));
        newsManager = NewsManager.getInstance();
        NewsVisibility newsVisibility = newsManager.getNewsVisibility();
        choiceBox.getSelectionModel().select(newsVisibility == NewsVisibility.VISIBLE ? 0 : 1);
        choiceBox.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> changeVisibility(newValue));

        Label label = new Label("Widoczność modułu aktualności w portalu: ");
        label.setPadding(new Insets(0, 5, 0, 25));
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(Double.MAX_VALUE);

        getChildren().addAll(label, choiceBox);
    }

    private void changeVisibility(Number newValue) {
        if (newValue.intValue() == 0)
            newsManager.turnOnNewsVisibility();
        else
            newsManager.turnOffNewsVisibility();

    }
}
