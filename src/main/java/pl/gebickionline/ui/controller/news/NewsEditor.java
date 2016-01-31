package pl.gebickionline.ui.controller.news;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import pl.gebickionline.communication.News;

/**
 * Created by Łukasz on 2016-01-31.
 */
public class NewsEditor extends GridPane {

    private final HTMLEditor editor = new HTMLEditor();
    private final TextField title = new TextField();

    public NewsEditor(News news) {
        super();

        title.setText(news.title());
        editor.setHtmlText(news.content());

        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20, 150, 10, 10));

        add(createLabel("Tytuł"), 0, 0);
        add(title, 1, 0);

        Label contentLabel = createLabel("Treść");
        contentLabel.setAlignment(Pos.TOP_LEFT);
        add(contentLabel, 0, 1);
        add(editor, 1, 1);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(
                new Button("Zapisz"),
                new Button("Anuluj")
        );
        add(buttonBar, 1, 2);

    }

    public NewsEditor() {
        this(new News.Builder().build());
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setMaxHeight(Double.MAX_VALUE);
        return label;
    }
}
