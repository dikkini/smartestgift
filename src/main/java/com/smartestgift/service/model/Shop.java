package com.smartestgift.service.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 2:07 PM
 */

@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @Type(type = "pg-uuid")
    private UUID uuid;

    @Column(nullable = true, insertable = false, unique = false)
    private String name;

    @Column(nullable = true, insertable = false, unique = false)
    private String description;

}
