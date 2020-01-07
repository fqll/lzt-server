package com.tomasky.departure.bo;


import com.tomasky.departure.model.CompanyInfo;

/**
 * 上午9:29:51
 * 
 * @version V1.0
 */
public class MimeMessageDTO {
	/** 变量名 subject: 邮件标题 */
	private String subject;

	/** 变量名 text: 邮件内容 */
	private String text;

	public MimeMessageDTO(SendEntryNoticeBo sendEntryNoticeBo, CompanyInfo companyInfo) {
		this.subject = buildSubject(companyInfo);
		this.text = buildText(sendEntryNoticeBo, companyInfo);
	}

	/**
	 * 构造邮件标题
	 * @return
	 */
	private String buildSubject(CompanyInfo companyInfo) {
		return companyInfo.getCompanyName() + " 入职通知";
	}

	/**
	 * 构造邮件内容
	 * @return
	 */
	private String buildText(SendEntryNoticeBo sendEntryNoticeBo, CompanyInfo companyInfo) {
		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append("亲爱的 ");
		textBuilder.append(sendEntryNoticeBo.getEntryEmployeeName());
		textBuilder.append(" : </br>");
		textBuilder.append("非常荣幸地通知您，您已被 ");
		textBuilder.append(companyInfo.getCompanyName());
		textBuilder.append(" 正式录用，聘于 ");
		textBuilder.append(sendEntryNoticeBo.getEntryPosition());
		textBuilder.append(" 工作。我们真诚欢迎您的加入，并诚意地提醒您根据下列陈述，前往公司报到。</br>");
		textBuilder.append("报到时间：");
		textBuilder.append(sendEntryNoticeBo.getReportDate());
		textBuilder.append("</br>");
		textBuilder.append("报到地点：");
		textBuilder.append(sendEntryNoticeBo.getReportLocation());
		textBuilder.append("</br>");
		textBuilder.append("接待人员：");
		textBuilder.append(sendEntryNoticeBo.getReceptionPersonnel());
		textBuilder.append("</br>");
		textBuilder.append("联系人电话：");
		textBuilder.append(sendEntryNoticeBo.getContactNumber());
		return textBuilder.toString();
	}

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
