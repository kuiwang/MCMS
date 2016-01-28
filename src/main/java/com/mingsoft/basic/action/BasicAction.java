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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.biz.IBasicBiz;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 基础应用层
 * 
 * @author 王天培
 * @version 版本号：100-000-000<br/>
 *          创建日期：2014-6-15<br/>
 *          历史修订：<br/>
 */
@Controller
@RequestMapping("/manager/basic")
public class BasicAction extends BaseAction {

    /**
     * 业务层的注入
     */
    @Autowired
    private IBasicBiz basicBiz;

    /**
     * 业务层的注入
     */
    @Autowired
    private ICategoryBiz categoryBiz;

    /**
     * 加载添加文章页面
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @return 添加页面地址
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("categoryId", categoryId);
        return "/manager/basic/basic";
    }

    /**
     * 文章多选删除方法
     * 
     * @param response 响应对象
     * @param request 请求对象
     */
    @RequestMapping("/allDelete")
    @ResponseBody
    public void allDelete(HttpServletResponse response, HttpServletRequest request) {
        String checkboxData[] = request.getParameterValues("checkbox");
        if (!StringUtil.isBlank(checkboxData)) {
            for (String element : checkboxData) {
                int basicId = Integer.parseInt(element);
                // 获取id，查询该文章是否在该站点下
                basicBiz.deleteBasic(basicId);
            }
        }
        this.outJson(response, null, true);
    }

    /**
     * 根据文章id删除文章实体
     * 
     * @param basicId 文章id
     * @param response 响应对象
     * @param request 请求对象
     */
    @RequestMapping("/{basicId}/delete")
    @ResponseBody
    public void delete(@PathVariable int basicId, HttpServletResponse response,
            HttpServletRequest request) {
        basicBiz.deleteBasic(basicId);
        this.outJson(response, null, true);
    }

    /**
     * 加载编辑文档页面
     * 
     * @param basicId 文章id
     * @param request 请求对象
     * @return 编辑文档页面
     */
    @RequestMapping("/{basicId}/edit")
    public String edit(@PathVariable int basicId, HttpServletRequest request) {
        BasicEntity basic = basicBiz.getBasicEntity(basicId);
        request.setAttribute("basic", basic);
        return "/manager/basic/basic";
    }

    /**
     * 加载页面显示所有文章信息
     * 
     * @param request 请求对象
     * @param mode ModelMap实体对象
     * @param response 响应对象
     * @return 返回文章页面显示地址
     */
    @SuppressWarnings("static-access")
    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap mode, HttpServletResponse response) {

        // 获取站点id
        int appId = this.getManagerBySession(request).getBasicId();
        ModelEntity model = this.getCategoryModelCode(request);
        if (model == null) {
            this.outString(response, this.getResString("err"));
            return null;
        }
        List<CategoryEntity> list = categoryBiz.queryByAppIdOrModelId(appId, model.getModelId());
        JSONObject ja = new JSONObject();
        request.setAttribute("listCategory", JSON.toJSON(list).toString());
        // 返回路径
        return "/manager/basic/index";
    }

    /**
     * 加载文章列表页面，显示列表信息
     * 
     * @param request 请求对象
     * @param categoryId 栏目id
     * @return 文章列表页面
     */
    @RequestMapping("/{categoryId}/list")
    public String list(HttpServletRequest request, @PathVariable int categoryId) {
        String keyWord = request.getParameter("keyword");
        String categoryTitle = request.getParameter("categoryTitle");
        // 当前页面
        int pageNo = 1;
        // 获取页面的当页数
        if (request.getParameter("pageNo") != null) {
            pageNo = Integer.parseInt(request.getParameter("pageNo"));
        }
        String url = "/manager/basic/" + categoryId + "/list.do?categoryTitle="
                + StringUtil.encodeStringByUTF8(categoryTitle) + "&keyword="
                + (keyWord == null ? "" : keyWord);
        int count = 0;
        // 分页集合
        PageUtil page = new PageUtil(pageNo, 60, count, getUrl(request) + url);
        // 实例化对象
        List<BasicEntity> basicList = basicBiz.query(this.getAppId(request), categoryId, keyWord,
                page, this.getModelCodeId(request), null);
        request.setAttribute("basicList", basicList);
        request.setAttribute("categoryId", categoryId);
        return "/manager/basic/basic_list";
    }

    /**
     * 获取列表提供给ajax使用
     * 
     * @param response 响应对象
     * @param request 请求对象
     */
    @RequestMapping("/listForAjax")
    public void listForAjax(HttpServletResponse response, HttpServletRequest request) {
        PageUtil page = new PageUtil(1000);
        List list = basicBiz.query(this.getAppId(request), null, null, page, this
                .getBasicModelCode(request).getModelId(), null);
        this.outJson(response, JSON.toJSONString(list));
    }

    /**
     * 获取所有json数据
     */
    /**
     * 查询栏目下所有文章
     * 
     * @param categoryId 栏目id
     * @param request 请求对象
     * @param mode ModelMap实体对象
     * @param response 响应对象
     */
    @RequestMapping("/{categoryId}/query")
    public void query(@PathVariable Integer categoryId, HttpServletRequest request, ModelMap mode,
            HttpServletResponse response) {
        List<BasicEntity> list = basicBiz.query(categoryId);
        String jsonStr = JSON.toJSONString(list);
        LOG.debug(jsonStr);
        this.outJson(response, jsonStr);
    }

    /**
     * 保存文章实体
     * 
     * @param basic 文章实体对象
     * @param request 请求对象
     * @param response 响应对象
     */
    @RequestMapping("/save")
    public void save(@ModelAttribute BasicEntity basic, HttpServletRequest request,
            HttpServletResponse response) {
        basic.setBasicAppId(this.getAppId(request));
        basic.setBasicModelId(this.getModelCodeId(request));
        basicBiz.saveEntity(basic);
        this.outJson(response, null, true);
    }

    /**
     * 修改文章实体
     * 
     * @param basic 文章实体
     * @param request 请求对象
     * @param response 响应对象
     */
    @RequestMapping("/update")
    @ResponseBody
    public void update(@ModelAttribute BasicEntity basic, HttpServletRequest request,
            HttpServletResponse response) {
        basicBiz.updateBasic(basic);
        this.outJson(response, null, true);
    }

}
