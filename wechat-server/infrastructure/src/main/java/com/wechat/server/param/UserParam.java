package com.wechat.server.param;

import com.project.framework.common.param.BaseParam;
import lombok.Data;

@Data
public class UserParam extends BaseParam {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * '姓名'
     */
    private String name;
}
