package Services.SearchService.SerachProviders;

public class EBaySearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static EBaySearchProvider instance;

    public static EBaySearchProvider getInstance() {
        if (instance == null) {
            synchronized(lock) {
                if (instance == null) {
                    instance = new EBaySearchProvider();
                }
            }
        }
        return instance;
    }

    private EBaySearchProvider() {

    }
}
