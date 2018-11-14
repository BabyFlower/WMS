package com.zslin.web.controller.admin;

import com.zslin.web.model.Goods_info;
import com.zslin.web.service.IGoods_infoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/22.
 */
@RestController
public class barCodeController {
    @Autowired
    private IGoods_infoService goods_infoService;
    private Map data;
    @RequestMapping(value = "/data/goodsInfo", method = RequestMethod.GET)
    public JSONObject barCodeSearch(@RequestParam String barCode,HttpServletRequest request){


        StringBuilder  cn = new StringBuilder (barCode);
        String bar_code = cn.toString();
        data = new LinkedHashMap();
        Goods_info goods_info = this.goods_infoService.findBybarCode(bar_code);

        if (goods_info == null){
            data.put("state","0");
        }else{
            data.put("state","1");
            data.put("name",goods_info.getName());
            data.put("type",goods_info.getType());
            data.put("sn",goods_info.getSN());
        }
        JSONObject result = JSONObject.fromObject(data);
        return result;
    }
}
