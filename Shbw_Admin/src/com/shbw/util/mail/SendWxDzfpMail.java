package com.shbw.util.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
     
/**    
 * 使用Javamail实现邮件发送功能    
 */     
public class SendWxDzfpMail {
	
	protected static final Logger log = LoggerFactory.getLogger(SendWxDzfpMail.class);
	
    public SendWxDzfpMail() {      
    }
    
    public static void main(String[] args) {
    	byte[] b=null;
    	SendWxDzfpMail.send("974077198@qq.com", "你好！", "测试邮件内容 \n 测试了！", b);
	}
    
    /**
     * 发送邮件
     * @param sjr 收件人
     * @param title 标题
     * @param content 内容
     * @param fileAttachments 附件，已逗号分隔（字节数组文件）
     */
    public static String  send(String sjr, String title, String content, byte[] fileAttachments){      
        // 初始化信息      
        String sender = "18121296517@163.com";
        String password = "qwer1234";
        String smtpServer = "smtp.163.com";
        // 配置服务器属性
        Properties proper = new Properties();
        proper.put("mail.smtp.host", smtpServer); // smtp服务器
        proper.put("mail.smtp.auth", "true"); // 是否smtp认证
        proper.put("mail.smtp.port", "25"); // 设置smtp端口
        proper.put("mail.transport.protocol", "smtp"); // 发邮件协议
        proper.put("mail.store.protocol", "pop3"); // 收邮件协议
        // 配置邮件接收地址
        InternetAddress[] receiveAddress = new InternetAddress[1];      
        try {
        	//收件人
            receiveAddress[0] = new InternetAddress(sjr);
        } catch (AddressException e) {
        	log.error("错误的邮件地址：{}", sjr);
        	log.error("邮件地址解析失败！请检查邮件地址！", e);
        }
        // smtp认证，获取Session      
        SmtpAuth sa = new SmtpAuth();      
        sa.setUserinfo(sender, password);      
        Session session = Session.getInstance(proper, sa);
        session.setPasswordAuthentication(new URLName(smtpServer), sa.getPasswordAuthentication());
        // 构建邮件体      
        MimeMessage sendMess = new MimeMessage(session);
        MimeBodyPart mbp = new MimeBodyPart();
        MimeMultipart mmp = new MimeMultipart();
        try {
            // 邮件文本内容
            mbp.setContent(content, "text/plain; charset=GBK");
            mmp.addBodyPart(mbp);
            // 附件处理      
            if(fileAttachments != null && fileAttachments.length > 0)
            {
        		DataSource source = (new ByteArrayDataSource(fileAttachments,"application/octet-stream"));
        		String name = source.getName();
        		mbp = new MimeBodyPart();
        		mbp.setDataHandler(new DataHandler(source));
        		mbp.setFileName(name);
        		mmp.addBodyPart(mbp);
        		log.info("已添加附件：{}");
            	
            	/*String[] filenames = fileAttachments.split(",");
            	for(String fname:filenames)
            	{
            		DataSource source = new FileDataSource(fname);
            		String name = source.getName();
            		mbp = new MimeBodyPart();
            		mbp.setDataHandler(new DataHandler(source));
            		mbp.setFileName(name);
            		mmp.addBodyPart(mbp);
            		log.info("已添加附件：{}", fname);
            	}*/
            }
            // 邮件整体      
            sendMess.setSubject(title);
            sendMess.setContent(mmp);
            // 发送邮件
            sendMess.setFrom(new InternetAddress(sender));
            sendMess.setRecipients(Message.RecipientType.TO, receiveAddress);
            
            Transport.send(sendMess);
            log.info("发送成功至{}", sjr);
            return "0";
        } catch (MessagingException ex) {
            log.info("发送失败", ex);
            return "-1";
        }      
    }      
}