package com.tomasky.departure.helper;

import com.tomasky.departure.bo.MimeMessageDTO;
import com.tomasky.departure.bo.SendEntryMailBo;
import com.tomasky.departure.enums.MailTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sam on 2020-01-08.09:31
 */
@Component
public class SendMailHelper {

    private static Logger logger = LoggerFactory.getLogger(SendMailHelper.class);

    /**
     * 发送邮件
     *
     * @param sendEntryMailBo
     * @return
     */
    public boolean sendEmail(SendEntryMailBo sendEntryMailBo) {
        try {
            // 创建Session实例对象
            Session session = createSession(sendEntryMailBo);
            // 创建MimeMessage实例对象
            MimeMessage message = createMessage(session, sendEntryMailBo);
            // 发送邮件
            logger.info("发送邮件中......");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件异常==》");
            e.printStackTrace();
            return false;
        } finally {
            logger.info("邮件发送结束...");
        }
    }

    /**
     * 测试登录
     *
     * @param sendEntryMailBo
     * @return
     */
    public boolean authLogin(SendEntryMailBo sendEntryMailBo) {
        Transport ts = null;
        try {
            String userName = sendEntryMailBo.getFrom();
            String password = sendEntryMailBo.getPassword();
            String mailType = sendEntryMailBo.getMailType();
            String hostname = SMTPUtil.getSMTPAddress(userName, mailType);
            Session session = createSession(sendEntryMailBo);

            ts = session.getTransport();
            ts.connect(hostname, userName, password);
            return true;
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        } finally {
            try {
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建与邮件服务器的会话对象
     *
     * @return 服务器的会话对象
     */
    private Session createSession(SendEntryMailBo sendEntryMailBo) {
        String userName = sendEntryMailBo.getFrom();
        String password = sendEntryMailBo.getPassword();
        String mailType = sendEntryMailBo.getMailType();
        // 封装属性参数
        Properties props = makeMailProperties(userName, mailType);
        // 获取与服务器的会话Session对象
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 登陆账号及密码，密码需要是第三方授权码
                return new PasswordAuthentication(userName, password);
            }
        });
        session.setDebug(true);
        return session;
    }

    /**
     * 创建邮件信息
     *
     * @param userName
     * @return
     */
    private Properties makeMailProperties(String userName, String mailType) {
        Properties props = new Properties();
        String hostname = SMTPUtil.getSMTPAddress(userName, mailType);
        if (MailTypeEnum.TENCENT_CORPORATE_EMAIL.getValue().equals(mailType)) {
            props.put("mail.smtp.host", "smtp.exmail.qq.com");
        } else {
            props.put("mail.smtp.host", hostname);
        }
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        if (hostname.indexOf(".qq.com") != -1 || MailTypeEnum.TENCENT_CORPORATE_EMAIL.getValue().equals(mailType)) {
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        return props;
    }

    /**
     * 创建邮件的核心内容
     *
     * @param session
     * @param sendEntryMailBo
     * @return
     * @throws Exception
     */
    public static MimeMessage createMessage(Session session, SendEntryMailBo sendEntryMailBo) throws Exception {
        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(sendEntryMailBo.getFrom()));
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, sendEntryMailBo.getTo());
        // 设置发送日期
        message.setSentDate(new Date());
        MimeMessageDTO mimeMessageDTO = sendEntryMailBo.getMessage();
        // 设置邮件主题
        message.setSubject(mimeMessageDTO.getSubject());

        /* 创建用于组合邮件正文和附件的MimeMultipart对象 */
        MimeMultipart multipart = new MimeMultipart();
        List<String> filepathList = sendEntryMailBo.getFilepath();
        String filePath = filepathList.get(0);
        Map<String, String> imagesMap = new HashMap<>();
        imagesMap.put("微信扫码访问离职通", filePath);
        // 设置HTML内容
        MimeBodyPart content = createContent(mimeMessageDTO.getText(), imagesMap);
        multipart.addBodyPart(content);

        // 设置附件
        String[] attachments = {filePath};
        if (attachments != null && attachments.length != 0) {
            for (String filename : attachments) {
                MimeBodyPart attachPart = createAttachment(filename);
                multipart.addBodyPart(attachPart);
            }
        }

        // 将组合的MimeMultipart对象设置为整个邮件的内容，要注意调用saveChanges方法进行更新
        message.setContent(multipart);
        // 保存并生成最终的邮件内容
        message.saveChanges();

        return message;
    }


    public static MimeBodyPart createContent(String body, Map<String, String> imagesMap) throws Exception {

        /* 创建代表组合MIME消息的MimeMultipart对象和该对象保存到的MimeBodyPart对象 */
        MimeBodyPart content = new MimeBodyPart();

        // 创建一个MimeMultipart对象
        MimeMultipart multipart = new MimeMultipart();

        if (!body.isEmpty()) {
            // 创建一个表示HTML正文的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
            multipart.addBodyPart(htmlBodyPart);
        }

        if (imagesMap != null && !imagesMap.isEmpty()) {
            for (Map.Entry<String, String> entry : imagesMap.entrySet()) {
                /* 创建一个表示图片的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中 */
                MimeBodyPart pictureBodyPart = new MimeBodyPart();

                // FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
                FileDataSource fileDataSource = new FileDataSource(entry.getValue());

                // DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
                pictureBodyPart.setDataHandler(new DataHandler(fileDataSource));
                pictureBodyPart.setContentID(entry.getKey());

                multipart.addBodyPart(pictureBodyPart);
            }
        }

        // 将MimeMultipart对象保存到MimeBodyPart对象中
        content.setContent(multipart);

        return content;
    }

    /**
     * 创建邮件中的附件
     *
     * @param filepath 附件的路径
     * @return 生成的附件对象
     * @throws Exception
     */
    public static MimeBodyPart createAttachment(String filepath) throws Exception {
        /* 创建一个表示附件的MimeBodyPart对象，并加入附件内容以及相应的信息 */
        MimeBodyPart attachPart = new MimeBodyPart();

        // FileDataSource用于读取文件数据，并返回代表数据的输入输出流和数据的MIME类型
        FileDataSource fileDataSource = new FileDataSource(filepath);

        // DataHandler类用于封装FileDataSource对象，并为应用程序提供访问数据的接口
        attachPart.setDataHandler(new DataHandler(fileDataSource));

        // 设置附件名称,MimeUtility.encodeText可以处理乱码问题
        attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));

        return attachPart;
    }


}
