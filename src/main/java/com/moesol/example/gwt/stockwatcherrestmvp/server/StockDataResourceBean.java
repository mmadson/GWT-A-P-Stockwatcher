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
package com.moesol.example.gwt.stockwatcherrestmvp.server;

import com.moesol.example.gwt.stockwatcherrestmvp.shared.model.StockDataBean;
import com.moesol.example.gwt.stockwatcherrestmvp.shared.model.StockData;
import com.moesol.example.gwt.stockwatcherrestmvp.shared.util.StockSymbolHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockDataResourceBean
{
    private List<StockData> stockData;
    private static final Random random = new Random();
    private static final double MAX_PRICE = 100.0;
    private static final double MAX_PRICE_CHANGE = 0.02;

    public StockDataResourceBean(final String[] stockSymbols)
    {
        stockData = new ArrayList<StockData>();
        for(String stockSymbol : stockSymbols)
        {
            if(StockSymbolHelper.isInvalidSymbol(stockSymbol))
                continue;

            final double price = random.nextDouble() * MAX_PRICE;
            final double change = price * MAX_PRICE_CHANGE * (random.nextDouble() * 2f - 1f);
            stockData.add(new StockDataBean(stockSymbol,price,change));
        }
    }

    public List<StockData> getStockData()
    {
        return stockData;
    }

    public void setStockData(final List<StockData> stockData)
    {
        this.stockData = stockData;
    }
}
