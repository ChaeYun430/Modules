package com.fhk.search.dto;

import com.fhk.core.constant.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Builder
public class ProductReq {

    private String keyword;

    private List<ProductCategory> category;

    private String sortField;
    private List<Sort.Direction> direction;

}
