package com.epam.esm.jpa.criteria;
import javax.persistence.TypedQuery;

public class PaginationBuilder {

    public static void addPagination(Integer pageNumber, Integer pageSize, TypedQuery query) {
        if (pageNumber != null && pageSize != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
    }
}
