package com.bc48.bootcointypechange.repository;

import com.bc48.bootcointypechange.entity.TypeChange;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TypeChangeRepository extends ReactiveCrudRepository<TypeChange, String> {

    Mono<TypeChange> findByCurrencyOrigin(String currencyOrigin);
}
