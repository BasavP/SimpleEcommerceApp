package com.basav.productservice.productservice.controller;


import com.basav.productservice.productservice.dto.ProductRequestDTO;
import com.basav.productservice.productservice.dto.ProductResponseDTO;
import com.basav.productservice.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Log
@RestController
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDTO productRequestDTO){
      log.info("POST REQUEST : /api/product");
            productService.createProduct(productRequestDTO);

    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts(){

        return productService.getAllProducts();
    }

}

