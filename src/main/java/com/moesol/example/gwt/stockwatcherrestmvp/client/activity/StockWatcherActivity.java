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
package com.moesol.example.gwt.stockwatcherrestmvp.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.moesol.example.gwt.stockwatcherrestmvp.client.ClientFactory;
import com.moesol.example.gwt.stockwatcherrestmvp.client.model.StockDataOverlay;
import com.moesol.example.gwt.stockwatcherrestmvp.client.place.StockWatcherPlace;
import com.moesol.example.gwt.stockwatcherrestmvp.client.ui.StockWatcherView;
import java.util.logging.Logger;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockWatcherActivity
        extends AbstractActivity
        implements
        StockWatcherView.Presenter
{
    Logger logger = Logger.getLogger("StockWatcherActivity");
    private final ClientFactory clientFactory;
    private final StockWatcherView managedView;
    private final StockWatcherPlace managedPlace;
    private static final int REFRESH_INTERVAL = 5000; // ms
    private static String STOCK_PRICES_URI;
    private Timer refreshTimer = null;

    public StockWatcherActivity(final ClientFactory clientFactory, final StockWatcherPlace place)
    {
        this.clientFactory = clientFactory;
        this.managedView = this.clientFactory.getStockWatcherView();
        this.managedPlace = place;
        STOCK_PRICES_URI = URL.encode("stockWatcher/stockPrices?" + this
                .managedPlace
                .getToken());
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus)
    {
        this.managedView.setPresenter(this);

        refreshTimer = new Timer()
        {

            @Override
            public void run()
            {
                refreshStockData();
            }
        };

        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
        refreshStockData();
        panel.setWidget(this.managedView);
    }

    @Override
    public void onCancel()
    {
        super.onCancel();
        this.refreshTimer.cancel();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.refreshTimer.cancel();
    }

    private void refreshStockData()
    {
        this.managedView.hideError();
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
                                                    STOCK_PRICES_URI);
        try
        {
            Request request = builder.sendRequest(null, new RequestCallback()
            {
                @Override
                public void onResponseReceived(final Request request, final Response response)
                {
                    if(200 == response.getStatusCode())
                    {
                        JsArray<StockDataOverlay> result = asArrayOfStockData(
                                response.getText());
                        for(int i = 0; i < result.length(); i++)
                        {
                            final StockDataOverlay overlay = result.get(i);
                            managedView
                                    .updateStockPrice(overlay.getSymbol(),
                                                      overlay.getPrice(),
                                                      overlay.getChange(),
                                                      overlay.getChangePercent());
                        }
                    }
                    else
                    {
                        displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")");
                    }
                }

                @Override
                public void onError(final Request request, final Throwable throwable)
                {
                    displayError("Couldn't retrieve JSON");
                }
            });
        } catch(RequestException e)
        {
            displayError("Couldn't retrieve JSON");
        }
    }

    private void displayError(final String message)
    {
        managedView.displayError(message);
    }

    public void goTo(final Place p)
    {
        this.clientFactory.getPlaceController().goTo(p);
    }

    private final native JsArray<StockDataOverlay> asArrayOfStockData(String json) /*-{
        var result = eval('(' + json + ')');
        return result.stockData;
    }-*/;
}
