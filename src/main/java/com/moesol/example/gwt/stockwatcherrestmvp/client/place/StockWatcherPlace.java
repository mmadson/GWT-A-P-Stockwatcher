/**
 * Copyright (C) 2011 Moebius Solutions, Inc. <http://www.moesol.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moesol.example.gwt.stockwatcherrestmvp.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.moesol.example.gwt.stockwatcherrestmvp.shared.util.StockSymbolHelper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockWatcherPlace
        extends Place
{
    private final String token;
    private final Set<String> stockSymbols;

    public StockWatcherPlace(final Set<String> stockSymbols)
    {
        this.stockSymbols = stockSymbols;
        this.token = toToken(stockSymbols);
    }

    public StockWatcherPlace(final String token)
    {
        this.stockSymbols = fromToken(token);
        this.token = toToken(stockSymbols);
    }

    private String toToken(final Set<String> stockSymbols)
    {
        if(stockSymbols.isEmpty())
        {
            return "";
        }

        String token = "q=";
        for(String symbol : stockSymbols)
        {
            token += symbol + ",";
        }
        return token.substring(0, token.length() - 1);
    }

    public String getToken()
    {
        return token;
    }

    public Set<String> getStockSymbols()
    {
        return stockSymbols;
    }

    private Set<String> fromToken(final String token)
    {
        Set<String> result = new HashSet<String>();
        if(!token.contains("q="))
        {
            return result;
        }

        for(final String stockSymbol : Arrays.asList(token.substring(token.indexOf(
                "q=") + "q=".length()).split(",")))
        {
            final String canonicalStockSymbol = StockSymbolHelper.toCanonicalFormat(
                    stockSymbol);
            if(StockSymbolHelper.isValidSymbol(canonicalStockSymbol))
            {
                result.add(canonicalStockSymbol);
            }
        }

        return result;
    }

    /**
     * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
     * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
     */
    @Prefix("StockWatcher")
    public static class Tokenizer
            implements PlaceTokenizer<StockWatcherPlace>
    {

        public StockWatcherPlace getPlace(final String token)
        {
            return new StockWatcherPlace(token);
        }

        public String getToken(final StockWatcherPlace place)
        {
            return place.getToken();
        }
    }

    @Override
    public String toString()
    {
        return "StockWatcherPlace{" + "token='" + token + '\'' + '}';
    }
}
