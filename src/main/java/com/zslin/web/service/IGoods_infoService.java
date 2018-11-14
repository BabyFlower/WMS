package com.zslin.web.service;

import com.zslin.web.model.Device;
import com.zslin.web.model.Goods_info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/9/22.
 */

@Service
public interface IGoods_infoService extends JpaRepository<Goods_info, Integer>, JpaSpecificationExecutor<Goods_info> {

    @Query("FROM Goods_info d where d.barCode=:id")
    Goods_info findBybarCode(@Param("id") String id);
}