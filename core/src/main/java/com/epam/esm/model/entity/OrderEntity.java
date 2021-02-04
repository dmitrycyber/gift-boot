package com.epam.esm.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    private Integer cost;
    private Timestamp purchaseDate;

    @ManyToOne
    @JoinColumn(name = "gift_id")
    private GiftCertificateEntity giftCertificateEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;


}
