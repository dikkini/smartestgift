package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Person: dikkini
 * Date: 10/6/13
 * Time: 2:06 PM
 */

@Entity
@Table(name = "gift")
public class Gift implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @Column
    protected String name;

    @Column
    protected String description;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    protected GiftCategory giftCategory;

}
