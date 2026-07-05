package com.currency.repository;

import com.currency.entity.ChangeCurrency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChangeCurrencyRepository extends MongoRepository<ChangeCurrency,String> {

}
