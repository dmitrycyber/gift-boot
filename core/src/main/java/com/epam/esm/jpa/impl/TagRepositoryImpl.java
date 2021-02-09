package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.TagRepository;
import com.epam.esm.jpa.criteria.PaginationBuilder;
import com.epam.esm.jpa.criteria.TagCriteriaBuilder;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping
    public List<TagEntity> findAllTags(Integer pageNumber, Integer pageSize) {
        TypedQuery<TagEntity> query = entityManager.createQuery("select tagEntity from TagEntity tagEntity", TagEntity.class);
        PaginationBuilder.addPagination(pageNumber, pageSize, query);
        return query.getResultList();
    }

    @Override
    public List<TagEntity> findTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteriaQuery = tagCriteriaBuilder.build(criteriaBuilder, tagSearchDto.getTagName());

        TypedQuery<TagEntity> query = entityManager.createQuery(criteriaQuery);

        PaginationBuilder.addPagination(pageNumber, pageSize, query);
        return query.getResultList();
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

        return tagByName.orElse(null);
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
