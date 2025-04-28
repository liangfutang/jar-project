package com.project.framework.common.param;

import com.project.framework.common.result.PageParam;
import lombok.Data;

/**
 * @author tlf
 * 查询数据库基础条件
 */
@Data
public abstract class BaseParam extends PageParam {

    public BaseParam() {}

    public BaseParam(Boolean needPage, Integer pageNo, Integer pageSize) {
        this.needPage = needPage;
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    /**
     * 当前查询条件中是否需要使用limit分页查找
     */
    private Boolean needPage;

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     * 查询截至时间
     */
    private String endTime;
}
