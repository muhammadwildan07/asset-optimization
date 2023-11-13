package com.finalproject.assetmanagement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "branch_code" )
    private String branchCode;

    @Column(name = "branch_name" )
    private String branchName;

    @Column(name = "address" )
    private String address;

    @Column(name = "mobile_phone"  , unique = true)
    private String mobilePhone;

    @OneToMany(mappedBy = "branch")
    private List<Asset> assets;
}
