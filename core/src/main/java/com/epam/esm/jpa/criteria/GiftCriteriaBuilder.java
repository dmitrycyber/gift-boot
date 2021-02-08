package com.epam.esm.jpa.criteria;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.util.SearchConstants;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import static com.epam.esm.util.SearchConstants.NAME_FIELD;

public class GiftCriteriaBuilder {
    public CriteriaQuery<GiftCertificateEntity> build(CriteriaBuilder criteriaBuilder, GiftSearchDto giftCertificateQueryParameter) {

        CriteriaQuery<GiftCertificateEntity> criteriaQuery = criteriaBuilder.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> giftCertificateRoot = criteriaQuery.from(GiftCertificateEntity.class);

        List<Predicate> predicateList = new ArrayList<>();

        String giftNamePrefix = giftCertificateQueryParameter.getNamePrefix();
        if (giftNamePrefix != null) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get(DaoConstants.FIELD_NAME),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + giftNamePrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
            predicateList.add(predicate);
        }

        String giftDescriptionPrefix = giftCertificateQueryParameter.getDescriptionPrefix();
        if (giftDescriptionPrefix != null) {
            Predicate predicate = criteriaBuilder.like(giftCertificateRoot.get(DaoConstants.FIELD_DESCRIPTION),
                    DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD + giftDescriptionPrefix + DaoConstants.ZERO_OR_MORE_ELEMENTS_WILDCARD);
            predicateList.add(predicate);
        }

        List<String> tagNamePrefixes = giftCertificateQueryParameter.getTagNamePrefixes();
        if (tagNamePrefixes != null) {
            for (String tagNamePrefix : tagNamePrefixes){
                Join<GiftCertificateEntity, TagEntity> tagJoin = giftCertificateRoot.join(DaoConstants.FIELD_TAG_ENTITIES);
                Predicate predicate = criteriaBuilder.equal(tagJoin.get(DaoConstants.FIELD_NAME), tagNamePrefix);
                predicateList.add(predicate);
            }
        }

        criteriaQuery.select(giftCertificateRoot).where(predicateList.toArray(new Predicate[0]));

        String sortField = giftCertificateQueryParameter.getSortField();
        String sortMethod = giftCertificateQueryParameter.getSortMethod();

        String columnNameToSort;
        Order order = null;

        if (sortField != null && sortMethod != null) {
            if (sortField.equals(NAME_FIELD)) {
                columnNameToSort = DaoConstants.FIELD_NAME;
                if (sortMethod.equals(SearchConstants.ASC_METHOD_SORT)) {
                    order = criteriaBuilder.asc(giftCertificateRoot.get(columnNameToSort));
                }
                if (sortMethod.equals(SearchConstants.DESC_METHOD_SORT)) {
                    order = criteriaBuilder.desc(giftCertificateRoot.get(columnNameToSort));
                }
            }
            if (sortField.equals(SearchConstants.DATE_FIELD)) {
                columnNameToSort = DaoConstants.FIELD_CREATE_DATE;
                if (sortMethod.equals(SearchConstants.ASC_METHOD_SORT)) {
                    order = criteriaBuilder.asc(giftCertificateRoot.get(columnNameToSort));
                }
                if (sortMethod.equals(SearchConstants.DESC_METHOD_SORT)) {
                    order = criteriaBuilder.desc(giftCertificateRoot.get(columnNameToSort));
                }
            }
            criteriaQuery.orderBy(order);
        }
        return criteriaQuery;
    }
}
