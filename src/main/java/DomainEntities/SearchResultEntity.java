package DomainEntities;

import Utils.Common.ProvidersEnum;

public class SearchResultEntity {

    public String mAffiliateURL;
    public String mPictureURL;
    public String mPrice;
    public double mRank;
    public int mNumOfRanks;
    public ProvidersEnum mProvider;
    public String mDescription;
    public String mTitle;
    public String mCondition;
    public int mItemSold;

    public SearchResultEntity() {}

    public SearchResultEntity(String affiliateURL, String pictureURL, String price, double rank, int numOfRanks,
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
