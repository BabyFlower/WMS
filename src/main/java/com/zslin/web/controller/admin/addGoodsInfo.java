package com.zslin.web.controller.admin;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.web.model.Goods_info;
import com.zslin.web.model.Project;
import com.zslin.web.model.Repertory_ope;
import com.zslin.web.model.goodsInfo;
import com.zslin.web.service.IGoods_infoService;
import com.zslin.web.service.IProjectService;
import com.zslin.web.service.IRepertory_opeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Administrator on 2018/9/25.
 */
@RestController
@RequestMapping("/records")
public class addGoodsInfo {
    @Autowired
    private IGoods_infoService goods_infoService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IRepertory_opeService repertory_opeService;
    private java.util.Date date;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void items(@RequestBody goodsInfo goods_info) throws java.text.ParseException{
             Goods_info findGood = this.goods_infoService.findBybarCode(goods_info.getBarCode());
             Project project= this.projectService.findById(goods_info.getProject());
             if(findGood==null){

                 Goods_info newGood = new Goods_info();
                 newGood.setBarCode(goods_info.getBarCode());
                 newGood.setName(goods_info.getName());
                 if(project!=null){
                     newGood.setProject(project);
                 }
                 newGood.setProperty(goods_info.getProperty());
                 newGood.setSN(goods_info.getSN());
                 newGood.setType(goods_info.getType());
                 newGood.setStatus(goods_info.getStatus());
                 goods_infoService.save(newGood);
             }else{
                 findGood.setStatus(goods_info.getStatus());
                 findGood.setProperty(goods_info.getProperty());
                 if(project!=null){
                     findGood.setProject(project);
                 }
                 goods_infoService.save(findGood);
             }
             Repertory_ope repertory_ope = new Repertory_ope();
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             String dateString = formatter.format(new java.util.Date( ).getTime());
             repertory_ope.setDate(dateString);
             repertory_ope.setOperator(goods_info.getUsername());
             repertory_ope.setOperation_issue(goods_info.getOperation_issue());
             repertory_ope.setGoods_info(this.goods_infoService.findBybarCode(goods_info.getBarCode()));
             repertory_opeService.save(repertory_ope);

    }
}
