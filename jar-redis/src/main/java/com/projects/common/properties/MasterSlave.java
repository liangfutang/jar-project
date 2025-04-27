package com.projects.common.properties;

import cn.hutool.core.collection.CollectionUtil;
import com.projects.common.constants.RedisConstant;
import com.projects.common.enums.RedisRoleEnum;
import com.projects.common.utils.ParamUtil;
import com.projects.common.utils.ParamValidateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class MasterSlave {

    private List<Client> clients;
    private List<Connect> connects;

    @Setter @Getter
    public static class Client extends ClientBase {

    }

    @Setter @Getter
    public static class Connect extends ConnectBase {
        /**
         * 当前连接的redis服务节点在整个redis服务中扮演的角色信息
         * @link com.edu.framework.redis.enums.RedisRoleEnum
         */
        private String role;
    }

    /**
     * 校验客户端参数，并为相关字段填充默认值
     */
    public void checkAndFillClients() {
        // 如果用户没传client，则默认添加一个client，并填充默认值
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
        ParamValidateUtil.notNull(clients, Client::getDatabase, "masterSlave.clients.database 不能为空");
        // 填充默认客户端名
        clients.forEach(c -> {
            if (StringUtils.isBlank(c.getBeanName())) {
                c.setBeanName(RedisConstant.CLIENT_BEAN_NAME + c.getDatabase());
            }
        });
        // 校验客户端名、db 是否重复
        ParamUtil.checkRepeat(clients, Client::getBeanName, "masterSlave.clients.beanName 重复");
        ParamUtil.checkRepeat(clients, Client::getDatabase, "masterSlave.clients.database 重复");
    }

    /**
     * 校验连接参数，并为相关字段填充默认值
     */
    public void checkAndFilConnects() {
        // 校验必要参数是否为空
        ParamValidateUtil.notEmpty(connects, "masterSlave.connects 不能为空");
        ParamValidateUtil.notBlank(connects, Connect::getHost, "masterSlave.connects.host 不能为空");
        ParamValidateUtil.notNull(connects, Connect::getPort, "masterSlave.connects.port 不能为空");
        ParamValidateUtil.notBlank(connects, Connect::getRole, "masterSlave.connects.role 不能为空");
        ParamValidateUtil.notBlank(connects, Connect::getPassword, "masterSlave.connects.password 不能为空");
        // 校验role参数，只能有一个master，其他的只能是slave
        if (connects.stream().filter(c -> RedisRoleEnum.isMaster(c.getRole())).count() != 1) {
            throw new IllegalArgumentException("masterSlave.connects.role 中必须只有一个master，其他的只能是slave");
        }
        if (connects.stream().filter(c -> RedisRoleEnum.isSlave(c.getRole())).count() != connects.size()-1) {
            throw new IllegalArgumentException("masterSlave.connects.role slave节点错误");
        }

        // 非必传参数添加默认值
    }
}
