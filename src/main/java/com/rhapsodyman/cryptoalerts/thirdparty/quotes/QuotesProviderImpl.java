package com.rhapsodyman.cryptoalerts.thirdparty.quotes;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuotesProviderImpl implements IQuotesProvider {

    // see https://coinmarketcap.com/api/documentation/v1/#tag/cryptocurrency

    private final String baseURL;
    private final String apiKey;
    private static  final String quotesUrl = "/v1/cryptocurrency/quotes/latest";

    public QuotesProviderImpl(@Value("${quotes.baseUrl}") String baseURL, @Value("${quotes.apiKey}") String apiKey) {
        this.baseURL = baseURL;
        this.apiKey = apiKey;
    }

    @Override
    public BigDecimal getCryptoPrice(String symbol) throws IOException, URISyntaxException {
        System.out.println("Getting crypto price for symbol: " + symbol);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("symbol", symbol));

        String res = makeAPICall(baseURL + quotesUrl, params);
        String price = res.replaceAll(".*\"price\":(.+?),\".*", "$1");

        System.out.println(String.format("Got price %s for symbol %s", price.toString(), symbol));
        return new BigDecimal(price);
    }


    public String makeAPICall(String uri, List<NameValuePair> parameters) throws IOException, URISyntaxException {
        String responseContent = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }
        return responseContent;
    }
}