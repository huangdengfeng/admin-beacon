package com.seezoon.domain.service.sys.authentication.valueobj;

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * token 业务字段
 *
 * @author huangdengfeng
 * @date 2023/9/11 23:11
 */
@Getter
public class TokenInfoVO {

    /**
     * 主题字段，业务关键信息
     */
    private String subject;
    /**
     * 用于安全控制，可空
     */
    private String tokenId;
    /**
     * 验证码，因为jwt 内容没有加密，如果泄露的服务器签名密钥，可以任意伪造，该字段提高伪造难度
     */
    private String checkSum;

    public TokenInfoVO(String subject, String tokenId, String checkSum) {
        Assert.hasText(subject, "subject must not empty");
        Assert.hasText(checkSum, "checkSum must not empty");
        this.subject = subject;
        this.tokenId = tokenId;
        this.checkSum = checkSum;
    }
}
