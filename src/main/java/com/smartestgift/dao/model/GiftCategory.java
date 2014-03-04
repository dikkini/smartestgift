package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    protected String code;

    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    protected File file;

    @OneToMany(mappedBy = "category")
    private Set<Gift> gifts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
        if (!(o instanceof GiftCategory)) return false;

        GiftCategory that = (GiftCategory) o;

        if (!code.equals(that.code)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (gifts != null ? !gifts.equals(that.gifts) : that.gifts != null) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }
}
