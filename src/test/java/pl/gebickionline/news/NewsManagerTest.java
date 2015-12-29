package pl.gebickionline.news;

import org.junit.*;
import pl.gebickionline.communication.*;
import pl.gebickionline.security.AuthorizationProvider;

import java.util.List;

import static org.junit.Assert.*;

public class NewsManagerTest {
    private AuthorizationProvider authorizationProvider;
    private NewsManager newsManager;

    @Before
    public void setUp() throws Exception {
        authorizationProvider = AuthorizationProvider.getInstance();
        newsManager = NewsManager.getInstance();
    }

    @Test
    public void testNewsManagement() throws Exception {
        authorizationProvider.login("admin", "zaq12wsx");
        int sizeBeforeTest = newsManager.getNewsList().size();

        String firstTitle = "tytuł wiadomości";
        String firstContent = "Przykładowa treść";

        long newsId = newsManager.addNews(firstTitle, firstContent);
        assertTrue(newsId > 0);
        assertNewsOnList(newsId, firstTitle, firstContent);

        String changedTitle = "Nowy tytuł";
        String changedContent = "Nowa treść";
        newsManager.modifyNews(newsId, changedTitle, changedContent);
        assertNewsOnList(newsId, changedTitle, changedContent);

        newsManager.deleteNews(newsId);
        assertEquals(sizeBeforeTest, newsManager.getNewsList().size());
    }

    @Test
    public void testNewsVisibilityManagement() throws Exception {
        newsManager.turnOffNewsVisibility();
        assertEquals(NewsVisibility.INVISIBLE, newsManager.getNewsVisibility());

        newsManager.turnOnNewsVisibility();
        assertEquals(NewsVisibility.VISIBLE, newsManager.getNewsVisibility());

    }

    private void assertNewsOnList(long newsId, String title, String content) {
        List<News> newsList = newsManager.getNewsList();
        News news = newsList.stream()
                .filter(n -> newsId == n.id())
                .findFirst()
                .orElseThrow(AssertionError::new);

        assertEquals(title, news.title());
        assertEquals(content, news.content());

    }
}