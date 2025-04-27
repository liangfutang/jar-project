package com.projects.common.properties;

import cn.hutool.core.collection.CollectionUtil;
import com.projects.common.constants.RedisConstant;
import com.projects.common.utils.ParamUtil;
import com.projects.common.utils.ParamValidateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class Sentinel {
    /**
     * 可不带客户端信息，默认添加一个client(db：0)
     */
    private List<Client> clients;
    private Connect connect;

    @Setter
    @Getter
    public static class Client extends ClientBase {

    }

    @Setter @Getter
    public static class Connect extends ConnectBase {

    }

    /**
     * 校验客户端参数，并为相关字段提供默认值
     */
    public void checkAndFillClients() {
        // 如果用户没传client，则默认添加一个client
        if (CollectionUtil.isEmpty(clients)) {
            clients = new ArrayList<>();
            Client c0 = new Client();
            c0.setDatabase(0);
            c0.setBeanName(RedisConstant.CLIENT_BEAN_NAME + 0);
            clients.add(c0);
        }
        if (clients.size() == 1 && clients.get(0).getDatabase() == null) {
            clients.get(0).setDatabase(0);
        }
        ParamValidateUtil.notNull(clients, Client::getDatabase, "sentinel.clients.database 不能为空");
        // 填充默认客户端名
        clients.forEach(c -> {
            if (StringUtils.isBlank(c.getBeanName())) {
                c.setBeanName(RedisConstant.CLIENT_BEAN_NAME + c.getDatabase());
            }
        });
        // 校验db和客户端名是否重复
        ParamUtil.checkRepeat(clients, Client::getDatabase, "sentinel.clients.database 不能重复");
        ParamUtil.checkRepeat(clients, Client::getBeanName, "sentinel.clients.beanName 不能重复");
    }

    /**
     * 校验连接参数，并为相关字段提供默认值
     */
    public void checkAndFillConnect() {
        // 校验连接信息的配置
        ParamValidateUtil.notNull(connect, "sentinel.redis缺少连接信息");
        ParamValidateUtil.notBlank(connect.getHost(), "sentinel.redis连接缺少host");
        ParamValidateUtil.notNull(connect.getPort(), "sentinel.redis连接缺少port");
        ParamValidateUtil.notBlank(connect.getPassword(), "sentinel.redis连接缺少Password");
    }
}
