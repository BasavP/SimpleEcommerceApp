package com.basav.productservice.productservice.service;

import com.basav.productservice.productservice.dto.ProductRequestDTO;
import com.basav.productservice.productservice.dto.ProductResponseDTO;
import com.basav.productservice.productservice.model.Product;
import com.basav.productservice.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Log
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDTO productRequestDTO){
        //map the dto to model (entity)

        Product product = Product.builder()
                .name(productRequestDTO.getName()).
                price(productRequestDTO.getPrice()).
                description(productRequestDTO.getDescription()).
                build();


        //save the model in the db
        productRepository.save(product);
        log.info(" product saved to db :"+product);
    }


    public List<ProductResponseDTO> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductResponseDTO> allProductsDTO = allProducts.stream().map(
                product -> ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build()
        ).collect(Collectors.toList());


        return allProductsDTO;
    }
}
