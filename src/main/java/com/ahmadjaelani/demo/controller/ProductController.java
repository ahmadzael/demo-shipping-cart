package com.ahmadjaelani.demo.controller;

import com.ahmadjaelani.demo.entity.Product;
import com.ahmadjaelani.demo.entity.User;
import com.ahmadjaelani.demo.model.*;
import com.ahmadjaelani.demo.repository.ProductRepository;
import com.ahmadjaelani.demo.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    public ProductServices productService;

    @PostMapping(
            path = "/api/products",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> create(@RequestBody CreateProductRequest request) {
        ProductResponse productResponse = productService.create(request);

        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }


    @GetMapping(
            path = "/api/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> get(User user,
                                            @PathVariable("productId") String productId) {
        ProductResponse productResponse = productService.get(productId);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

    @PutMapping(
            path = "/api/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> update(
            @RequestBody UpdateProductRequest request,
            @PathVariable("productId") String productId) {
        request.setId(productId);

        ProductResponse productResponse = productService.update(request);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();
    }

    @DeleteMapping(
            path = "/api/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> remove(
                                      @PathVariable("productId") String productId) {
        productService.delete(productId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ProductResponse>> search(
                                                     @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "email", required = false) String email,
                                                     @RequestParam(value = "phone", required = false) String phone,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                     @RequestParam(value = "lowprice", required = false, defaultValue = "10") BigDecimal lowPrice,
                                                     @RequestParam(value = "highprice", required = false, defaultValue = "10") BigDecimal highprice) {


        {
            SearchProductRequest request = SearchProductRequest.builder()
                    .page(page)
                    .size(size)
                    .name(name)
                    .type(email)
                    .lowPrice(lowPrice)
                    .highPrice(highprice)
                    .build();

            Page<ProductResponse> productResponses = productService.search(request);
            return WebResponse.<List<ProductResponse>>builder()
                    .data(productResponses.getContent())
                    .paging(PagingResponse.builder()
                            .currentPage(productResponses.getNumber())
                            .totalPage(productResponses.getTotalPages())
                            .size(productResponses.getSize())
                            .build())
                    .build();
        }
    }

}
