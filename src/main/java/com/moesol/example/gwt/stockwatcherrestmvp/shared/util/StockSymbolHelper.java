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
package com.moesol.example.gwt.stockwatcherrestmvp.shared.util;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public final class StockSymbolHelper
{
    private StockSymbolHelper()
    {
    }

    public static boolean isValidSymbol(final String stockSymbol)
    {
        return !isInvalidSymbol(toCanonicalFormat(stockSymbol));
    }

    public static String toCanonicalFormat(final String stockSymbol)
    {
        return stockSymbol.trim().toUpperCase();
    }

    public static boolean isInvalidSymbol(final String stockSymbol)
    {
        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        return !toCanonicalFormat(stockSymbol).toUpperCase().trim().matches("^[0-9A-Z\\.]{1,10}$");
    }
}
