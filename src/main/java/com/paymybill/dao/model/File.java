package com.paymybill.dao.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 19.11.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table (name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1236835088034759790L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
    @SequenceGenerator(name = "file_seq_gen", sequenceName = "file_id_seq")
    protected Long id;

    @Column(name = "name")
    protected String name;
}
