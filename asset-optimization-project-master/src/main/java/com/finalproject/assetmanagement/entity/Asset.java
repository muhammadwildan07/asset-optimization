package com.finalproject.assetmanagement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "asset_code" )
    private String assetCode;

    @Column(name = "asset_name" )
    private String name;

    @Column(name = "description" )
    private String description;

    @Column(name = "quantity" )
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
