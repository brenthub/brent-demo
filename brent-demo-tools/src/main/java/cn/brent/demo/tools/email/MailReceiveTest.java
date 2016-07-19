package cn.brent.demo.tools.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

public class MailReceiveTest {

	@Test
	public void ReceiveMailTest() throws Exception {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setHost("pop.163.com");
		mailInfo.setUsername("*****");
		mailInfo.setPassword("***");
		String[] fdess = new String[] { "D:\\image" };
		mailInfo.setFilepath(fdess);
		List<MailInfo> list = new PraseMimeMessage().receive(mailInfo);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Mailnumber=" + list.get(i).getMailnumber());
			System.out.println("Subject=" + list.get(i).getSubject());
			System.out.println("Content=" + list.get(i).getContent());
			System.out.println("Filename=" + list.get(i).getFilename());
			System.out.println("SentDate=" + list.get(i).getSentdate());
			System.out.println("Messageid=" + list.get(i).getMessageid());
			System.out.println("ExistsFile=" + list.get(i).isExistsFile());
			System.out.println("Read=" + list.get(i).isRead());
			System.out.println("Replysign=" + list.get(i).isReplysign());
			System.out.println("FromAddress=" + list.get(i).getFromAddress());
			System.out.println("ToAddress=" + list.get(i).getToAddress());
		}
	}

	public static class PraseMimeMessage {
		private MimeMessage mimeMessage = null;

		private String saveAttachPath = "d:\\";// 附件下载后的存放目录

		private StringBuffer bodytext = new StringBuffer();

		// 存放邮件内容的StringBuffer对象
		private String dateformat = "yy-MM-dd　HH:mm";// 默认的日前显示格式

		/**
		 * 构造函数,初始化一个MimeMessage对象
		 */
		public PraseMimeMessage() {
		}

		public PraseMimeMessage(MimeMessage mimeMessage) {
			this.mimeMessage = mimeMessage;
			// System.out.println("create　a　PraseMimeMessage　object........");
		}

		public void setMimeMessage(MimeMessage mimeMessage) {
			this.mimeMessage = mimeMessage;
		}

		/**
		 * 
		 * 获取发件人的姓名和密码
		 * 
		 * @return
		 */
		public String getFrom() throws Exception {
			InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
			String from = address[0].getAddress();
			if (from == null)
				from = "";
			String personal = address[0].getPersonal();
			if (personal == null)
				personal = "";
			String fromaddr = personal + "<" + from + ">";
			return fromaddr;
		}

		/**
		 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址
		 * "bcc"---密送人地址
		 */

		public String getMailAddress(String type) throws Exception {
			String mailaddr = "";
			String addtype = type.toUpperCase();
			InternetAddress[] address = null;
			if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BCC"))

			{
				if (addtype.equals("TO")) {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
				} else if (addtype.equals("CC")) {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
				} else {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
				}
				if (address != null) {
					for (int i = 0; i < address.length; i++) {
						String email = address[i].getAddress();
						if (email == null)
							email = "";
						else {
							email = MimeUtility.decodeText(email);
						}
						String personal = address[i].getPersonal();
						if (personal == null)
							personal = "";
						else {
							personal = MimeUtility.decodeText(personal);
						}
						String compositeto = personal + "<" + email + ">";
						mailaddr += "," + compositeto;
					}
					mailaddr = mailaddr.substring(1);
				}
			} else {
				throw new Exception("Error　emailaddr　type!");
			}
			return mailaddr;
		}

		/**
		 * 
		 * 获取邮件主题
		 */
		public String getSubject() throws MessagingException {
			String subject = "";
			try {
				subject = MimeUtility.decodeText(mimeMessage.getSubject());
				if (subject == null)
					subject = "";
			} catch (Exception exce) {
			}
			return subject;
		}

		/*
		 * 获取邮件发送日期
		 */

		public java.util.Date getSentDate() throws Exception {
			Date sentdate = mimeMessage.getSentDate();
			SimpleDateFormat format = new SimpleDateFormat(dateformat);
			return sentdate;
		}

		/**
		 * 获取邮件正文
		 * 
		 * @return
		 * 
		 */

		public String getBodyText() {
			return bodytext.toString();
		}

		/**
		 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
		 * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
		 * 
		 * 
		 */

		public void getMailContent(Part part) throws Exception {
			String contenttype = part.getContentType();
			int nameindex = contenttype.indexOf("name");
			boolean conname = false;
			if (nameindex != -1)
				conname = true;
			// System.out.println("CONTENTTYPE:　" + contenttype);
			if (part.isMimeType("text/plain") && !conname) {
				bodytext.append((String) part.getContent());
			} else if (part.isMimeType("text/html") && !conname) {
				bodytext.append((String) part.getContent());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i));
				}
			} else if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent());
			} else {
			}
		}

		/**
		 * 
		 * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
		 * 
		 */

		public boolean getReplySign() throws MessagingException {
			boolean replysign = false;
			String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
			if (needreply != null) {
				replysign = true;
			}
			return replysign;
		}

		/*
		 * 获得此邮件的Message-ID
		 */

		public String getMessageId() throws MessagingException {
			return mimeMessage.getMessageID();
		}

		/*
		 * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
		 */

		public boolean isNew() throws MessagingException {
			boolean isnew = false;
			Flags flags = ((Message) mimeMessage).getFlags();
			Flags.Flag[] flag = flags.getSystemFlags();
			// System.out.println("flags's　length:　" + flag.length);
			for (int i = 0; i < flag.length; i++) {
				if (flag[i] == Flags.Flag.SEEN) {
					isnew = true;
					// System.out.println("seen　Message.......");
					break;
				}
			}
			return isnew;
		}

		/**
		 * 
		 * 判断此邮件是否包含附件
		 * 
		 */

		public boolean isContainAttach(Part part) throws Exception {
			boolean attachflag = false;
			String contentType = part.getContentType();
			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null)
							&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
						attachflag = true;
					else if (mpart.isMimeType("multipart/*")) {
						attachflag = isContainAttach((Part) mpart);
					} else {
						String contype = mpart.getContentType();
						if (contype.toLowerCase().indexOf("application") != -1)
							attachflag = true;
						if (contype.toLowerCase().indexOf("name") != -1)
							attachflag = true;
					}
				}
			} else if (part.isMimeType("message/rfc822")) {
				attachflag = isContainAttach((Part) part.getContent());
			}
			return attachflag;
		}

		// base64解码
		private static String base64Decoder(String s) throws Exception {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] b = decoder.decodeBuffer(s);
			return (new String(b));
		}

		/**
		 * 【设置附件存放路径】
		 */

		public void setAttachPath(String attachpath) {
			this.saveAttachPath = attachpath;
		}

		/**
		 * 
		 * 设置日期显示本格式
		 */
		public void setDateFormat(String format) throws Exception {
			this.dateformat = format;
		}

		/**
		 * 【获得附件存放路径】
		 */

		public String getAttachPath() {
			return saveAttachPath;
		}

		/**
		 * 【真正的保存附件到指定目录里】
		 */

		private void saveFile(String fileName, InputStream in) throws Exception {

			String osName = System.getProperty("os.name");
			String storedir = getAttachPath();
			String separator = "";
			if (osName == null)
				osName = "";
			if (osName.toLowerCase().indexOf("win") != -1) {
				separator = "\\";
				if (storedir == null || storedir.equals(""))
					storedir = "c:\\tmp";
			} else {
				separator = "/";
				storedir = "/tmp";
			}
			File storefile = new File(storedir + separator + fileName);
			// System.out.println("storefile's　path:　" + storefile.toString());

			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(storefile));
				bis = new BufferedInputStream(in);
				int c;
				while ((c = bis.read()) != -1) {
					bos.write(c);
					bos.flush();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
				throw new Exception("文件保存失败!");
			} finally {
				bos.close();
				bis.close();
			}
		}

		/**
		 * PraseMimeMessage类测试
		 */

		public static void main(String args[]) throws Exception {
			String host = "POP3.163.com";// 【POP3.163.com】
			String username = "liposter";// 【yuxia2217】
			String password = "liisposter";// 【........】
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("pop3");
			store.connect(host, username, password);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();
			System.out.println("Messages's　length:　" + message.length);
			PraseMimeMessage pmm = null;
			for (int i = 0; i < message.length; i++) {
				pmm = new PraseMimeMessage((MimeMessage) message[i]);
				System.out.println("Message　" + i + "　subject:　" + pmm.getSubject());
				System.out.println("Message　" + i + "　sentdate:　" + pmm.getSentDate());
				System.out.println("Message　" + i + "　replysign:　" + pmm.getReplySign());
				System.out.println("Message　新的" + i + "　hasRead:　" + pmm.isNew());
				System.out.println("Message　附件" + i + "　　containAttachment:　" + pmm.isContainAttach((Part) message[i]));
				System.out.println("Message　" + i + "　form:　" + pmm.getFrom());
				System.out.println("Message　" + i + "　to:　" + pmm.getMailAddress("to"));
				System.out.println("Message　" + i + "　cc:　" + pmm.getMailAddress("cc"));
				System.out.println("Message　" + i + "　bcc:　" + pmm.getMailAddress("bcc"));
				pmm.setDateFormat("yy年MM月dd日　HH:mm");
				System.out.println("Message" + i + "　sentdate:　" + pmm.getSentDate());
				System.out.println("Message　" + i + "　Message-ID:　" + pmm.getMessageId());
				pmm.getMailContent((Part) message[i]);
				System.out.println("Message　正文" + i + "　bodycontent:　\r\n" + pmm.getBodyText());
				pmm.setAttachPath("D:\\image");

			}
		}

		public List<MailInfo> receive(MailInfo mailInfo) throws Exception {
			List<MailInfo> list = new ArrayList<MailInfo>();
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("pop3");
			store.connect(mailInfo.getHost(), mailInfo.getUsername(), mailInfo.getPassword());
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.getMessages();
			// System.out.println("Messages's　length:　" + message.length);
			PraseMimeMessage pmm = null;
			for (int i = 0; i < message.length; i++) {
				try {
					MailInfo mail = new MailInfo();
					mail.setMailnumber(message.length);
					pmm = new PraseMimeMessage((MimeMessage) message[i]);
					mail.setSubject(message[i].getSubject());
					mail.setSentdate(pmm.getSentDate());
					mail.setReplysign(pmm.getReplySign());
					mail.setExistsFile(pmm.isContainAttach((Part) message[i]));
					mail.setFromAddress(pmm.getFrom().substring(1, pmm.getFrom().length()).replaceAll(">", ""));
					mail.setToAddress(pmm.getMailAddress("to").substring(1, pmm.getMailAddress("to").length())
							.replaceAll(">", ""));
					pmm.setDateFormat("yy年MM月dd日　HH:mm");
					mail.setMessageid(pmm.getMessageId());
					pmm.getMailContent((Part) message[i]);
					mail.setContent(pmm.getBodyText());
					pmm.setAttachPath(mailInfo.getFilepath()[0]);
					list.add(mail);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
	}

	public static class MailInfo implements java.io.Serializable {

		private String host;// 发送邮件的主机号
		private String port;// 发送邮件的端口号
		private String fromAddress;// 发送邮件地址
		private String toAddress;// 接收邮件地址
		private String username;// 发送邮件用户的用户名
		private String password;// 发送邮件用户的密码
		private String validate;// 是否需要身份的验证:ture认证，false不认证信息
		private String subject;// 邮件主题
		private String content;// 邮件的内容,分为文本，超文本，html等形式
		private String[] filepath;// 发送邮件的附件的位置
		private String[] sendAddress;// 接收邮件地址
		private String filename;// 发送邮件的附件显示的名称
		private String[] receivepath;// 接收邮件的时候附件保存的路径
		private java.util.Date sentdate;
		private java.util.Date receivedate;
		private String mailauthor;// 邮件作者
		private boolean read;// 是否读过，true读过，false没有阅读过
		private boolean ExistsFile;// 是否有附件true有，false没有
		private boolean replysign;// 是否有回执
		private String messageid;// 消息ID
		private int mailnumber;// 邮件数目，一个用户的总邮件数

		public String[] getSendAddress() {
			return sendAddress;
		}

		public void setSendAddress(String[] sendAddress) {
			this.sendAddress = sendAddress;
		}

		public int getMailnumber() {
			return mailnumber;
		}

		public void setMailnumber(int mailnumber) {
			this.mailnumber = mailnumber;
		}

		public String getMessageid() {
			return messageid;
		}

		public void setMessageid(String messageid) {
			this.messageid = messageid;
		}

		public boolean isExistsFile() {
			return ExistsFile;
		}

		public boolean isRead() {
			return read;
		}

		public void setExistsFile(boolean existsFile) {
			ExistsFile = existsFile;
		}

		public void setRead(boolean read) {
			this.read = read;
		}

		public boolean isReplysign() {
			return replysign;
		}

		public void setReplysign(boolean replysign) {
			this.replysign = replysign;
		}

		public String getMailauthor() {
			return mailauthor;
		}

		public void setMailauthor(String mailauthor) {
			this.mailauthor = mailauthor;
		}

		public java.util.Date getReceivedate() {
			return receivedate;
		}

		public void setReceivedate(java.util.Date receivedate) {
			this.receivedate = receivedate;
		}

		public java.util.Date getSentdate() {
			return sentdate;
		}

		public void setSentdate(java.util.Date sentdate) {
			this.sentdate = sentdate;
		}

		public String[] getFilepath() {
			return filepath;
		}

		public void setFilepath(String[] filepath) {
			this.filepath = filepath;
		}

		public String getContent() {
			return content;
		}

		public String getFilename() {
			return filename;
		}

		public String getFromAddress() {
			return fromAddress;
		}

		public String getHost() {
			return host;
		}

		public String getPassword() {
			return password;
		}

		public String getPort() {
			return port;
		}

		public String getSubject() {
			return subject;
		}

		public String getToAddress() {
			return toAddress;
		}

		public String getUsername() {
			return username;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public void setFromAddress(String fromAddress) {
			this.fromAddress = fromAddress;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public void setToAddress(String toAddress) {
			this.toAddress = toAddress;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getValidate() {
			return validate;
		}

		public void setValidate(String validate) {
			this.validate = validate;
		}

		public String[] getReceivepath() {
			return receivepath;
		}

		public void setReceivepath(String[] receivepath) {
			this.receivepath = receivepath;
		}
	}
}
