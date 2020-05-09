package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//引入dubbo配置
	@Reference(check=false)
	private DubboUserService userService;
	@Autowired
	private JedisCluster jedis;
	
	
	
	//实现通用页面跳转        resfule形式
	@RequestMapping("/{moduleName}")
	public String register(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	/**
	 * 
	 * @return  SysResult对象~~JSON串
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		
		userService.saveUser(user);
		return SysResult.success();
	}
	
	/**
	 * 业务：实现用户的登录操作
	 * url地址：http：//
	 * 参数：username/password
	 * 返回值类型：Sys Result对象
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		//1.将数据法网jt-sso系统完成校验
		String uuid = userService.findUserByUP(user);
		//2.需要校验uuid是否正确
		if(StringUtils.isEmpty(uuid)) {
			return SysResult.fail();
		}
		//3.将用户信息写入cookie中
		Cookie cookie = new Cookie("JT_TICKET",uuid);
		cookie.setMaxAge(7*24*3600);
		cookie.setPath("/");
		response.addCookie(cookie);
		return SysResult.success();
	}
	
	/**
	 * 业务需求：实现用户登出操作
	 * url
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if(cookies!=null && cookies.length>0) {
			
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("JT_TICKET")) {
					//3.动态获取cookie的值
					ticket = cookie.getValue();
					cookie.setMaxAge(0); 	//立即删除cookie
					cookie.setPath("/");
					cookie.setDomain("jt.com");
					response.addCookie(cookie);
				}
			}
			
		}
		
		if(jedis.exists(ticket)) {
			jedis.del(ticket);
		}
		
		return "redirect:/";
	}
	
	
}
