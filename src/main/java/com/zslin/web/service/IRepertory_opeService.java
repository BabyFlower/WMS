package com.zslin.web.service;

import com.zslin.web.model.Goods_info;
import com.zslin.web.model.Repertory_ope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/9/22.
 */

@Service
public interface IRepertory_opeService extends JpaRepository<Repertory_ope, Integer>, JpaSpecificationExecutor<Repertory_ope> {

    @Query("FROM Repertory_ope d where d.goods_info=:id")
    List<Repertory_ope> findByGoods_info(@Param("id") Integer id);
}