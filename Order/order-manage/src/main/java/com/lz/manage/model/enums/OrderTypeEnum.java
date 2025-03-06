package com.lz.manage.model.enums;

import com.lz.common.utils.StringUtils;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Project: config
 * Description: OrderTypeEnum
 * 订单类型枚举
 * Version: 1.0
 */
@Getter
public enum OrderTypeEnum {

    ORDER_TYPE_1("线上", "1"),
    ORDER_TYPE_2("线下", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, OrderTypeEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (OrderTypeEnum enumValue : OrderTypeEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    OrderTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(OrderTypeEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<OrderTypeEnum> getEnumByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }

}