package com.example.demo;

import com.example.demo.persistence.model.AzureServicePrice;
import com.example.demo.persistence.model.AzureServicePricePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class AzureServicePriceDownloader {
    Logger logger = LoggerFactory.getLogger(AzureServicePriceDownloader.class);

    //That will take about 20 minutes
    public List<AzureServicePrice> download() throws IOException {
        logger.info("Downloading prices...");
        List<AzureServicePrice> prices = new ArrayList<>();
        String url = "https://prices.azure.com/api/retail/prices";
        while (url != null && !url.equals("")) {
            String json = Utils.getUrl(url);
            AzureServicePricePage page = Utils.gson.fromJson(json, AzureServicePricePage.class);
            prices.addAll(page.getItems());
            url = page.getNextPageLink();
            logger.info("Page processed");
        }
        logger.info("All pages processed");
        return filter(prices);
    }

    private List<AzureServicePrice> filter(List<AzureServicePrice> prices) {
        return onlyLowestPrice(onlyHourly(prices));
    }

    private List<AzureServicePrice> onlyHourly(List<AzureServicePrice> prices) {
        for(int i = 0; i < prices.size(); i++)
            if(!prices.get(i).getUnitOfMeasure().equals("1 Hour"))
                prices.remove(i--);
        return prices;
    }

    private List<AzureServicePrice> onlyLowestPrice(List<AzureServicePrice> prices) {
        Map<String, Double> skuidToLowestPrice = new HashMap<>();
        prices.forEach(p -> {
            if(skuidToLowestPrice.containsKey(p.getSkuId())) {
                if (skuidToLowestPrice.get(p.getSkuId()) > p.getRetailPrice())
                    skuidToLowestPrice.put(p.getSkuId(), p.getRetailPrice());
            } else skuidToLowestPrice.put(p.getSkuId(), p.getRetailPrice());
        });
        for(int i = 0; i < prices.size(); i++)
            if(prices.get(i).getRetailPrice() > skuidToLowestPrice.get(prices.get(i).getSkuId()))
                prices.remove(i--);
        for(int i = 0; i < prices.size(); i++) {
            if (!skuidToLowestPrice.containsKey(prices.get(i).getSkuId()))
                prices.remove(i--);
            else skuidToLowestPrice.remove(prices.get(i).getSkuId());
        }
        return prices;
    }
}
