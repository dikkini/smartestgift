package com.smartestgift.service.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */

@Entity
@Table(name = "gift")
public class Gift {

    @Id
    @Type(type = "pg-uuid")
    private UUID uuid;

    @Column(nullable = false, insertable = true, unique = false)
    private String name;

    @Column(nullable = true, insertable = true, unique = false)
    private String description;

    @Column(nullable = false, insertable = true, unique = false)
    private int priceid;

    @Column(nullable = false, insertable = true, unique = false)
    private int caterogyid;

}
