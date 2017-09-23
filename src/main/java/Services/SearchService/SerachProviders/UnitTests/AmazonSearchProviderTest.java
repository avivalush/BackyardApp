package Services.SearchService.SerachProviders.UnitTests;

import Services.SearchService.SerachProviders.AmazonSearchProvider;
import org.junit.Test;

import static org.junit.Assert.*;

public class AmazonSearchProviderTest {
    @Test
    public void search() throws Exception {
        AmazonSearchProvider.getInstance().search("Pads");
    }

}