package com.zslin.web.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String password;

    @ManyToOne
    private Priority priority;
}
