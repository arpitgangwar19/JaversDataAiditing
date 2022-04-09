package io.javerse.controller;

import java.util.List;

import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.javerse.domain.Address;
import io.javerse.domain.Product;
import io.javerse.domain.Store;
import io.javerse.repo.StoreRepository;
import io.javerse.service.StoreService;

@RestController
public class StoreController {
    private final StoreService storeService;
    private final Javers javers;
    
    @Autowired
    StoreRepository storeRepository;


    public StoreController(StoreService customerService, Javers javers) {
        this.storeService = customerService;
        this.javers = javers;
    }
    
    @GetMapping("/")
    public String check() {
//        storeService.createRandomProduct(storeId);
    	   Store store = new Store("Baeldung store", new Address("Some street", 22222));
           for (int i = 1; i < 3; i++) {
               Product product = new Product("Product #" + i, 100 * i);
               store.addProduct(product);
           }
           storeRepository.save(store);
    	return "working";
    }

    @PostMapping("/stores/{storeId}/products/random")
    public void createRandomProduct(@PathVariable final Integer storeId) {
        storeService.createRandomProduct(storeId);
    }

    @PostMapping("/stores/{storeId}/rebrand")
    public void rebrandStore(@PathVariable final Integer storeId, @RequestBody RebrandStoreDto rebrandStoreDto) {
        storeService.rebrandStore(storeId, rebrandStoreDto.name);
    }

    @GetMapping(value = "/stores/{storeId}/products/{productId}/price/{price}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProductPrice(@PathVariable final Integer productId, @PathVariable String storeId,@PathVariable Double  price) {
        storeService.updateProductPrice(productId, price);
    }

    @GetMapping("/products/{productId}/changes")
    public String getProductChanges(@PathVariable int productId) {
        Product product = storeService.findProductById(productId);
        QueryBuilder jqlQuery = QueryBuilder.byInstance(product);
        Changes changes = javers.findChanges(jqlQuery.build());
        javers.commit("arpit", product);
        return javers.getJsonConverter().toJson(changes);
    }

    @GetMapping("/products/snapshots")
    public String getProductSnapshots() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Product.class);
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
        return javers.getJsonConverter().toJson(snapshots);
    }

    @GetMapping(" ")
    public String getStoreShadows(@PathVariable int storeId) {
        Store store = storeService.findStoreById(storeId);
        JqlQuery jqlQuery = QueryBuilder.byInstance(store)
                .withChildValueObjects().build();
        List<Shadow<Store>> shadows = javers.findShadows(jqlQuery);
        return javers.getJsonConverter().toJson(shadows.get(0));
    }

    @GetMapping("/stores/snapshots")
    public String getStoresSnapshots() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(Store.class);
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
        return javers.getJsonConverter().toJson(snapshots);
    }

}