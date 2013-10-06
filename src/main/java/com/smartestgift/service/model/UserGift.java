package com.smartestgift.service.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 10/6/13
 * Time: 11:00 PM
 */

@Table(name = "usergift")
public class UserGift {

    @Id
    @Column(nullable = false, insertable = false, unique = true)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Gift gift;
}
