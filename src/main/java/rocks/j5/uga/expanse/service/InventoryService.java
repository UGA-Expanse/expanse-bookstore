package rocks.j5.uga.expanse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.Stock;
import rocks.j5.uga.expanse.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryService
{
    private final StockRepository stockRepository;

    public InventoryService(StockRepository stockRepository)
    {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAll()
    {
        Page<Stock> pallette = stockRepository.findAll(PageRequest.ofSize(100));
        List<Stock> scroll = pallette.get().collect(Collectors.toList());
        return scroll;
    }
}
