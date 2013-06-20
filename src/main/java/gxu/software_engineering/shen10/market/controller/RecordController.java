/*
 * Copyright 2013 Department of Computer Science and Technology, Guangxi University
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
package gxu.software_engineering.shen10.market.controller;

import static gxu.software_engineering.shen10.market.util.Consts.BAD_REQUEST;
import static gxu.software_engineering.shen10.market.util.Consts.RECORD;
import static gxu.software_engineering.shen10.market.util.Consts.RECORDS;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS_OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import gxu.software_engineering.shen10.market.entity.Record;
import gxu.software_engineering.shen10.market.service.RecordService;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 成功交易控制器。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-21
 */
@Controller
@Scope("session")
public class RecordController {
	
	private static final Logger L = LoggerFactory.getLogger(RecordController.class);
	
	@Inject
	private RecordService recordService;
	
	@RequestMapping(value = "/records/add", method = POST)
	public String add(
			Model model,
			@RequestParam("uid") long uid,
			@RequestParam("item_id") long itemId,
			@RequestParam(value = "extra", required = false) String extra) {
		L.info("成功交易: {}, uid: {}, itemId: {}", uid, itemId);
		Record record = recordService.create(itemId, uid, extra);
		
		model.addAttribute(RECORD, record);
		model.addAttribute(STATUS, STATUS_OK);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/records", method = GET)
	public String list(
			Model model,
			@RequestParam("uid") long uid,
			@RequestParam("count") int count,
			@RequestParam(value = "last_id", defaultValue = "0") long lastId) {
		List<Record> list = recordService.list(uid, count, lastId);
		model.addAttribute(RECORDS, list);
		return BAD_REQUEST;
	}

}
