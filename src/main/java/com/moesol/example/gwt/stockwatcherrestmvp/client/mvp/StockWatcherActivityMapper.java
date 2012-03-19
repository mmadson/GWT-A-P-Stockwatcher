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
package com.moesol.example.gwt.stockwatcherrestmvp.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.moesol.example.gwt.stockwatcherrestmvp.client.ClientFactory;
import com.moesol.example.gwt.stockwatcherrestmvp.client.activity.StockWatcherActivity;
import com.moesol.example.gwt.stockwatcherrestmvp.client.place.StockWatcherPlace;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockWatcherActivityMapper implements ActivityMapper
{
    private final ClientFactory clientFactory;

    public StockWatcherActivityMapper(final ClientFactory clientFactory)
    {
        this.clientFactory = clientFactory;
    }

    public Activity getActivity(final Place place)
    {
        if(place instanceof StockWatcherPlace)
            return new StockWatcherActivity(clientFactory, (StockWatcherPlace) place);

        return null;
    }
}
