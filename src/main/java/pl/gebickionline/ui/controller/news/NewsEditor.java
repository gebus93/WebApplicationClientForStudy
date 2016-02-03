package pl.gebickionline.ui.controller.news;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import pl.gebickionline.communication.News;
import pl.gebickionline.news.NewsManager;
import pl.gebickionline.ui.Main;
import pl.gebickionline.ui.controller.ConfirmationAlert;

import java.util.Optional;

/**
 * Created by Łukasz on 2016-01-31.
 */
public class NewsEditor extends BorderPane {

    private final HTMLEditor editor = new HTMLEditor();
    private final TextField title = new TextField();
    private Optional<Long> newsId;


    public NewsEditor(News news, String title) {
        super();
        newsId = Optional.ofNullable(news.id());
        setTop(getTitle(title));
        setCenter(getEditor(news));

    }

    private Label getTitle(String title) {
        Label label = new Label(title);
        label.getStyleClass().addAll("pricelist-group-name");
        return label;
    }

    public NewsEditor(String title) {
        this(new News.Builder().build(), title);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setMaxHeight(Double.MAX_VALUE);
        return label;
    }

    private GridPane getEditor(News news) {
        GridPane gridPane = new GridPane();
        this.title.setText(news.title());
        this.editor.setHtmlText(news.content());
        this.editor.setMaxWidth(Double.MAX_VALUE);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.setMaxHeight(Double.MAX_VALUE);
        gridPane.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(createLabel("Tytuł"), 0, 0);
        gridPane.add(this.title, 1, 0);

        Label contentLabel = createLabel("Treść");
        contentLabel.setAlignment(Pos.TOP_LEFT);
        gridPane.add(contentLabel, 0, 1);
        gridPane.add(editor, 1, 1);
        GridPane.setHgrow(editor, Priority.ALWAYS);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(getSaveButton(), getCancelButton());
        gridPane.add(buttonBar, 1, 2);
        return gridPane;
    }

    private Button getCancelButton() {
        Button button = new Button("Anuluj");
        button.setOnMouseClicked(event -> Main.getInstance().setCenter(new NewsList()));
        return button;
    }

    private Button getSaveButton() {
        Button button = new Button("Zapisz");
        button.setOnMouseClicked(event -> {
            if (!dataIsValid()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd walidacji danych");
                alert.setHeaderText("Nieprawidłowo wypełniony formularz");
                alert.setContentText("Wszystkie pola z formularza muszą zostać wypełnione");
                alert.show();
                return;
            }
            NewsManager newsManager = NewsManager.getInstance();
            if (newsId.isPresent()) {
                Optional<ButtonType> buttonType = new ConfirmationAlert(
                        "Potwierdzenie zapisania zmian",
                        "Czy na pewno chcesz zapisać zmiany w edytowany wpisie?\nDane zostaną zmienione bezpowrotnie."
                ).showAndWait();

                if (buttonType.get() == ButtonType.YES)
                    newsManager.modifyNews(newsId.get(), title.getText(), editor.getHtmlText());
            } else
                newsManager.addNews(title.getText(), editor.getHtmlText());

            Main.getInstance().setCenter(new NewsList());
        });
        return button;
    }

    private boolean dataIsValid() {

        return title.getText() != null && !title.getText().isEmpty()
                && editor.getHtmlText() != null && !editor.getHtmlText().isEmpty();
    }
}
