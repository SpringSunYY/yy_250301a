package com.lz.manage.model.enums;

import com.lz.common.utils.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Project: config
 * Description: CCommonWhetherEnum
 * 通用是否状态枚举
 * Version: 1.0
 */
public enum CommonWhetherEnum {

    COMMON_WHETHER_1("是", "1"),
    COMMON_WHETHER_2("否", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, CommonWhetherEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (CommonWhetherEnum enumValue : CommonWhetherEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    CommonWhetherEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(CommonWhetherEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<CommonWhetherEnum> getEnumByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}