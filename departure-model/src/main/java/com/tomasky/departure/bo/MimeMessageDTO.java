package com.tomasky.departure.bo;


/**
 * 上午9:29:51
 * 
 * @version V1.0
 */
public class MimeMessageDTO {
	/**   
	 * 变量名 subject: TODO 邮件标题
	 */   
	private String subject;

	/**   
	 * 变量名 text: TODO 邮件内容
	 */   
	private String text;

	/** 
	 * 方法名: initMimeMessage 
	 * 功能描述: TODO 初始化
	 * @param: @param subject
	 * @param: @param text
	 * @param: @return  
	 * @return: MimeMessageDTO 
	 */
	public MimeMessageDTO initMimeMessage(String subject, String text) {
		return new MimeMessageDTO(subject, text);
	}
	
	public MimeMessageDTO() {
		super();
	}

	public MimeMessageDTO(String subject, String text) {
		super();
		this.subject = subject;
		this.text = text;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
