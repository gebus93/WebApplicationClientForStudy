package pl.gebickionline.ui.controller.news;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import pl.gebickionline.news.NewsManager;

/**
 * Created by Łukasz on 2016-01-31.
 */
public class NewsList extends VBox {
    private TableView table = new TableView();
    private final Label header = new Label("Lista aktualności");
    private final ObservableList<NewsVO> newsList = FXCollections.observableArrayList();

    public NewsList() {
        super();

        NewsManager
                .getInstance()
                .getNewsList()
                .stream()
                .forEach(n -> newsList.add(new NewsVO(n)));

        initializeTable();

        getChildren().addAll(
                header,
                table
        );
    }

    private void initializeTable() {
        table.setMaxHeight(Double.MAX_VALUE);
        TableColumn id = new TableColumn("ID");
        TableColumn title = new TableColumn("Tytuł");
        TableColumn creationDate = new TableColumn("Data utworzenia");
        TableColumn updateDate = new TableColumn("Ostatnia modyfikacja");
        TableColumn actions = new TableColumn("Akcje");

        changeColumnWidth(id, 0.1);
        changeColumnWidth(title, 0.25);
        changeColumnWidth(creationDate, 0.2);
        changeColumnWidth(updateDate, 0.2);
        changeColumnWidth(actions, 0.25);

        id.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("id"));
        title.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("title"));
        creationDate.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("creationDate"));
        updateDate.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("lastUpdateDate"));
        actions.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("id"));

        table.getColumns().addAll(
                id,
                title,
                creationDate,
                updateDate,
                actions
        );
        table.setItems(newsList);
    }

    private void changeColumnWidth(TableColumn column, double percentOfTableWidth) {
        DoubleBinding width = table.widthProperty().multiply(percentOfTableWidth);
        column.prefWidthProperty().bind(width);

    }
}
