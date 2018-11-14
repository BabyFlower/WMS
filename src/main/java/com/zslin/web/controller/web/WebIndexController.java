package com.zslin.web.controller.web;

import com.google.zxing.qrcode.decoder.Mode;
import com.zslin.basic.exception.SystemException;
import com.zslin.basic.model.User;
import com.zslin.basic.service.IUserService;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.basic.utils.BaseSearch;
import com.zslin.basic.utils.PageableUtil;
import com.zslin.basic.utils.SearchDto;
import com.zslin.basic.utils.SortDto;
import com.zslin.web.model.*;
import com.zslin.web.service.*;
import com.zslin.web.tools.EmailTools;
import com.zslin.web.tools.RandomTools;
import com.zslin.web.tools.RequestTools;
import com.zslin.web.tools.SmsTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.HyperlinkEvent;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
public class WebIndexController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private EmailTools emailTools;
    @Autowired
    private SmsTool smsTool;
    @Autowired
    private IUserService userService;

    private Map data;
    private String registerCode="";
    @RequestMapping(value={"","/","index"},method=RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){
        return "web/index";
    }
    /** 用户注册 */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(HttpServletRequest request) {
        return "web/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model, HttpServletRequest request) {
        String email = request.getParameter("email"); //邮箱地址
        String code = request.getParameter("code"); //验证码
        String name = request.getParameter("name"); //用户昵称
        String pwd=request.getParameter("password");//用户的密码
        String registerCode = (String) request.getSession().getAttribute("registerCode");
        Account a = accountService.findByEmail(email);
        if(a!=null) {
            throw new SystemException("邮箱地址【"+email+"】已经存在。如果忘记密码请<a href='/findPwd'>找回密码</a>");
        } else if(!registerCode.equalsIgnoreCase(code)) {
            throw new SystemException("邮箱验证码输入错误！");
        } else {
            /*String pwd = RandomTools.randomCode(); //随机密码*/
            a = new Account();
            a.setEmail(email);
            a.setNickname(name);
            try {
                a.setPassword(SecurityUtil.md5(email, pwd));
            } catch (NoSuchAlgorithmException e) {
            }
            a.setStatus("1");
            accountService.save(a);

            /*emailTools.sendRegisterSuc(email, pwd, RequestTools.getCurUrl(request)+"/web/login");
            emailTools.sendOnRegister(RequestTools.getAdminEmail(request), name, email, "#");*/
        }
        return "redirect:/web/login?reg=true";
    }

    @RequestMapping(value = "appregister", method = RequestMethod.POST)
    public @ResponseBody String appregister(HttpServletRequest request) {
        String email = request.getParameter("email"); //邮箱地址
        String code = request.getParameter("code"); //验证码
        String name = request.getParameter("name"); //用户昵称
        String pwd=request.getParameter("password");//用户的密码
        System.out.println("ok");
        Account a = accountService.findByEmail(email);
        if(a!=null) {
            return "1";
        }else if (registerCode.equals("")){
            return "2";
        }
        else if(!registerCode.equalsIgnoreCase(code)) {
             return "3";
        } else {
            /*String pwd = RandomTools.randomCode(); //随机密码*/
            a = new Account();
            a.setEmail(email);
            a.setNickname(name);
            try {
                a.setPassword(SecurityUtil.md5(email, pwd));
            } catch (NoSuchAlgorithmException e) {
            }
            a.setStatus("1");
            accountService.save(a);

            /*emailTools.sendRegisterSuc(email, pwd, RequestTools.getCurUrl(request)+"/web/login");
            emailTools.sendOnRegister(RequestTools.getAdminEmail(request), name, email, "#");*/
        }
        return "4";
    }
    /** 找回密码 */
    @RequestMapping(value = "findPwd")
    public String findPwd(Model model, HttpServletRequest request) {
        String method = request.getMethod();
        if("get".equalsIgnoreCase(method)) {
            return "web/findPwd";
        } else {
            String email = request.getParameter("email");
            String code = request.getParameter("code");

            if(email==null || code==null) {
                throw new SystemException("请输入邮箱地址和验证码");
            }
            String oldCode = (String) request.getSession().getAttribute("registerCode");
            if(!code.equalsIgnoreCase(oldCode)) {
                throw new SystemException("验证码输入不正确！");
            }

            try {
                String randPwd = RandomTools.randomCode();
                Account a = accountService.findByEmail(email);
                if(a==null) {throw new SystemException("未检索到【"+email+"】对应的用户。");}
                a.setPassword(SecurityUtil.md5(email, randPwd));
                accountService.save(a);

                String url = RequestTools.getCurUrl(request)+"/web/login";
                emailTools.sendFindPwdSuc(email, randPwd, url);

            } catch (NoSuchAlgorithmException e) {
            }

            return "redirect:/web/login";
        }
    }

    /**  用户充值登陆*/
    @RequestMapping(value = "account",method = RequestMethod.GET)
    public String account() {

        return "webm/account/pay2";
    }

    /**  用户充值完成跳转*/
    @RequestMapping(value = "payOk",method = RequestMethod.GET)
    public String payOk() {
        return "webm/account/index";
    }

    /**  后台跳转*/
    @RequestMapping(value = "basic/robot_data",method = RequestMethod.GET)
    public String robot_data() {
        return "pages/robot_data";
    }

    @RequestMapping(value = "basic/flot",method = RequestMethod.GET)
    public String flot() {
        return "pages/flot";
    }

    @RequestMapping(value = "basic/data",method = RequestMethod.GET)
    public String data() {
        return "pages/data";
    }

    /** 站点添加 */
    @RequestMapping(value = "basic/addsite")
    public String addsite(Model model) {
           List<User> users=this.userService.findAll();
           model.addAttribute("prods",users);
                return "pages/addsite";
    }


    /** 设备添加 */
    @RequestMapping(value = "basic/adddevice")
    public String adddevice(Model model, HttpServletRequest request){
        return "pages/adddevice";
    }


    /** 用户信息　*/
    @RequestMapping(value = "basic/userinfo")
    public String userinfo(Model model,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("login_user");
        if(user.getIsAdmin()==1){
            List<User> users=this.userService.findAll();
            model.addAttribute("prods",users);
            return "pages/userinfo";
        }else{
            model.addAttribute("prods",user);
            return "pages/userinfo";
        }
    }

    /** 添加用户 */
    @RequestMapping(value = "basic/adduser")
    public String adduser(){
        return "pages/adduser";
    }

    /** 用户信息修改 */
    @RequestMapping(value = "basic/verifyuser")
    public String verifyuser(Model model,HttpServletRequest request){
        String username=request.getParameter("userid");
        User user=this.userService.findByUsername(username);
        model.addAttribute("user",user);
        return "pages/verifyuser";
    }

    /** 用户登陆 */
    @RequestMapping(value = "/appmanage/login")
    @ResponseBody
    public JSONObject appmanagelogin(@RequestParam String username,@RequestParam String password,HttpServletRequest request) {
        String method = request.getMethod();
        data=new LinkedHashMap();
        if("post".equalsIgnoreCase(method)) {
            if(username==null || password==null) {
                data.put("state","usernameorpasswordNull");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
            User user = userService.findByUsername(username);
            if(user==null || user.getStatus()==0) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
            try {
                String pwd = SecurityUtil.md5(username, password);
                if(!pwd.equals(user.getPassword())) {
                    data.put("state","fail");
                    JSONObject result = JSONObject.fromObject(data);
                    return result;
                } else {
                    data.put("state","success");
                    data.put("isadmin",user.getIsAdmin().toString());
                    JSONObject result = JSONObject.fromObject(data);
                    return result;
                }
            } catch (NoSuchAlgorithmException e) {
                data.put("state","fail");
                JSONObject result = JSONObject.fromObject(data);
                return result;
            }
        } else {
            data.put("state","fail");
            JSONObject result = JSONObject.fromObject(data);
            return result;
        }
    }

    /** 用户登陆 */
    @RequestMapping(value = "/web/login")
    public String login(Model model, HttpServletRequest request) {
        String method = request.getMethod();
        if("post".equalsIgnoreCase(method)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if(email==null || password==null) {
                throw new SystemException("请输入手机号和密码登陆。");
            }
            Account account = accountService.findByEmail(email);
            if(account==null || !account.getStatus().equalsIgnoreCase("1")) {
                throw new SystemException("账户【"+email+"】不存在或已被停用。");
            }
            try {
                String pwd = SecurityUtil.md5(email, password);
                if(!pwd.equals(account.getPassword())) {
                    throw new SystemException("密码输入错误！");
                } else {
                    request.getSession().setAttribute("login_account", account);
                }
                return "redirect:/webm/account/index";
            } catch (NoSuchAlgorithmException e) {
            }
        } else {
            Account account = (Account) request.getSession().getAttribute("login_account");
            if(account!=null) {
                return "redirect:/webm/account/index";
            }
        }
        return "web/login";
    }

    /** 退出 */
    @RequestMapping(value = "/web/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("login_account");
        return "redirect:/";
    }

    /** 发送邮箱验证码 */
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    public @ResponseBody String sendCode(String email, HttpServletRequest request) {
        try {
            String code = RandomTools.randomCode();
            registerCode=code;
            request.getSession().setAttribute("registerCode", code);
            if(email.contains("@")){
                emailTools.sendRegisterCode(email, code);
            }
            else{
                smsTool.sendRegisterCode(email,code);
            }
            /*smsTool.sendRegisterCode(email,code);*/
        } catch (Exception e) {
            return "0";
        }
        return "1";
    }


}
