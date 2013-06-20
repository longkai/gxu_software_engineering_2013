/*
 * Copyright (c) 2001-2013 newgxu.cn <the original author or authors>.
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

import static gxu.software_engineering.shen10.market.util.Consts.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局的异常处理器，采用json写错误信息。
 * 
 * @author longkai
 * @email im.longkai@gmail.com
 * @since 2013-3-29
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger L = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * 全局异常处理器，支持ajax，html的错误视图~
	 * @param t 触发异常
	 * @return json or html depends on what the client wants!
	 */
	@ExceptionHandler(Throwable.class)
	public ModelAndView exp(Throwable t) {
		L.error("全局异常处理中。。。", t);
		ModelAndView mav = new ModelAndView(ERROR_PAGE);
		mav.addObject(STATUS, STATUS_NO);
		mav.addObject(MESSAGE, t.getMessage());
		if (t.getCause() != null) {
			mav.addObject(EXP_REASON, t.getCause().getMessage());
		} else {
			mav.addObject(EXP_REASON, UNKNOWN_REASON);
		}
		return mav;
	}
	
}
