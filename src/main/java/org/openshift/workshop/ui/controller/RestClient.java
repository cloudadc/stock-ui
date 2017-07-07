package org.openshift.workshop.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.openshift.workshop.ui.model.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
public class RestClient {
    
    private static String BASE = buildBaseURL();
    
    private static String API_PRODUCT = BASE + "/rest/query/account/products";
    private static String API_MARKETDATA = BASE + "/rest/query/marketData/stockPrices";
    private static String API_STOCKS= BASE + "/rest/query/stocks";
    
    
    public static List<Product> loadProduct(final List<Product> items) {
        
        try {
            URL url = new URL(API_PRODUCT);
            InputStream in = url.openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(in, List.class);
            list.forEach(m -> {
                Integer id = (Integer) m.get("id");
                String symbol = (String) m.get("symbol");
                String company_name = (String) m.get("company_name") ;
                items.add(new Product(id, symbol, company_name));
            });
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return items;
    }
    
    private static String buildBaseURL() {
        
        return System.getProperty("REST_PROTOCOL", "http") + "://" + System.getProperty("REST_HOST", "spring-boot-cxf-jaxrs") + ":" +  System.getProperty("REST_PORT", "8080");
        
//        return "http://spring-boot-cxf-jaxrs-sample.apps.na1.openshift.opentlc.com";
    }

    public static List<StockPrice> loadMarketData(final List<StockPrice> items) {
        try {
            URL url = new URL(API_MARKETDATA);
            InputStream in = url.openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(in, List.class);
            list.forEach(m -> {
                String symbol = (String) m.get("symbol");
                Double price = (Double) m.get("price") ;
                items.add(new StockPrice(symbol, price));
            });
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<Stock> loadStocks(final List<Stock> items) {
        try {
            URL url = new URL(API_STOCKS);
            InputStream in = url.openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(in, List.class);
            list.forEach(m -> {
                Integer id = (Integer) m.get("product_id");
                String symbol = (String) m.get("symbol");
                Double price = (Double) m.get("price") ;
                String company_name = (String) m.get("company_name") ;
                items.add(new Stock(id, symbol, price, company_name));
            });
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        
       System.out.println(RestClient.loadProduct(new ArrayList<Product>()));
       System.out.println(RestClient.loadMarketData(new ArrayList<StockPrice>()));
       System.out.println(RestClient.loadStocks(new ArrayList<Stock>()));
    }
}
