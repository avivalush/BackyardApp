package Services.SearchService.SerachProviders;

public class AmazonSearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static AmazonSearchProvider instance;

    public static AmazonSearchProvider getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new AmazonSearchProvider();
                }
            }
        }
        return instance;
    }

    private AmazonSearchProvider() {

    }
}
