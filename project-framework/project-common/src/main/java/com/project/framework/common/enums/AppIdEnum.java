package com.project.framework.common.enums;

import com.project.framework.common.exception.ServiceException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 请求来源枚举值
 */
public enum AppIdEnum {

    ADMIN(0, "后台管理系统"),
    UNIAPP(1, "移动端")
    ;
    @Getter
    private Integer value;
    @Getter
    private String name;
    AppIdEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据终端值获取对应的终端名称
     * @param value 终端appid值
     * @return 终端站点名
     */
    public static String nameByValue(Integer value) {
        return Arrays.stream(AppIdEnum.values()).filter(one -> Objects.equals(value, one.getValue())).findFirst()
                .map(AppIdEnum::getName).orElseGet(() -> "");
    }

    /**
     * 校验终端站点枚举值的有效性
     * @param value 终端appid值
     */
    public static void validValue(Integer value) {
        if (Arrays.stream(AppIdEnum.values()).noneMatch(one -> Objects.equals(value, one.getValue()))) {
            throw new ServiceException("无效站点");
        }
    }
}
