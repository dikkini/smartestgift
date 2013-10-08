package com.smartestgift.service.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 2:07 PM
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Type(type = "pg-uuid")
    private UUID uuid;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Gift gift;
}
