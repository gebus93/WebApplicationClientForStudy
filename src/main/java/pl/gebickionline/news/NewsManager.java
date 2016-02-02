package pl.gebickionline.news;


import org.jetbrains.annotations.NotNull;
import pl.gebickionline.communication.*;

import java.util.List;

public class NewsManager {
    private static NewsManager instance;
    private CommunicationManager communicationManager;

    private NewsManager() {
        communicationManager = CommunicationManagerImpl.getInstance();
    }

    public long addNews(@NotNull String title, @NotNull String content) {
        return communicationManager.addNews(title, content);
    }

    public void modifyNews(long newsId, @NotNull String title, @NotNull String content) {
        communicationManager.modifyNews(newsId, title, content);
    }

    public void deleteNews(long newsId) {
        communicationManager.deleteNews(newsId);
    }

    public List<News> getNewsList() {
        return communicationManager.getNewsList();
    }

    public News getNews(long id) {
        return getNewsList().stream().filter(n -> n.id().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Wpis nie istnieje"));
    }

    public void turnOnNewsVisibility() {
        communicationManager.turnOnNewsVisibility();
    }

    public void turnOffNewsVisibility() {
        communicationManager.turnOffNewsVisibility();
    }

    public NewsVisibility getNewsVisibility() {
        return communicationManager.getNewsVisibility();
    }

    public static NewsManager getInstance() {
        if (instance == null)
            instance = new NewsManager();

        return instance;
    }
}
