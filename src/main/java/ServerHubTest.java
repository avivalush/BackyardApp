import DomainEntities.SearchResultEntity;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServerHubTest {

    ServerHub hub = new ServerHub();

    @Test
    public void searchAllProviders() throws Exception {
        List<SearchResultEntity> results = hub.searchAllProviders("whiskey");
        assert(results.size() == 9);
    }

    @Test
    public void searchSpecificProvider() throws Exception {
    }

}