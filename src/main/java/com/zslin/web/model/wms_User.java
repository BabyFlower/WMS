package com.zslin.web.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/9/22.
 */

@Data
@Entity
@Table(name = "wms_User")
public class wms_User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String password;

    @ManyToOne
    private Priority priority;
}
