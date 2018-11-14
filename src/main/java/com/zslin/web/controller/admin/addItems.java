package com.zslin.web.controller.admin;

import com.zslin.web.model.*;
import com.zslin.web.service.IGoods_infoService;
import com.zslin.web.service.IProjectService;
import com.zslin.web.service.IRepertory_opeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/14.
 */
@RestController
@RequestMapping("/items")
public class addItems {
    @Autowired
    private IGoods_infoService goods_infoService;
    @Autowired
    private IRepertory_opeService repertory_opeService;
    @Autowired
    private IProjectService projectService;
    private Map data;
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public JSONObject searchItem(@RequestParam String id, HttpServletRequest request){
        Project project = this.projectService.findById(Integer.parseInt(id));
        data = new HashMap( );
        if(project != null) {
            project.setGoods_info(null);
            data.put("status", "success");
            data.put("data",project);
        } else {
            data.put("status", "fail");
        }
        JSONObject result = JSONObject.fromObject(data);
        return result;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addItem(@RequestBody projectInfo project_info) throws java.text.ParseException {
        try {
            Project project = this.projectService.findById(Integer.parseInt(project_info.getId()));
            if (project == null) {
                Project newProject = new Project();
                newProject.setName(project_info.getName());
                newProject.setPrinciple(project_info.getPrinciple());
                newProject.setReleaseDate(project_info.getReleaseDate());
                newProject.setStartDate(project_info.getStartDate());
                newProject.setRemarks(project_info.getRemarks());
                this.projectService.save(newProject);
            } else {
                project.setName(project_info.getName());
                project.setPrinciple(project_info.getPrinciple());
                project.setReleaseDate(project_info.getReleaseDate());
                project.setStartDate(project_info.getStartDate());
                project.setRemarks(project_info.getRemarks());
                this.projectService.save(project);
            }
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
}
