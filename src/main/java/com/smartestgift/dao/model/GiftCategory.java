package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "gift_category")
public class GiftCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column
    protected String name;

    @Column
    protected String description;
}
