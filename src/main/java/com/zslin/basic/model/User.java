package com.zslin.basic.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
@Entity
@Table(name="a_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Integer status;
    @Column(name="create_date")
    private Date createDate;
    /** 用户昵称 */
    private String nickname;

    @Column(name="is_admin")
    private Integer isAdmin;
}
