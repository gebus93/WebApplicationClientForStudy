package pl.gebickionline.communication;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Łukasz on 2015-12-02.
 */
public interface CommunicationManager {
    boolean login(@NotNull String username, @NotNull String password);

    void logout();

    long addNews(@NotNull String title, @NotNull String content);

    void modifyNews(long newsId, @NotNull String title, @NotNull String content);

    void deleteNews(long newsId);

    List<News> getNewsList();

    void turnOnNewsVisibility();

    void turnOffNewsVisibility();

    NewsVisibility getNewsVisibility();

    List<Group> getVisibleGroups();

    List<Service> getVisibleServices();

    void updateGroupList(List<Group> groups);

    void updateServiceList(List<Service> services);

    List<Group> getGroups();

    List<Service> getServices();
}
