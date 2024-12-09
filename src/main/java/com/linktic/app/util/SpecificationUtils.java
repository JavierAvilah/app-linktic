package com.linktic.app.util;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> distinct(Specification<T> spec) {
        if(spec==null)return null;
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}