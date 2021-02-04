package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.model.entity.GiftCertificateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificateEntity> findAll() {
        return entityManager.createQuery("select giftCertificate from GiftCertificateEntity giftCertificate", GiftCertificateEntity.class).getResultList();
    }

    @Override
    public GiftCertificateEntity findById(Long id) {
        return entityManager.find(GiftCertificateEntity.class, id);
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
