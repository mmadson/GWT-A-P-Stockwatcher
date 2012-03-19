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
package com.moesol.example.gwt.stockwatcherrestmvp.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public interface StockWatcherView
        extends IsWidget
{
    void updateStockPrice(String stockSymbol, double stockPrice, double priceDifference, double percentDifference);

    void displayError(String message);

    void hideError();

    void setPresenter(Presenter p);

    /**
     * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
     * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
     */
    public interface Presenter
    {
        void goTo(Place p);
    }
}
