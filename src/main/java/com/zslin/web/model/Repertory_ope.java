package com.zslin.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
@Data
@Entity
@Table(name = "Repertory_ope")
@JsonIgnoreProperties(value = {"goods_info"})
public class Repertory_ope {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String date;
    private String operator;
    private String operation_issue;


    @ManyToOne
    private Goods_info goods_info;
}
