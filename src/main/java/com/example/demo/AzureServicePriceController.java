package com.example.demo;

import com.example.demo.persistence.repo.AzureServicePriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/prices")
public class AzureServicePriceController {
    Logger logger = LoggerFactory.getLogger(AzureServicePriceController.class);

    @Autowired
    private AzureServicePriceDownloader priceDownloader;

    @Autowired
    private AzureServicePriceRepository repository;

    //That will take about 20 minutes
    @Scheduled(cron = "0 0 1 * * MON")
    @GetMapping("/refresh")
    public void refresh() throws IOException {
        repository.saveAll(priceDownloader.download());
    }

    @GetMapping("/hourly")
    public Double getHourly(@RequestParam String id) {
        return repository.findByskuId(id).getRetailPrice();
    }

    @GetMapping("/daily")
    public Double getDaily(@RequestParam String id) {
        return repository.findByskuId(id).getRetailPrice() * 24;
    }

    @GetMapping("/monthly")
    public Double getMonthly(@RequestParam String id) {
        return repository.findByskuId(id).getRetailPrice() * 24 * 30;
    }
}
