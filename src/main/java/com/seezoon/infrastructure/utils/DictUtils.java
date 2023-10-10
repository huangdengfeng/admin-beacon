package com.seezoon.infrastructure.utils;

import com.seezoon.infrastructure.configuration.context.SpringContextHolder;
import com.seezoon.infrastructure.configuration.properties.AppProperties;
import com.seezoon.infrastructure.configuration.properties.DictProperties;
import com.seezoon.infrastructure.constants.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * @author dfenghuang
 * @date 2023/9/21 09:22
 */
public class DictUtils {

    private static final Map<String, String> cache = new HashMap<>();

    static {
        Map<String, List<DictProperties>> dicts = SpringContextHolder.getBean(AppProperties.class).getDicts();
        for (Entry<String, List<DictProperties>> entry : dicts.entrySet()) {
            String key = entry.getKey();
            List<DictProperties> value = entry.getValue();
            if (CollectionUtils.isEmpty(value)) {
                continue;
            }
            for (DictProperties dictProperties : value) {
                cache.put(concat(key, dictProperties.getValue()), dictProperties.getName());
            }
        }
    }

    public static String getName(String type, Object value) {
        Assert.hasText(type, "dict type must not empty");
        if (null == value) {
            return null;
        }
        return cache.get(concat(type, value));
    }

    private static String concat(String type, Object value) {
        return type + Constants.UNDERLINE + value;
    }
}
