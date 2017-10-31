package Services.SearchService.SerachProviders.UnitTests;

import Services.SearchService.SerachProviders.EBaySearchProvider;
import org.junit.Test;

public class EbaySearchProviderTest {
    @Test
    public void search() throws Exception {
        EBaySearchProvider.getInstance().search("iphone,6");
    }
}
