package com.example.demo.persistence.repo;

import com.example.demo.persistence.model.AzureServicePrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AzureServicePriceRepository extends CrudRepository<AzureServicePrice, Long> {
    @Query("SELECT DISTINCT a.unitOfMeasure FROM AzureServicePrice a")
    List<String> findDistinctUnitOfMeasure();//For debug, to check if all entities has it set to "1 hour"

    AzureServicePrice findByskuId(String skuId);
}
