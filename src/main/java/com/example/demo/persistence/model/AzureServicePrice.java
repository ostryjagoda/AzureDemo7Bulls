package com.example.demo.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AzureServicePrice {
    @Column(nullable = false)
    private String currencyCode;
    @Column(nullable = false)
    private double tierMinimumUnits;
    @Column(nullable = false)
    private double retailPrice;
    @Column(nullable = false)
    private double unitPrice;
    @Column(nullable = false)
    private String armRegionName;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String effectiveStartDate;
    @Column(nullable = false)
    private String meterId;
    @Column(nullable = false)
    private String meterName;
    @Column(nullable = false)
    private String productId;
    @Id
    @Column(nullable = false, unique = true)
    private String skuId; //That is not unique, why?
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String skuName;
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private String serviceId;
    @Column(nullable = false)
    private String serviceFamily;
    @Column(nullable = false)
    private String unitOfMeasure;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private boolean isPrimaryMeterRegion;
    @Column(nullable = false)
    private String armSkuName;
}
