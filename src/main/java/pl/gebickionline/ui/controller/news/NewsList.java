package pl.gebickionline.ui.controller.news;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import pl.gebickionline.news.NewsManager;

/**
 * Created by Łukasz on 2016-01-31.
 */
public class NewsList extends BorderPane {
    private TableView table = new TableView();
    private final VBox header = new VBox();
    private final Label headerTitle = new Label("Lista aktualności");
    private final ObservableList<NewsVO> newsList = FXCollections.observableArrayList();

    public NewsList() {
        super();
        headerTitle.getStyleClass().addAll("pricelist-group-name");
        NewsManager
                .getInstance()
                .getNewsList()
                .stream()
                .forEach(n -> newsList.add(new NewsVO(n)));

        initializeTable();

        header.getChildren().addAll(headerTitle, new NewsVisibilityManagementBar());
        setTop(header);
        setCenter(table);
    }

    private void initializeTable() {
        table.setMaxHeight(Double.MAX_VALUE);
        TableColumn id = new TableColumn("ID");
        TableColumn title = new TableColumn("Tytuł");
        TableColumn creationDate = new TableColumn("Data utworzenia");
        TableColumn updateDate = new TableColumn("Ostatnia modyfikacja");
        TableColumn actions = new TableColumn("Akcje");

        id.getStyleClass().add("center");
        creationDate.getStyleClass().add("center");
        updateDate.getStyleClass().add("center");
        actions.getStyleClass().add("center");

        title.getStyleClass().addAll("center-left");

        changeColumnWidth(id, 0.1);
        changeColumnWidth(title, 0.35);
        changeColumnWidth(creationDate, 0.2);
        changeColumnWidth(updateDate, 0.2);
        changeColumnWidth(actions, 0.15);

        id.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("id"));
        title.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("title"));
        creationDate.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("creationDate"));
        updateDate.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("lastUpdateDate"));
        actions.setCellValueFactory(new PropertyValueFactory<NewsVO, Long>("id"));
        actions.setCellFactory(param -> new ActionsCell());

        table.getColumns().addAll(
                id,
                title,
                creationDate,
                updateDate,
                actions
        );
        table.setItems(newsList);

        id.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(id);

    }

    private void changeColumnWidth(TableColumn column, double percentOfTableWidth) {
        DoubleBinding width = table.widthProperty().multiply(percentOfTableWidth);
        column.prefWidthProperty().bind(width);

    }
}
