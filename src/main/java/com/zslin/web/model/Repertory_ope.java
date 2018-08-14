package com.zslin.web.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
@Data
@Entity
@Table(name = "Repertory_ope")
public class Repertory_ope {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date date;
    private String operator;
    private String operation_issue;

    @ManyToOne
    private Goods_info goods_info;
}
