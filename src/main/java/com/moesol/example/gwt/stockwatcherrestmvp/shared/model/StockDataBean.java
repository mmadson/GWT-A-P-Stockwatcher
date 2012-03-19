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
package com.moesol.example.gwt.stockwatcherrestmvp.shared.model;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockDataBean implements StockData
{
    private String symbol;
    private double price;
    private double change;

    public StockDataBean()
    {

    }

    public StockDataBean(final String symbol, final double price, final double change)
    {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public double getPrice()
    {
        return price;
    }

    public double getChange()
    {
        return change;
    }

    public void setSymbol(final String symbol)
    {
        this.symbol = symbol;
    }

    public void setPrice(final double price)
    {
        this.price = price;
    }

    public void setChange(final double change)
    {
        this.change = change;
    }

    public double getChangePercent() {
        return 100.0 * this.change / this.price;
    }
}
