package com.landconnect.specification;

import com.landconnect.dto.request.LandSearchRequest;
import com.landconnect.entity.Land;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
public class LandSpecification {
public static Specification<Land> searchLand(LandSearchRequest request) {
    return (root, query, criteriaBuilder) -> {

        List<Predicate> predicates = new ArrayList<>();

        // State Filter
        if (request.getState() != null && !request.getState().isBlank()) {
            predicates.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(root.get("state")),
                            request.getState().toLowerCase()
                    )
            );
        }

        // District Filter
        if (request.getDistrict() != null && !request.getDistrict().isBlank()) {
            predicates.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(root.get("district")),
                            request.getDistrict().toLowerCase()
                    )
            );
        }
        // Village Filter
        if (request.getVillage() != null && !request.getVillage().isBlank()) {
            predicates.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(root.get("village")),
                            request.getVillage().toLowerCase()
                    )
            );
        }

        // Land Type Filter
        if (request.getLandType() != null) {
            predicates.add(
                    criteriaBuilder.equal(
                            root.get("landType"),
                            request.getLandType()
                    )
            );
        }
        // Minimum Price
        if (request.getMinPrice() != 0) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            root.get("price"),
                            request.getMinPrice()
                    )
            );
        }

        // Maximum Price
        if (request.getMaxPrice() != 0) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            root.get("price"),
                            request.getMaxPrice()
                    )
            );
        }
        // Minimum Area
        if (request.getMinArea() != 0) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            root.get("area"),
                            request.getMinArea()
                    )
            );
        }

        // Maximum Area
        if (request.getMaxArea() != 0) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            root.get("area"),
                            request.getMaxArea()
                    )
            );
        }

        return criteriaBuilder.and(
                predicates.toArray(new Predicate[0])
        );
    };


}


}
