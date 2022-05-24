package com.lhr.milk.user.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.template.TemplateUtil;
import com.lhr.milk.user.service.EmailService;
import com.lhr.milk.user.utils.EmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.Template;
import java.util.Collections;

/**
 * @author lhr
 * @Date:2022/5/22 13:40
 * @Version 1.0
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.email}")
    private String email;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Override
    public boolean send(String email, String code) {

        //判断邮箱是否为空
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        // 调用服务直接发送邮件
        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template",
                TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email-code.ftl");

        // 发送验证码
        EmailSend(new EmailDto(Collections.singletonList(email),
                "邮箱验证码", template.render(Dict.create().set("code", code))));

        return true;
    }

    private void EmailSend(EmailDto emailDto) {
        //判空
        if (email == null || host == null || port == null || username == null || password == null) {
            throw new RuntimeException("邮箱配置异常");
        }
        // 设置
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(Integer.parseInt(port));
        // 设置发送人邮箱
        account.setFrom(username + "<" + email + ">");
        // 设置发送人名称
        account.setUser(username);
        // 设置发送授权码
        account.setPass(password);
        account.setAuth(true);
        // ssl方式发送
        account.setSslEnable(true);
        // 使用安全连接
        account.setStarttlsEnable(true);

        // 发送邮件
        try {
            int size = emailDto.getTos().size();
            Mail.create(account)
                    .setTos(emailDto.getTos().toArray(new String[size]))
                    .setTitle(emailDto.getSubject())
                    .setContent(emailDto.getContent())
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
