package com.smartestgift.service.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */
@Entity
@IdClass(ClientPK.class)
@Table(name = "client")
public class Client {

    @Id
    @Type(type = "pg-uuid")
    private UUID uuid;

    @Id
    private String login;

    @Column(nullable = true, insertable = false, unique = false)
    private Date birthDate;

    @Column(nullable = true, insertable = false, unique = false)
    private String firstName;

    @Column(nullable = true, insertable = false, unique = false)
    private String lastName;

    @Column(nullable = true, insertable = false, unique = false)
    private String middleName;

    @Column(nullable = true, insertable = false, unique = false)
    private String passwordMd5;

    @Column(nullable = true, insertable = false, unique = false)
    private byte[] photo;

    @Column(nullable = true, insertable = false, unique = false)
    private Date regDate;
}
