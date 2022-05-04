package rocks.j5.uga.expanse.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rocks.j5.uga.expanse.domain.EncounteredError;
import rocks.j5.uga.expanse.domain.HttpResponseWrapper;
import rocks.j5.uga.expanse.model.Book;
import rocks.j5.uga.expanse.model.BookOriginal;
import rocks.j5.uga.expanse.model.Stock;
import rocks.j5.uga.expanse.model.UserO;
import rocks.j5.uga.expanse.service.CatalogService;
import rocks.j5.uga.expanse.service.InventoryService;
import rocks.j5.uga.expanse.service.UserServiceO;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class InventoryController
{
    private final InventoryService inventoryService;

    /**
     * Gets all books.
     *
     * @return all books
     */
    @GetMapping(value = "/all")
    public List<Stock> getAll(HttpSession session)
    {
        return inventoryService.findAll();
    }
}
