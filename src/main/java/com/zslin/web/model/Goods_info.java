package com.zslin.web.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */
@Data
@Entity
@Table(name = "Goods_info")
public class Goods_info {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String type;
    private String SN;
    private String status;
    private String property;

    @OneToOne
    private Project project;

    @OneToMany(mappedBy = "goods_info")
    private List<Repertory_ope> repertory_ope;
}
