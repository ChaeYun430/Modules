package com.fhk.search.controller;

import com.fhk.search.document.ProductDoc;
import com.fhk.search.dto.ProductReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @GetMapping("/products")
    public ResponseEntity<ProductDoc> getProducts(@ModelAttribute ProductReq req) {



        return ResponseEntity.ok(new ProductDoc());
    }

}
