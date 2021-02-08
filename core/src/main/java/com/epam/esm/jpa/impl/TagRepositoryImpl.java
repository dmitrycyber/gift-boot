package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.TagRepository;
import com.epam.esm.jpa.criteria.TagCriteriaBuilder;
import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.jpa.exception.TagNotFoundException;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagRepositoryImpl implements TagRepository {
    private final TagCriteriaBuilder tagCriteriaBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagEntity> findAllTags() {
        return entityManager.createQuery("select tagEntity from TagEntity tagEntity", TagEntity.class).getResultList();
    }

    @Override
    public TagEntity findTagById(Long tagId) {
        TagEntity tagEntity = entityManager.find(TagEntity.class, tagId);
        if (tagEntity == null) {
            throw new UserNotFoundException(tagId.toString());
        }
        return tagEntity;
    }

    @Override
    public TagEntity findTagByName(String tagName) {
        Optional<TagEntity> tagByName = entityManager.createQuery("select tagEntity from TagEntity tagEntity where name=:tagName")
                .setParameter("tagName", tagName)
                .getResultStream()
                .findFirst();

        if (!tagByName.isPresent()) {
            throw new TagNotFoundException();
        }
        return tagByName.get();

    }

    @Override
    public List<TagEntity> findTagByPartName(TagSearchDto tagSearchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteriaQuery = tagCriteriaBuilder.build(criteriaBuilder, tagSearchDto.getTagName());

        TypedQuery<TagEntity> query = entityManager.createQuery(criteriaQuery);


        Integer pageNumber = tagSearchDto.getPageNumber();
        Integer pageSize = tagSearchDto.getPageSize();
        if (pageNumber != null && pageSize != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }

    @Override
    public TagEntity createTag(TagEntity tagEntity) {
        entityManager.persist(tagEntity);
        return tagEntity;
    }

    @Override
    public void deleteTagById(Long tagId) {
        TagEntity referenceEntity = entityManager.getReference(TagEntity.class, tagId);
        entityManager.remove(referenceEntity);
    }

    @Override
    public TagEntity findMostWidelyUsedUserTag(Long userId) {
        return entityManager.createQuery(
                "SELECT te FROM OrderEntity o " +
                        "JOIN o.giftCertificateEntity gc " +
                        "JOIN gc.tagEntities te " +
                        "WHERE o.userEntity.id = :userId " +
                        "GROUP BY te.id " +
                        "ORDER BY sum(o.cost) DESC "
                , TagEntity.class)
                .setParameter("userId", userId)
                .setMaxResults(1)
                .getSingleResult();
    }
}
