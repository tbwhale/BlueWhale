package com.bluewhale.common.util;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * -Restful常用工具类
 * @author ZhangXr
 *
 */
public class RestTemplateUtil {
	
	/**
	 * -解决字符串中文乱码问题
	 * @param charSet
	 * @return
	 */
	public static RestTemplate getRestTemplate(String charSet) {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> httpMessageConverter : messageConverters) {
			if (httpMessageConverter instanceof StringHttpMessageConverter) {
				StringHttpMessageConverter messageConverter = 
						(StringHttpMessageConverter) httpMessageConverter;
				messageConverter.setDefaultCharset(Charset.forName(charSet));
				break;
			}
		}
		return restTemplate;
	}

}
