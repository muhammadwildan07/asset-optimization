package com.finalproject.assetmanagement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "username" )
    private String username;

    @Column(name = "password" )
    private String password;

    @Email
    @Column(name = "email" )
    private String email;

    @Column(name = "mobilePhone")
    private String mobilePhone;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @OneToOne
    @JoinColumn(name = "user_credential")
    private UserCredential userCredential;
}
