package com.smartestgift.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by dikkini on 19.11.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table (name = "file")
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq_gen")
    @SequenceGenerator(name = "file_seq_gen", sequenceName = "file_id_seq")
    protected Integer id;

    @Column(name = "name")
    protected String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="type_id")
    private FileType type;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "files", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Gift> gifts;

    public File() {}

    public File(String name, FileType type) {
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;

        File file = (File) o;

        if (!id.equals(file.id)) return false;
        if (!name.equals(file.name)) return false;

        return true;
    }
}
