package com.bc48.bootcointypechange.service;


import com.bc48.bootcointypechange.entity.TypeChange;
import com.bc48.bootcointypechange.repository.TypeChangeRepository;
import com.bc48.bootcointypechange.util.Constant;
import com.bc48.bootcointypechange.util.handler.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TypeChangeServiceImpl implements TypeChangeService {

    @Autowired
    public  TypeChangeRepository repository;

    public Flux<TypeChange> getAll() {
        return repository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "typeChange", key = "#typeChangeId")
    public Mono<TypeChange> getById(String typeChangeId) {
        return repository.findById(typeChangeId);
    }

    @Override
    public Mono<TypeChange> save(TypeChange typeChange) {
        return repository.findById(typeChange.getId())
                .map(sa -> {
                    throw new BadRequestException(
                            "ID",
                            "Type change exist must be upgrade",
                            sa.getId(),
                            TypeChangeServiceImpl.class,
                            "save.onErrorResume"
                    );
                })
                .switchIfEmpty(Mono.defer(() -> {
                            typeChange.setId(null);
                            typeChange.setInsertionDate(new Date());
                            typeChange.setRegistrationStatus((short) 1);
                            return repository.save(typeChange);
                        }
                ))
                .onErrorResume(e -> Mono.error(e)).cast(TypeChange.class);
    }

    @Override
    @CachePut(cacheNames = "typeChange", key = "#typeChangeId")
    public Mono<TypeChange> update(String typeChangeId, TypeChange typeChange) {
        return repository.findById(typeChangeId)
                .switchIfEmpty(Mono.error(new Exception("An item with the id " + typeChange.getId() + " was not found. >> switchIfEmpty")))
                .flatMap(p -> repository.save(typeChange))
                .onErrorResume(e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to update an item.",
                        e.getMessage(),
                        TypeChangeServiceImpl.class,
                        "update.onErrorResume"
                )));
    }

    @Override
    @CacheEvict(cacheNames = "person", key = "#personId")
    public Mono<TypeChange> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("An item with the id " + id + " was not found. >> switchIfEmpty")))
                .flatMap(p -> {
                    p.setRegistrationStatus(Constant.STATUS_INACTIVE);
                    return repository.save(p);
                })
                .onErrorResume(e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to delete an item.",
                        e.getMessage(),
                        TypeChangeServiceImpl.class,
                        "update.onErrorResume"
                )));
    }

    @Override
    public Mono<TypeChange> getByCurrencyOrigin(String currencyOrigin) {
        return repository.findByCurrencyOrigin(currencyOrigin);
    }

}
