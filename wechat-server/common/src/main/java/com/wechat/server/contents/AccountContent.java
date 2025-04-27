package com.wechat.server.contents;

import java.util.Arrays;
import java.util.List;

public interface AccountContent {

    /**
     * 刷新token需要的延长时长
     */
    Long REFRESH_MORE_TIME = 24*60*60*1000L;
    /**
     * 不需要鉴权的路由
     */
    List<String> SKIP_AUTH_URI = Arrays.asList(
            "/edu-platform/login/simple",
            ""
    );

    String REDIS_TOKEN_KEY = "auth:%d:%d:token";
    String REDIS_REFRESH_TOKEN_KEY = "auth:%d:%d:refresh-token";
}
