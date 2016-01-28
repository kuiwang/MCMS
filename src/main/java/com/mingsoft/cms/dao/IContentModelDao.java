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

package com.mingsoft.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.base.entity.BaseEntity;

/**
 * 
 * 
 * <p>
 * <b>铭飞CMS-铭飞内容管理系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author 姓名 史爱华
 * 
 * @version 300-001-001
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 * 
 *          <p>
 *          Comments:字段管理持久化层 || 继承IBaseDao持久化层
 *          </p>
 * 
 *          <p>
 *          Create Date:2014-9-11
 *          </p>
 *
 *          <p>
 *          Modification history:
 *          </p>
 */
public interface IContentModelDao extends IBaseDao {

    /**
     * 根据管理员Id查找内容模型的总数
     * 
     * @param cmManagerId 管理员id
     * @return 内容模型总数
     */
    public int getContentModelByManagerId(int cmManagerId);

    /**
     * 根据内容模型的表名查找实体
     * 
     * @param cmTableName 内容模型表名
     */
    public BaseEntity getContentModelByTableName(String cmTableName);

    /**
     * 根据管理员id查找内容模型实体
     * 
     * @param cmManagerId 管理员id
     * @return 内容模型列表集合
     */
    public List<BaseEntity> queryByManagerId(int cmManagerId);

    /**
     * 分页查询指定管理员的表单内容模型
     * 
     * @param entity 实体
     * @param page Map对象
     * @param order 排序方式,true:asc;fales:desc
     * @param cmManagerId 管理员id
     * @return 内容模型列表集合
     */
    List<BaseEntity> queryPageByManagerId(@Param("pageNo") int pageNo,
            @Param("pageSize") int pageSize, @Param("orderBy") String orderBy,
            @Param("order") boolean order, @Param("cmManagerId") int cmManagerId);
}
