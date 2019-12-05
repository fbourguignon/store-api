package com.store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user", uniqueConstraints= @UniqueConstraint(columnNames={ "mail"}))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data public class User implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String lastName;

    private String mail;

    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "tb_user_id"),
            inverseJoinColumns = @JoinColumn(name = "tb_role_id"))
    private Set<Role> roles = new HashSet<>();

}
