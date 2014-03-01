package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by dikkini on 19.11.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Table (name = "file")
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
    @SequenceGenerator(name = "file_seq_gen", sequenceName = "file_id_seq")
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "size")
    protected String size;

    @ManyToOne
    @JoinColumn(name="type_id")
    protected FileType type;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "files", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    protected Set<Gift> gifts;

    public File() {}

    public File(String name, String size, FileType type) {
        this.name = name;
        this.size = size;
        this.type = type;
    }

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }
}
