package rocks.j5.uga.expanse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.model.PriceDetail;
import rocks.j5.uga.expanse.service.PricingService;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping(value = "/api/price", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://j5.rocks", maxAge = 3600)
public class PricingController {

    final private PricingService pricingService;

    public PricingController(PricingService pricingService) {

        this.pricingService = pricingService;
    } // PricingController

    /**
     * Get the price for book
     *
     * @return book price
     */
    @GetMapping(value = "/{bookId}")
    public PriceDetail getBookPriceFromId(@PathVariable Integer bookId,
                                          HttpSession httpSession) {

        return pricingService.getBookPrice(bookId);
    }


}
