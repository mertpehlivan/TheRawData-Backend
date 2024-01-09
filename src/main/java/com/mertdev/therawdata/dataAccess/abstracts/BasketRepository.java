package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mertdev.therawdata.entities.concretes.Basket;

import jakarta.transaction.Transactional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
	 @Transactional
	    default void removeRawDataFromBasket(Long basketId, Long rawDataId) {
	        Optional<Basket> basketOptional = findById(basketId);

	        if (basketOptional.isPresent()) {
	            Basket basket = basketOptional.get();

	            // Find the RawData entity with the given ID in the rawDatas set
	            basket.getRawDatas().removeIf(rawData -> rawData.getId().equals(rawDataId));

	            // Save the updated basket
	            save(basket);
	        }
	    }

}
