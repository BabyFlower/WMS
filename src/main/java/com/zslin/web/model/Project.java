package com.zslin.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


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
    private String startDate;
    private String releaseDate;
    private String principle;
    private String remarks;

    @OneToMany(mappedBy = "project")
    private List<Goods_info> goods_info;
}
