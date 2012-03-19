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
package com.moesol.example.gwt.stockwatcherrestmvp.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.moesol.example.gwt.stockwatcherrestmvp.client.ui.StockWatcherView;
import com.moesol.example.gwt.stockwatcherrestmvp.client.ui.StockWatcherViewImpl;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class ClientFactoryImpl implements ClientFactory
{
    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);
    private static final StockWatcherView stockWatcherView = new StockWatcherViewImpl();

    public EventBus getEventBus()
    {
        return eventBus;
    }

    public PlaceController getPlaceController()
    {
        return placeController;
    }

    public StockWatcherView getStockWatcherView()
    {
        return stockWatcherView;
    }
}
