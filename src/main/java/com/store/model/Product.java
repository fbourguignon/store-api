package com.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_product", uniqueConstraints= @UniqueConstraint(columnNames={ "name"}))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;


}
