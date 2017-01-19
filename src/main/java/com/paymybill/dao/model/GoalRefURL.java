package com.paymybill.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "goal_ref_url", catalog = "paymybilldb", schema = "public")
public class GoalRefURL implements Serializable {

    private static final long serialVersionUID = 1224486710361253530L;

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private UUID uuid;

    @Column(name = "refurl")
    private String refURL;

    @OneToOne(fetch=FetchType.LAZY, mappedBy="goalRefURL")
    private Goal goal;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRefURL() {
        return refURL;
    }

    public void setRefURL(String refURL) {
        this.refURL = refURL;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
