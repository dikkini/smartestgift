package com.paymybill.dao.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table (name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1236835088034759790L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
    @SequenceGenerator(name = "file_seq_gen", sequenceName = "file_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "mimetype")
    private String mimeType;

    @Column(name = "size")
    private Float size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }
}
