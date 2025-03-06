package com.lz.manage.model.enums;

import com.lz.common.utils.StringUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Project: config
 * Description: CReturnOrderStatusEnum
 * 退货订单状态枚举
 * Version: 1.0
 */
@Getter
public enum ReturnOrderStatusEnum {

    RETURN_ORDER_STATUS_1("未退货", "1"),
    RETURN_ORDER_STATUS_2("已退货", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, ReturnOrderStatusEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (ReturnOrderStatusEnum enumValue : ReturnOrderStatusEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    ReturnOrderStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(ReturnOrderStatusEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<ReturnOrderStatusEnum> getEnumByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }

}