package org.openshift.workshop.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.openshift.workshop.ui.model.Product;
import org.openshift.workshop.ui.model.Stock;
import org.openshift.workshop.ui.model.StockPrice;

@RequestScoped
public class RestController {

    private List<Product> products = new ArrayList<>();
    private List<StockPrice> stockPrices = new ArrayList<>();
    private List<Stock> stocks = new ArrayList<>();

    @Produces
    @Named
    public List<Product> getProducts() {
        return products;
    }

    @Produces
    @Named
    public List<StockPrice> getStockPrices() {
        return stockPrices;
    }

    @Produces
    @Named
    public List<Stock> getStocks() {
        return stocks;
    }
    
    @PostConstruct
    public void invokeRest() {
        RestClient.loadProduct(products);
        RestClient.loadMarketData(stockPrices);
        RestClient.loadStocks(stocks);
    }
}
