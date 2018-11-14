package com.zslin.web.controller.admin;

import com.zslin.web.model.Project;
import com.zslin.web.service.IProjectService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/27.
 */
@RestController
public class itemSearchController {
    @Autowired
    private IProjectService projectService;
    private Map data;
    @RequestMapping(value = "/itemList",method = RequestMethod.GET)
    public JSONObject addsite(Model model, HttpServletRequest request){

        List<Project> projects = this.projectService.findAll();
       if(projects!=null) {
           for (int i = 0; i < projects.size(); i++) {
                  projects.get(i).setGoods_info(null);
               }
            }

        data = new HashMap( );
        data.put("data",projects);
        JSONObject result = JSONObject.fromObject(data);
        return result;
    }
}
