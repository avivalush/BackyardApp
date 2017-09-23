package Services.SearchService.SerachProviders;

import DomainEntities.SearchResultEntity;
import Utils.HttpRequests.GetRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AmazonSearchProvider implements ISearchProvider {

    private static final Object lock = new Object();
    private static AmazonSearchProvider instance;
    private static final String ACCESS_KEY_ID = "AKIAJG67YBC3DSRQ5SWQ";
    private static final String ASSOCIATE_TAG = "buyfiapp-20";
    private static final String SECRET_KEY = "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";
    private static final String ENDPOINT = "webservices.amazon.com";
    private static final String requestBaseURL = "http://%s/onca/xml?AWSAccessKeyId=%s" +
            "&AssociateTag=%s&Keywords=%s&Operation=ItemSearch&ResponseGroup=" +
            "Images%s2CItemAttributes%s2COffers%s2CSalesRank&SearchIndex=All&Service=" +
            "AWSECommerceService&Timestamp=%s&Signature=%s";

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

    public List<SearchResultEntity> search(String searchURL) throws IOException {
        List<SearchResultEntity> searchResults = new ArrayList<SearchResultEntity>();

        String xmlResult = (new GetRequest()).get(instance.buildGetRequest(searchURL));

        return null;
    }

    private String buildGetRequest(String searchURL) throws Exception {

        String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
        String hour = new SimpleDateFormat("hh:mm:ss").format(new Date());
        //String timeStamp = String.format("%sT%sZ", date, hour);

        //TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        //df.setTimeZone(tz);
        String timeStamp = df.format(new Date());

        //String timeStamp = "2017-09-19T22:32:02Z"
        return String.format(requestBaseURL, ENDPOINT, ACCESS_KEY_ID, ASSOCIATE_TAG, searchURL,
                "%", "%", "%", timeStamp, getSignatureKey(SECRET_KEY, timeStamp, "", "AWSECommerceService"));
    }

    static byte[] HmacSHA256(String data, byte[] key) throws Exception {
        String algorithm="HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
        byte[] kDate = HmacSHA256(dateStamp, kSecret);
        byte[] kRegion = HmacSHA256(regionName, kDate);
        byte[] kService = HmacSHA256(serviceName, kRegion);
        byte[] kSigning = HmacSHA256("aws4_request", kService);
        return kSigning;
    }
}
