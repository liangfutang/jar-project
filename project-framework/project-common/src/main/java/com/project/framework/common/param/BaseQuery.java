package com.project.framework.common.param;

import com.project.framework.common.result.PageParam;
import lombok.Data;

/**
 * @author tlf
 * 接口入参基础查询条件
 */
@Data
public abstract class BaseQuery extends PageParam {

    public BaseQuery() {}

    public BaseQuery(Integer pageNo, Integer pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询截至时间
     */
    private String endTime;

}
