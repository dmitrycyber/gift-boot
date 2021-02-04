package com.epam.esm.jpa;

import com.epam.esm.model.entity.TagEntity;

public interface TagRepository extends BaseRepository<TagEntity, Long> {

    TagEntity findTagEntityByName(String name);


}
