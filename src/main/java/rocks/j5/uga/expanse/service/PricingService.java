package rocks.j5.uga.expanse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.domain.BookPrice;
import rocks.j5.uga.expanse.model.PriceDetail;
import rocks.j5.uga.expanse.repository.PriceRepository;

@Service
public class PricingService {

    PriceRepository priceRepository;

    @Autowired
    public PricingService(PriceRepository priceRepository) {

        this.priceRepository = priceRepository;
    }

    public PriceDetail getBookPrice(Integer bookId) {
        return (PriceDetail) priceRepository.findFirstByBook_Id(bookId).get();
    }
}
