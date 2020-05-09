package com.jt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jt.util.HttpClientService;
@SpringBootTest
public class TestHttpClient {
	/**
	 * 1.实例化httpClient对象
	 * 2.确定url地址
	 * 3.定义请求方式类型 get post put delete
	 * 4.利用api发起HTTP请求
	 */
	@Test
	public void test01() {
		HttpClient httpClient=HttpClients.createDefault();
		String url = "https://www.baiddu.com";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			int code = httpResponse.getStatusLine().getStatusCode();	//获取响应的状态码
			String reason = httpResponse.getStatusLine().getReasonPhrase();		//获取响应的状态信息
			System.out.println("获取返回信息："+code+":"+reason);

			
			if(code==200) {
				HttpEntity httpEntity=httpResponse.getEntity();  //获取响应的实体
				//将信息转化为String类型，进行展现，防止乱码
				String result = EntityUtils.toString(httpEntity,"utf-8");
				System.out.println(result);
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	@Autowired
	private HttpClientService httpClientService;
	
	@Test
	public void testGet() {
		String url = "http://www.baidu.com";
		Map<String,String> params=new HashMap<String,String>();
		params.put("id", "123");
		params.put("性别", "男");
		params.put("age", "1222");
		String result =httpClientService.doGet(url, params, null);
		
	}
	
}
