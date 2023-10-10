package com.seezoon.application.sys.executor;

import com.seezoon.application.sys.dto.clientobject.DictCO;
import com.seezoon.application.sys.dto.clientobject.ParamCO;
import com.seezoon.infrastructure.configuration.properties.AppProperties;
import com.seezoon.infrastructure.configuration.properties.DictProperties;
import com.seezoon.infrastructure.dto.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 参数查询
 *
 * @author dfenghuang
 * @date 2023/9/19 09:25
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class ParamQryExe {

    private final AppProperties appProperties;

    public Response<ParamCO> execute() {
        ParamCO co = new ParamCO();
        Map<String, List<DictCO>> dictMap = new HashMap<>();
        Map<String, List<DictProperties>> dictProperties = appProperties.getDicts();
        for (Entry<String, List<DictProperties>> entry : dictProperties.entrySet()) {
            List<DictCO> dicts = entry.getValue().stream()
                    .map(v -> new DictCO(v.getValue(), v.getName(), v.isDisabled())).collect(Collectors.toList());
            dictMap.put(entry.getKey(), dicts);
        }
        co.setDicts(dictMap);
        return Response.success(co);
    }
}
