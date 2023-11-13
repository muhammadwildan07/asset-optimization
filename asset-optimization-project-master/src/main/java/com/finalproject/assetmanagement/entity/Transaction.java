package com.finalproject.assetmanagement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "inbound_item")
    private String inboundItem;

    @Column(name = "outbound_item")
    private String outboundItem;

    @Column(name = "loan_amount" )
    private Long loanAmount;

    @Column(name = "status")
    private String  status; // ganti boolean

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
