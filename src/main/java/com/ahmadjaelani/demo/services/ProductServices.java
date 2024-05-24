package com.ahmadjaelani.demo.services;

import com.ahmadjaelani.demo.entity.Product;
import com.ahmadjaelani.demo.entity.User;
import com.ahmadjaelani.demo.model.*;
import com.ahmadjaelani.demo.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse create(CreateProductRequest request){

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setType(ProductTypeEnum.valueOf(request.getType()));
        product.setPrice(request.getPrice());

        productRepository.save(product);

        return toProductResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse get(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return toProductResponse(product);
    }

    @Transactional
    public ProductResponse update(UpdateProductRequest request){
        Product product = productRepository.findById(request.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setName(request.getName());
        product.setType(ProductTypeEnum.valueOf(request.getType()));
        product.setPrice(request.getPrice());
        productRepository.save(product);

        return toProductResponse(product);
    }

    @Transactional
    public void delete(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        productRepository.delete(product);
    }



    @Transactional(readOnly = true)
    public Page<ProductResponse> search(SearchProductRequest request){
        Specification<Product> specification = (root,query,builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getName())) {
                predicates.add(
                        builder.like(root.get("name"), "%" + request.getName() + "%")
                );
            }
            if (Objects.nonNull(request.getType())) {
                predicates.add(builder.like(root.get("type"), "%" + request.getType() + "%"));
            }
            if (Objects.nonNull(request.getLowPrice())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), request.getLowPrice()));
                //predicates.add(builder.greaterThanOrEqualTo(root.get("price"), "%" + request.getLowPrice() + "%"));

            }
            if (Objects.nonNull(request.getHighPrice())) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), request.getHighPrice()));
                //predicates.add(builder.greaterThanOrEqualTo(root.get("price"), "%" + request.getHighPrice() + "%"));

            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Product> products = productRepository.findAll(specification, pageable);
        List<ProductResponse> productResponses = products.getContent().stream()
                .map(this::toProductResponse)
                .toList();

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .type(product.getType())
                .price(product.getPrice())
                .build();
    }
}
