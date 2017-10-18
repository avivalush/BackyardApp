package DomainEntities;

import Utils.Common.ProvidersEnum;

public class SearchResultEntity {

    private String mAffiliateURL;
    private String mPictureURL;
    // in dollars
    private double mPrice;
    private double mRank;
    private int mNumOfRanks;
    private ProvidersEnum mProvider;
    private String mDescription;
    private String mTitle;
    private String mCondition;
    private int mItemSold;

    public SearchResultEntity(String affiliateURL, String pictureURL, double price, double rank, int numOfRanks,
                              ProvidersEnum provider, String description, String title, String condition, int itemSold) {
        mAffiliateURL = affiliateURL;
        mPictureURL = pictureURL;
        mPrice = price;
        mRank = rank;
        mNumOfRanks = numOfRanks;
        mProvider = provider;
        mDescription = description;
        mTitle = title;
        mCondition = condition;
        mItemSold = itemSold;
    }

}
