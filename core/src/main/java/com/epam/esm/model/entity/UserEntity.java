package com.epam.esm.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = {
        "orderEntities"
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderEntity> orderEntities;

    @Builder
    public UserEntity(Long id, Timestamp lastUpdate, Timestamp createDate, String firstName, String lastName, String email, String phoneNumber, Set<OrderEntity> orderEntities) {
        super(id, lastUpdate, createDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orderEntities = orderEntities;
    }
}
