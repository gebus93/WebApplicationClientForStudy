package pl.gebickionline.ui.controller.news;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import pl.gebickionline.communication.News;
import pl.gebickionline.ui.Main;

/**
 * Created by Łukasz on 2016-01-31.
 */
public class NewsEditor extends BorderPane {

    private final HTMLEditor editor = new HTMLEditor();
    private final TextField title = new TextField();
    private final Button saveButton = new Button("Zapisz");
    private final Button cancelButton = new Button("Anuluj");


    public NewsEditor(News news, String title) {
        super();

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

        cancelButton.setOnMouseClicked(event -> Main.getInstance().setCenter(new NewsList()));

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(saveButton, cancelButton);
        gridPane.add(buttonBar, 1, 2);
        return gridPane;
    }
}
