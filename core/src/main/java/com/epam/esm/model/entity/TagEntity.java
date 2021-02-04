package com.epam.esm.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = {
        "giftCertificateEntities"
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
public class TagEntity extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "tagEntities")
    private Set<GiftCertificateEntity> giftCertificateEntities;

    @Builder
    public TagEntity(Long id, Timestamp lastUpdate, Timestamp createDate, String name, Set<GiftCertificateEntity> giftCertificateEntities) {
        super(id, lastUpdate, createDate);
        this.name = name;
        this.giftCertificateEntities = giftCertificateEntities;
    }
}
