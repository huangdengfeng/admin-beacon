package com.seezoon.infrastructure.configuration;

import java.beans.PropertyEditorSupport;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * form 特殊字符处理
 */
@ControllerAdvice
public class WebParamBinder {

    /**
     * trim text param and escapeHtml4 for xss attack
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
        });
    }
}
