package com.paymybill.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "currency", catalog = "paymybilldb", schema = "public")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1325687088267757690L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "currency_seq_gen")
    @SequenceGenerator(name = "currency_seq_gen", sequenceName = "currency_id_seq")
    private Long id;

    @Column(name = "sign")
    private String sign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
