package com.lhr.milk.user.service;

/**
 * @author lhr
 * @Date:2022/5/22 13:40
 * @Version 1.0
 */
public interface EmailService {
    boolean send(String email, String code);
}
