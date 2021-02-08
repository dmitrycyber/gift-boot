package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.criteria.GiftCriteriaBuilder;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final GiftCriteriaBuilder giftCriteriaBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificateEntity> findAll() {
        return entityManager.createQuery("select giftCertificate from GiftCertificateEntity giftCertificate", GiftCertificateEntity.class).getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAndSortGifts(GiftSearchDto giftSearchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> criteriaQuery = giftCriteriaBuilder.build(criteriaBuilder, giftSearchDto);
        TypedQuery<GiftCertificateEntity> query = entityManager.createQuery(criteriaQuery);

        Integer pageNumber = giftSearchDto.getPageNumber();
        Integer pageSize = giftSearchDto.getPageSize();
        if (pageNumber != null && pageSize != null){
            query.setFirstResult((pageNumber-1) * pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public GiftCertificateEntity findById(Long id) {
        GiftCertificateEntity giftCertificateEntity = entityManager.find(GiftCertificateEntity.class, id);
        if (giftCertificateEntity == null){
            throw new UserNotFoundException(id.toString());
        }
        return giftCertificateEntity;
    }

    @Override
    public GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity) {
        entityManager.persist(giftCertificateEntity);
        return giftCertificateEntity;
    }

    @Override
    public GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity){
        return entityManager.merge(giftCertificateEntity);
    }

    @Override
    public void deleteGift(Long id) {
        GiftCertificateEntity referenceEntity = entityManager.getReference(GiftCertificateEntity.class, id);
        entityManager.remove(referenceEntity);
    }
}
