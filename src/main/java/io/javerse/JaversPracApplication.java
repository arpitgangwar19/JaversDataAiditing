package io.javerse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import io.javerse.domain.Address;
import io.javerse.domain.Product;
import io.javerse.domain.Store;
import io.javerse.repo.StoreRepository;

@SpringBootApplication
public class JaversPracApplication {
	
	
	 @Autowired
	   StoreRepository storeRepository;


	public static void main(String[] args) {
		SpringApplication.run(JaversPracApplication.class, args);
	}
	
	
//	@EventListener
//    public void appReady(ApplicationReadyEvent event) {
//        Store store = new Store("Baeldung store", new Address("Some street", 22222));
//        for (int i = 1; i < 3; i++) {
//            Product product = new Product("Product #" + i, 100 * i);
//            store.addProduct(product);
//        }
//        storeRepository.save(store);
//    }

}
