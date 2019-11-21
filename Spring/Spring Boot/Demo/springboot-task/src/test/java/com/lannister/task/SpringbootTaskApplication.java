package com.lannister.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by Lannister on 2019/11/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTaskApplication {

  @Autowired
  JavaMailSenderImpl mailSender;

  @Test
  public void sendMail(){
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setSubject("通知-今晚开会");
    mailMessage.setText("今晚7:30开会");
    mailMessage.setFrom("18710893694@163.com");
    mailMessage.setTo("1579624684@qq.com");
    mailSender.send(mailMessage);
  }

  @Test
  public void sendMail02() throws MessagingException {
    //1、创建一个复杂的消息邮件
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

    //2、邮件设置
    helper.setSubject("通知-今晚开会");
    helper.setText("<b style='color:red'>今晚7:30开会</b>",true);
    helper.setFrom("18710893694@163.com");
    helper.setTo("1579624684@qq.com");
    //上传文件
    helper.addAttachment("1.jpg", new File("C:\\Users\\hp\\Pictures\\Saved Pictures\\1.jpg"));
    helper.addAttachment("2.jpg", new File("C:\\Users\\hp\\Pictures\\Saved Pictures\\2.jpg"));
    mailSender.send(mimeMessage);
  }

}
