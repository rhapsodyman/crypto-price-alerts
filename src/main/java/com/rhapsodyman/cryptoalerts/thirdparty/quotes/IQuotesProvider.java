package com.rhapsodyman.cryptoalerts.thirdparty.quotes;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

public interface IQuotesProvider {

    BigDecimal getCryptoPrice(String symbol) throws IOException, URISyntaxException;
}
