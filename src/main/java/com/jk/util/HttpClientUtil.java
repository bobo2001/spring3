package com.jk.util;
 
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
  
/**
 * 
 * 类: HttpClient <br>
 * 描述: httpclient工具类 <br>
 * 作者: song<br>
 * 时间: 2017年7月21日 下午3:15:27
 */
public class HttpClientUtil {
	
	static CloseableHttpClient client = null;
	static {
		client = HttpClients.createDefault();
	}
	
	public static String get2(String url,Object params){
		try {
			HttpGet httpGet = new HttpGet();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(url).append("/").append(params);
			httpGet.setURI(new URI(stringBuffer.toString()));
			CloseableHttpResponse execute = client.execute(httpGet);
			int statusCode = execute.getStatusLine().getStatusCode();
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * 方法: get <br>
	 * 描述: get请求 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年7月21日 下午3:15:25
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String get(String url,HashMap<String, Object> params){
		try {
			HttpGet httpGet = new HttpGet();
			Set<String> keySet = params.keySet();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(url).append("?t=").append(System.currentTimeMillis());
			for (String key : keySet) {
				stringBuffer.append("&").append(key).append("=").append(params.get(key));
			}
			httpGet.setURI(new URI(stringBuffer.toString()));
			CloseableHttpResponse execute = client.execute(httpGet);
			int statusCode = execute.getStatusLine().getStatusCode();
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * 方法: post <br>
	 * 描述: post请求 <br>
	 * 作者: Teacher song<br>
	 * 时间: 2017年7月21日 下午3:20:31
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url,HashMap<String, Object> params) {
		try {
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(new URI(url));
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				NameValuePair e = new BasicNameValuePair(key, params.get(key).toString());
				parameters.add(e);
			}
			HttpEntity entity = new UrlEncodedFormEntity(parameters , "utf-8");
			httpPost.setEntity(entity );
			CloseableHttpResponse execute = client.execute(httpPost);
			int statusCode = execute.getStatusLine().getStatusCode();
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			e.printStackTrace();     
			return null;
		}
	}
	

	
	public static String get1(String url,HashMap<String, Object> params){
		try {
			HttpGet httpGet = new HttpGet();
			// map集合.keySet() 方法，拿到map集合中的key
			// map.get(key)
			// params = [name:zhangsan,age:23]
			// keySet: name age
			Set<String> keySet = params.keySet();
			StringBuffer stringBuffer = new StringBuffer();
			// url: 127.0.0.1:8080/aaa/bbb.do
			// ?t=12345679000&name=zhangsan&age=23
			stringBuffer.append(url).append("?t=").append(System.currentTimeMillis());
			for (String key : keySet) {
				stringBuffer.append("&").append(key).append("=").append(params.get(key));
			}
			// 127.0.0.1:8080/aaa/bbb.do?t=12345679000&name=zhangsan&age=23
			httpGet.setURI(new URI(stringBuffer.toString()));
			// 发起请求，返回响应
			CloseableHttpResponse execute = client.execute(httpGet);
			int statusCode = execute.getStatusLine().getStatusCode();
			// 如果状态码等于200 请求成功
			if (200 != statusCode) {
				return "";
			}
			//execute.getEntity() 获取响应里面的实体对象
			return EntityUtils.toString(execute.getEntity(), "utf-8");

			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			}
	}
	/**
	 * 127.0.0.1:8080/aaa/bbb.do/zhangsan/23
	 * get请求 参数为/传递    
	 * @param url 请求地址
	 * @param params String类型数组形式 String... 变量名
	 * @return
	 */
	public static String get(String url, String...params){
		try {
			HttpGet httpGet = new HttpGet();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(url);
			for (String string : params) {
				stringBuffer.append("/").append(string);
			}
			httpGet.setURI(new URI(stringBuffer.toString()));
			CloseableHttpResponse execute = client.execute(httpGet);
			int statusCode = execute.getStatusLine().getStatusCode();
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String post1(String url,HashMap<String, Object> params) {
		try {
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(new URI(url));
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				NameValuePair e = new BasicNameValuePair(key, params.get(key).toString());
				parameters.add(e);
			}
			HttpEntity entity = new UrlEncodedFormEntity(parameters , "utf-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse execute = client.execute(httpPost);
			int statusCode = execute.getStatusLine().getStatusCode();
			if (200 != statusCode) {
				return "";
			}
			return EntityUtils.toString(execute.getEntity(), "utf-8");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
