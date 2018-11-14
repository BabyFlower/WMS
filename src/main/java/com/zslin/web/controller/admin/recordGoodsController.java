package com.zslin.web.controller.admin;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zslin.web.model.Account;
import com.zslin.web.model.Goods_info;
import com.zslin.web.model.Repertory_ope;
import com.zslin.web.model.repertoryOpeInfo;
import com.zslin.web.service.IGoods_infoService;
import com.zslin.web.service.IRepertory_opeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/9/22.
 */
@RestController
public class recordGoodsController {
    @Autowired
    private IGoods_infoService goods_infoService;
    @Autowired
    private IRepertory_opeService  repertory_opeService;
    private Map data;

    @RequestMapping(value = "/data/recordsInfo", method = RequestMethod.GET)
    public JSONObject records(@RequestParam String barCode, HttpServletRequest request) throws java.text.ParseException{


        StringBuilder cn = new StringBuilder(barCode);
        String bar_code = cn.toString();

        data = new HashMap();
        Goods_info goods_info = this.goods_infoService.findBybarCode(bar_code);
        if (goods_info == null) {
            data.put("state", "error");
            JSONObject result = JSONObject.fromObject(data);
            return result;
        }

        List<Repertory_ope> datas = goods_info.getRepertory_ope();

        List<repertoryOpeInfo> datatemp = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            repertoryOpeInfo temp = new repertoryOpeInfo();
            temp.setDate(datas.get(i).getDate());
            temp.setOperation_issue(datas.get(i).getOperation_issue());
            temp.setOperator(datas.get(i).getOperator());
            datatemp.add(temp);
        }

//        for(int i=0;i<datas.size();i++){
//            datas.get(i).setGoods_info(null);
//        }   //json能得到，但是返回的时候报错 getOutputStream() has already been called for this response
         int len  = datas.size();
        data.put("state","success");
          data.put("recordsTotal", len);
        data.put("data", datatemp);
        JSONObject result = JSONObject.fromObject(data);
        return result;
    }

}
