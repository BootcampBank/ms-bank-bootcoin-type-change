package com.bc48.bootcointypechange.service;

import com.bc48.bootcointypechange.entity.TypeChange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeChangeService {

    public Flux<TypeChange> getAll();

    public Mono<TypeChange> getById(String typechangeId);

    public Mono<TypeChange> save(TypeChange typeChange);

    public Mono<TypeChange> update(String typechangeId, TypeChange typeChange);

    public Mono<TypeChange> delete(String id);

    Mono<TypeChange> getByCurrencyOrigin(String currencyOrigin);

}
