package Utils.HttpRequests;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetRequest {

    public String get(String urlRequest) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlRequest);
        HttpResponse response = client.execute(request);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String inputLine;
        StringBuffer data = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            data.append(inputLine);
        }
        in.close();

        return data.toString();
    }

}
