/*
 * Copyright 2013 longkai
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package gxu.software_engineering.shen10.market.core;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 将试图映射成为jsonp的数据类型，继承了MappingJacksonJsonView。
 * 
 * 返回jsonp判断依据：
 * 		1：请求的后缀必须是jsonp（这个在spring中配置的，你也可以自己修改，但不推荐）
 * 		2：必须是GET请求
 * 		3：必须包含callback的查询参数
 * 否则转换为json的试图
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 * @since  2013-4-13
 */
public class MappingJacksonJsonpView extends MappingJacksonJsonView {

	public static final String DEFAULT_CONTENT_TYPE = "application/javascript";
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	public MappingJacksonJsonpView() {
//		这里，我们只需要在构造函数设置内容类型即可，字符编码已经在spirng的encoding filter中强制转码了
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	@Override
	public String getContentType() {
//		这里，我们必须重载这个方法，否则，spring将不会找到application/javascript的试图解析！
		return DEFAULT_CONTENT_TYPE;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		如果到了这里还没有确定字符集，那么就使用默认的utf8字符集
		// String charset = response.getCharacterEncoding();
		// if (charset == null || charset.length() == 0) {
		// 	response.setCharacterEncoding(DEFAULT_CHARSET);
		// }
		
		if (request.getMethod().toUpperCase().equals("GET")) {
			if (request.getParameterMap().containsKey("callback")) {
				ServletOutputStream ostream = response.getOutputStream();
//				这里，出于安全性考虑，用try包围了异常
				ostream.write(new String("try{"
						+ request.getParameter("callback") + "(").getBytes());
				super.render(model, request, response);
				ostream.write(new String(");}catch(e){}").getBytes());
//				这里，其实我也不清楚要不要close和flush，spring会替我们关掉吗？
//				自己打开的资源应该还是自己关闭的好。
				ostream.flush();
				ostream.close();
			} else {
				super.render(model, request, response);
			}
		} else {
			super.render(model, request, response);
		}
	}
	
}
