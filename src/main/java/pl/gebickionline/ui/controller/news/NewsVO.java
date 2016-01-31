package pl.gebickionline.ui.controller.news;

import javafx.beans.property.*;
import pl.gebickionline.communication.News;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Łukasz on 2016-01-31.
 */
public class NewsVO {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private SimpleLongProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty creationDate;
    private SimpleStringProperty lastUpdateDate;

    public NewsVO(News news) {
        id = new SimpleLongProperty(news.id());
        title = new SimpleStringProperty(news.title());
        creationDate = new SimpleStringProperty(dateToString(news.creationTime()));
        lastUpdateDate = new SimpleStringProperty(dateToString(news.lastUpdateTime()));
    }

    private String dateToString(Date date) {
        return sdf.format(date);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getCreationDate() {
        return creationDate.get();
    }

    public SimpleStringProperty creationDateProperty() {
        return creationDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate.get();
    }

    public SimpleStringProperty lastUpdateDateProperty() {
        return lastUpdateDate;
    }
}
