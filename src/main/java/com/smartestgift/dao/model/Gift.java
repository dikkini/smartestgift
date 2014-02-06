package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "gift")
public class Gift implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @Column
    protected String name;

    @Column
    protected BigDecimal cost;

    @Column
    protected String description;

    @ManyToOne
    @JoinColumn(name="categoryid")
    protected GiftCategory category;
}
