/**
The MIT License (MIT) * Copyright (c) 2015 铭飞科技

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.basic.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.mingsoft.base.action.BaseAction;
import com.mingsoft.basic.biz.IModelTemplateBiz;
import com.mingsoft.basic.entity.ModelTemplateEntity;

/**
 * 模块自定义页面
 * 
 * @author 王天培
 * @version 版本号：100-000-000<br/>
 *          创建日期：2014-6-29<br/>
 *          历史修订：<br/>
 */
@Controller
@RequestMapping("/manager/modeltemplate/")
public class ModelTemplateAction extends BaseAction {

    /**
     * appBiz业务层的注入
     */
    @Autowired
    private IModelTemplateBiz modelTemplateBiz;

    /**
     * 加载模块编辑页面
     * 
     * @return 模板模块编辑地址
     */
    @RequestMapping(value = "/add")
    public String add() {
        return "model_template_add";
    }

    /**
     * 根据id删除自定义模块
     * 
     * @param id 自定义模块id
     * @param response 响应对象
     */
    @RequestMapping(value = "/{id}/delete")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        modelTemplateBiz.deleteEntity(id);
        this.outJson(response, null, true);
    }

    /**
     * 根据模板模块id编辑模板模块
     * 
     * @param id 模板模块id
     * @param response 响应对象
     */
    @RequestMapping(value = "/{id}/edit")
    public void edit(@PathVariable int id, HttpServletResponse response) {
        ModelTemplateEntity modelTemplate = (ModelTemplateEntity) modelTemplateBiz.getEntity(id);
        this.outJson(response, null, true, null, JSON.toJSONString(modelTemplate));
    }

    /**
     * 加载自定义模块列表页面
     * 
     * @param mode ModelMap实体对象
     * @param request 请求对象
     * @return 自定义模块列表页面地址
     */
    @RequestMapping(value = "/list")
    public String list(ModelMap mode, HttpServletRequest request) {
        List list = modelTemplateBiz.queryByAppId(this.getAppId(request));
        mode.addAttribute("list", list);
        return "/manager/model_template/model_template_list";
    }

    /**
     * 添加模板模块
     * 
     * @param modelTemplate 模板模块实体
     * @param response 响应对象
     * @param request 请求对象
     */
    @RequestMapping(value = "/save")
    public void save(@ModelAttribute ModelTemplateEntity modelTemplate,
            HttpServletResponse response, HttpServletRequest request) {
        modelTemplate.setModelTemplateAppId(this.getAppId(request));
        modelTemplateBiz.saveEntity(modelTemplate);
        this.outJson(response, null, true);
    }

    /**
     * 根据模板模块id更新模板实体
     * 
     * @param id 模板模块id
     * @param modelTemplate 模板模块实体
     * @param response 响应对象
     */
    @RequestMapping(value = "/{id}/update")
    public void update(@PathVariable int id, @ModelAttribute ModelTemplateEntity modelTemplate,
            HttpServletResponse response) {
        modelTemplate.setModelTemplateId(id);
        modelTemplateBiz.updateEntity(modelTemplate);
        this.outJson(response, null, true);
    }
}
