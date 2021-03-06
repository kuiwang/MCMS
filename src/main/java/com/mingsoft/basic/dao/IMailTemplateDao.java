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

package com.mingsoft.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.entity.MailTemplateEntity;

/**
 * 邮件模板持久化层
 * 
 * @author 杨新远
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
public interface IMailTemplateDao extends IBaseDao {

    /**
     * 删除评论，多条或者一条
     * 
     * @param ids 多条评论集合
     */
    public void deleteAll(@Param("ids") String[] ids);

    /**
     * 根据应用编号与模块编号获取邮件模板
     * 
     * @param mailTemplateAppId 应用编号
     * @param modelId 模块编码
     * @return 返回MailTemplateEntity null:没有找到
     */
    MailTemplateEntity getByAppIdAndModelCode(@Param("mailTemplateAppId") int mailTemplateAppId,
            @Param("modelId") int modelId);

    /**
     * 根据站点id得到邮箱模板列表
     * 
     * @param mailTemplateAppId 站点id
     * @return 返回MailTemplateEntity列表
     */
    List<MailTemplateEntity> queryAllByAppId(@Param("mailTemplateAppId") int mailTemplateAppId);
}
