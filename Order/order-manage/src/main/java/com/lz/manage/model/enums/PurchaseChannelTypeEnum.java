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
 * Description: PurchaseChannelTypeEnum
 * 采购渠道类型枚举
 * Version: 1.0
 */
public enum PurchaseChannelTypeEnum {

    PURCHASE_CHANNEL_TYPE_0("其他", "0"),
    PURCHASE_CHANNEL_TYPE_1("线上", "1"),
    PURCHASE_CHANNEL_TYPE_2("线下", "2");

    private final String text; // 字典描述（label）
    private final String value; // 字典值

    private static final Map<String, PurchaseChannelTypeEnum> VALUE_TO_ENUM = new ConcurrentHashMap<>();

    static {
        for (PurchaseChannelTypeEnum enumValue : PurchaseChannelTypeEnum.values()) {
            VALUE_TO_ENUM.put(enumValue.value, enumValue);
        }
    }

    PurchaseChannelTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(PurchaseChannelTypeEnum::getValue)
                .collect(Collectors.toList());
    }

    public static Optional<PurchaseChannelTypeEnum> getEnumByValue(String value) {
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