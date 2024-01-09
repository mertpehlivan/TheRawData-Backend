package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;
import java.util.UUID;

import com.mertdev.therawdata.bussines.concretes.BasketResponse;

public interface BasketService {
	public List<BasketResponse> getAllBasket();
	public BasketResponse getRawDataForPublicationFromBasket(UUID id);
	public String addBasket(Long id);
	public String deleteBasket(Long id);
	public Boolean existsByRawData(Long id);
	public Integer getPrice();
}
