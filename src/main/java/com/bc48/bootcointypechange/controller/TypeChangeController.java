package com.bc48.bootcointypechange.controller;

import com.bc48.bootcointypechange.entity.TypeChange;
import com.bc48.bootcointypechange.service.TypeChangeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("typeChange")
@Tag(name = "Type change", description = "Manage maintenance of typeChange")
@CrossOrigin(value = {"*"})
@AllArgsConstructor
public class TypeChangeController {

    public final TypeChangeService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<TypeChange>>> getAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.getAll())
        );
    }

    @GetMapping("/{idTypeChange}")
    public Mono<ResponseEntity<Mono<TypeChange>>> getByIdTypeChange(@PathVariable String idTypeChange) {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.getById(idTypeChange))
        );
    }

    @PostMapping
    public Mono<ResponseEntity<TypeChange>> create(@RequestBody TypeChange typeChange) {

        return service.save(typeChange).map(p -> ResponseEntity
                .created(URI.create("/TypeChange/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p)
        );
    }

    @PutMapping
    public Mono<ResponseEntity<TypeChange>> update(@PathVariable String personId, @RequestBody TypeChange typeChange) {
        return service.update(personId, typeChange)
                .map(p -> ResponseEntity.created(URI.create("/TypeChange/"
                                .concat(p.getId())
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<TypeChange>> delete(@RequestBody String id) {
        return service.delete(id)
                .map(p -> ResponseEntity.created(URI.create("/TypeChange/"
                                .concat(p.getId())
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("currencyOrigin/{currencyOrigin}")
    public Mono<ResponseEntity<Mono<TypeChange>>> getByCurrencyOrigin(@PathVariable String currencyOrigin) {
        return Mono.create(monoSink -> monoSink.success(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getByCurrencyOrigin(currencyOrigin))));

    }
}
