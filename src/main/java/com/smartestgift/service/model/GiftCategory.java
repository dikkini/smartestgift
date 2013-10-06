package com.smartestgift.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 5:44 PM
 */

@Entity
@Table(name = "giftcategory")
public class GiftCategory {

    @Id
    @Column(nullable = false, insertable = false, unique = true)
    private Long id;

    @Column(nullable = true, insertable = false, unique = false)
    private String name;

    @Column(nullable = true, insertable = false, unique = false)
    private String description;

}
