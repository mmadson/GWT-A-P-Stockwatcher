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
package com.moesol.example.gwt.stockwatcherrestmvp.client.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockDataOverlay
        extends JavaScriptObject
{

    protected StockDataOverlay()
    {
    }

    public final native String getSymbol() /*-{
        return this.symbol;
    }-*/;

    public final native double getPrice() /*-{
        return this.price;
    }-*/;

    public final native double getChange() /*-{
        return this.change;
    }-*/;

    public final double getChangePercent()
    {
        return 100.0 * getChange() / getPrice();
    }
}
