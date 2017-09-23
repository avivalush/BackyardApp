package Services.SearchService.SerachProviders;

import DomainEntities.SearchResultEntity;

import java.io.IOException;
import java.util.List;

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

    @Override
    public List<SearchResultEntity> search(String searchString) throws IOException {
        return null;
    }
}
