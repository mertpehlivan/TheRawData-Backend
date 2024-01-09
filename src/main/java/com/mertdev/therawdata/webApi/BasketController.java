package com.mertdev.therawdata.webApi;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.BasketService;
import com.mertdev.therawdata.bussines.concretes.BasketResponse;
import com.mertdev.therawdata.bussines.requests.AddBasketRequest;
import com.mertdev.therawdata.bussines.requests.DeleteBasketRequest;
import com.mertdev.therawdata.exceptions.BasketException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/basket")
@AllArgsConstructor
public class BasketController {
	private final BasketService basketService;

	@GetMapping("/all")
	public ResponseEntity<?> getAllBasket() {
		try {
			List<BasketResponse> basketList = basketService.getAllBasket();

			if (basketList.isEmpty()) {
				throw new BasketException("No raw data found in the basket");
			}

			return ResponseEntity.ok(basketList);
		} catch (BasketException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
		}
	}
	@GetMapping("/getRawDataForPublication/{id}")
	public ResponseEntity<?> getRawDataForPublicationFromBasket(@PathVariable UUID id) {
		try {
			BasketResponse basket = basketService.getRawDataForPublicationFromBasket(id);

			if (basket == null) {
				throw new BasketException("No raw data for publication from basket");
			}

			return ResponseEntity.ok(basket);
		} catch (BasketException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred.");
		}
	}
	@PostMapping("/add")
	public void add(@RequestBody AddBasketRequest request) {
		basketService.addBasket(request.getId());
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteRawDataFromBasket(@RequestParam("rawDataId") Long rawDataId) {
	    try {
	        basketService.deleteBasket(rawDataId);
	        System.out.println("Silme İşlemi Başarılı");
	        return new ResponseEntity<>("Raw data removed from the basket successfully.", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Error deleting raw data from the basket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkIfRawDataExists(@PathVariable Long id) {
        Boolean exists = basketService.existsByRawData(id);
        System.out.println(exists);
        return ResponseEntity.ok(exists);
    }
    @GetMapping("/getPrice")
    public Integer getBasketPrice() {
        return basketService.getPrice();
    }
	
	
	
}
