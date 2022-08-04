package com.example.demo.persistence.model;

import lombok.Data;

import java.util.List;

@Data
public class AzureServicePricePage {
    private String BillingCurrency;
    private String CustomerEntityId;
    private String CustomerEntityType;
    private List<AzureServicePrice> Items;
    private String NextPageLink;
    private int Count;
}
