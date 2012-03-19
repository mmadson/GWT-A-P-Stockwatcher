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

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.EventBus;
import com.moesol.example.gwt.stockwatcherrestmvp.client.mvp.AppPlaceHistoryMapper;
import com.moesol.example.gwt.stockwatcherrestmvp.client.mvp.StockWatcherActivityMapper;
import com.moesol.example.gwt.stockwatcherrestmvp.client.place.StockWatcherPlace;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public final class StockWatcherRestMvp
        implements EntryPoint
{

    private Place defaultPlace = new StockWatcherPlace("");
    SimplePanel appContainerPanel = new SimplePanel();
    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();

        ActivityMapper activityMapper = new StockWatcherActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper,eventBus);
        activityManager.setDisplay(appContainerPanel);

        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        PlaceController placeController = clientFactory.getPlaceController();
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController,eventBus, defaultPlace);

        RootPanel.get("stockList").add(appContainerPanel);
        historyHandler.handleCurrentHistory();
    }
}
