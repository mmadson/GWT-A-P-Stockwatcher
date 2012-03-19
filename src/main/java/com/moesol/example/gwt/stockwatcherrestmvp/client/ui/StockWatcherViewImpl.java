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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.moesol.example.gwt.stockwatcherrestmvp.client.place.StockWatcherPlace;
import com.moesol.example.gwt.stockwatcherrestmvp.shared.util.StockSymbolHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://www.moesol.com/">Moebius Solutions, Inc.</a>
 * @author <a href="http://www.matthewmadson.com/">Matthew Madson</a>
 */
public class StockWatcherViewImpl
        extends Composite
        implements StockWatcherView
{
    interface StockWatcherViewImplUiBinder
            extends UiBinder<VerticalPanel, StockWatcherViewImpl>
    {
    }

    interface StockWatcherViewImplStyle
            extends CssResource
    {
        String watchListHeader();

        String watchList();

        String watchListNumericColumn();

        String watchListRemoveColumn();

        String addPanel();

        String removeButton();

        String positiveChange();

        String negativeChange();

        String noChange();

        String errorMessage();
    }

    private static final NumberFormat priceFormatter = NumberFormat.getFormat(
            "+#,##0.00");
    private static final NumberFormat changeFormatter = NumberFormat.getFormat(
            "+#,##0.00;-#,##0.00");
    private static final StockWatcherViewImplUiBinder binder = GWT.create(
            StockWatcherViewImplUiBinder.class);

    private List<String> stocks;
    private Presenter presenter;
    @UiField
    Label lastUpdatedLabel;
    @UiField
    HorizontalPanel addPanel;
    @UiField
    FlexTable stocksFlexTable;
    @UiField
    TextBox newSymbolTextBox;
    @UiField
    Button addStockButton;
    @UiField
    StockWatcherViewImplStyle style;
    @UiField
    Label errorMsgLabel;

    public StockWatcherViewImpl()
    {
        this.stocks = new ArrayList<String>();
        initWidget(binder.createAndBindUi(this));
        initUiFields();
    }

    private void initUiFields()
    {
        stocksFlexTable.setText(0, 0, "Symbol");
        stocksFlexTable.setText(0, 1, "Price");
        stocksFlexTable.setText(0, 2, "Change");
        stocksFlexTable.setText(0, 3, "Remove");
        stocksFlexTable.getRowFormatter()
                .addStyleName(0, style.watchListHeader());
        styleStocksFlexTableCellsForRow(0);
        newSymbolTextBox.setFocus(true);
        errorMsgLabel.setVisible(false);
    }

    private void styleStocksFlexTableCellsForRow(final int row)
    {
        stocksFlexTable.getCellFormatter().addStyleName(row,
                                                        1,
                                                        style.watchListNumericColumn());
        stocksFlexTable.getCellFormatter()
                .addStyleName(row, 2, style.watchListNumericColumn());
        stocksFlexTable.getCellFormatter()
                .addStyleName(row, 3, style.watchListRemoveColumn());
    }

    public void updateStockPrice(final String stockSymbol, final double stockPrice, final double priceDifference, final double percentDifference)
    {
        if(!stocks.contains(stockSymbol))
        {
            stocks.add(stockSymbol);
        }

        final int row = stocks.indexOf(stockSymbol) + 1;

        stocksFlexTable.setText(row, 0, stockSymbol);
        stocksFlexTable.setText(row,
                                1,
                                priceFormatter.format(stockPrice));
        Label changeLabel = new Label(changeFormatter.format(
                priceDifference) + " (" + changeFormatter
                .format(percentDifference) + "%)");

        String changeStyleName = style.noChange();
        if(percentDifference < -0.1f)
        {
            changeStyleName = style.negativeChange();
        }
        else if(percentDifference > 0.1f)
        {
            changeStyleName = style.positiveChange();
        }
        changeLabel.setStyleName(changeStyleName);
        stocksFlexTable.setWidget(row, 2, changeLabel);

        Button removeStockButton = new Button("x");
        removeStockButton.addClickHandler(new ClickHandler()
        {
            public void onClick(final ClickEvent event)
            {
                int removedIndex = stocks.indexOf(stockSymbol);
                stocks.remove(stockSymbol);
                stocksFlexTable.removeRow(removedIndex + 1);
                updatePlaceOnRemoveStockSymbol();
                resetNewSymbolTextBox();
            }
        });
        removeStockButton.setStyleName(style.removeButton());

        stocksFlexTable.setWidget(row, 3, removeStockButton);
        styleStocksFlexTableCellsForRow(row);
        lastUpdatedLabel.setText("Last Update : " + DateTimeFormat.getFormat(
                DateTimeFormat.PredefinedFormat.TIME_MEDIUM)
                .format(new Date()));
    }

    @Override
    public void displayError(final String message)
    {
        this.errorMsgLabel.setText(message);
        this.errorMsgLabel.setVisible(true);
    }

    @Override
    public void hideError()
    {
        this.errorMsgLabel.setVisible(false);
    }

    private void updatePlaceOnRemoveStockSymbol()
    {
        Set<String> symbols = new HashSet<String>(stocks);
        this.presenter.goTo(new StockWatcherPlace(symbols));
        resetNewSymbolTextBox();
    }

    private void resetNewSymbolTextBox()
    {
        this.newSymbolTextBox.setText("");
        this.newSymbolTextBox.setFocus(true);
    }

    public void setPresenter(final Presenter presenter)
    {
        this.presenter = presenter;
    }

    @UiHandler("addStockButton")
    void onClickAddButton(ClickEvent e)
    {
        updatePlaceOnNewStockSymbol();
    }

    private void updatePlaceOnNewStockSymbol()
    {
        final String stockSymbol = StockSymbolHelper.toCanonicalFormat(this.newSymbolTextBox
                                                                   .getText());
        if(isNewStockSymbol(stockSymbol))
        {
            Set<String> symbols = new HashSet<String>(stocks);
            symbols.add(stockSymbol);
            this.presenter.goTo(new StockWatcherPlace(symbols));
            resetNewSymbolTextBox();
        }
    }

    private boolean isNewStockSymbol(final String stockSymbol)
    {
        if(this.stocks.contains(stockSymbol))
        {
            return false;
        }
        if(StockSymbolHelper.isInvalidSymbol(stockSymbol))
        {
            Window.alert("'" + stockSymbol + "' is not a valid symbol.");
            newSymbolTextBox.selectAll();
            return false;
        }
        return true;
    }

    @UiHandler("newSymbolTextBox")
    void onPressEnterKey(KeyPressEvent e)
    {
        if(e.getCharCode() == KeyCodes.KEY_ENTER)
        {
            updatePlaceOnNewStockSymbol();
        }
    }
}
