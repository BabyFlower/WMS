package com.zslin.web.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */
@Data
@Entity
@Table(name = "Priority")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Boolean repertoryList_r;
    private Boolean repertoryList_a;
    private Boolean repertoryList_m;
    private Boolean repertoryList_d;
    private Boolean userManage_r;
    private Boolean userManage_a;
    private Boolean userManage_m;
    private Boolean userManage_d;

    @OneToMany(mappedBy = "priority")
    private List<User> user;
}
