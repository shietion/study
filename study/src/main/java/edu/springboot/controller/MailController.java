package edu.springboot.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.map.HashedMap;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MailController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * 
     * @throws Exception
     */
    @RequestMapping("/simplemail/send")
    public void sendSimpleMail() throws Exception {

	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom("41251794@qq.com");
	message.setTo("41251794@qq.com");
	message.setSubject("主题：简单邮件");
	message.setText("测试邮件内容");

	mailSender.send(message);
    }

    @RequestMapping("/attmail/send")
    public void sendAttachmentsMail() throws Exception {

	MimeMessage mimeMessage = mailSender.createMimeMessage();

	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	helper.setFrom("dyc87112@qq.com");
	helper.setTo("dyc87112@qq.com");
	helper.setSubject("主题：有附件");
	helper.setText("有附件的邮件");

	FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
	helper.addAttachment("附件-1.jpg", file);
	helper.addAttachment("附件-2.jpg", file);

	mailSender.send(mimeMessage);
    }

    @RequestMapping("/static/send")
    public void sendInlineMail() throws Exception {

	MimeMessage mimeMessage = mailSender.createMimeMessage();

	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	helper.setFrom("dyc87112@qq.com");
	helper.setTo("dyc87112@qq.com");
	helper.setSubject("主题：嵌入静态资源");
	helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);

	FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
	helper.addInline("weixin", file);

	mailSender.send(mimeMessage);
    }

    @RequestMapping("/template/send")
    public void sendTemplateMail() throws Exception {

	MimeMessage mimeMessage = mailSender.createMimeMessage();

	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	helper.setFrom("dyc87112@qq.com");
	helper.setTo("dyc87112@qq.com");
	helper.setSubject("主题：模板邮件");

	Map<String, Object> model = new HashMap<String, Object>();
	model.put("username", "didi");
	String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template.vm", "UTF-8", model);
	helper.setText(text, true);

	mailSender.send(mimeMessage);
    }

}
