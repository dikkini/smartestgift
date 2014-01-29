package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Person: dikkini
 * Date: 10/6/13
 * Time: 5:44 PM
 */

@Entity
@Table(name = "giftcategory")
public class GiftCategory implements Serializable {

    @Id
    @Column
    protected Long id;

    @Column
    protected String name;

    @Column
    protected String description;

    @OneToMany(mappedBy="giftCategory", fetch = FetchType.EAGER)
    private Set<Gift> gifts;


}
