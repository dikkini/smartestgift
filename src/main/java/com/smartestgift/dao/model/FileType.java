package com.smartestgift.dao.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by dikkini on 08.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "file_type")
public class FileType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "file_type_seq_gen")
    @SequenceGenerator(name = "file_type_seq_gen", sequenceName = "file_type_id_seq")
    protected Integer id;

    @Column(name = "name")
    protected String name;

    @Column (name = "path")
    protected String path;

    @OneToMany(mappedBy = "type")
    private Set<File> files;

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

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
