package com.zslin.web.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


/**
 * Created by Administrator on 2018/8/14.
 */

@Data
@Entity
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private Date startDate;
    private Date releaseDate;
    private String principle;
    private String remarks;

    @OneToOne(mappedBy = "project")
    private Goods_info goods_info;
}
