package com.mertdev.therawdata.bussines.concretes;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.MailService;
import com.mertdev.therawdata.bussines.requests.EmailVerficationRequest;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service

public class MailServiceImpl implements MailService {

	private static final int CODE_LENGTH = 6; // Doğrulama kodu uzunluğu
	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Override
	public void sendVerificationCode(User user, String email, String verificationCode) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(fromMail);
		helper.setTo(email);
		helper.setSubject("Email Verification Code");


		String htmlContent = "<html><body>" + "<p>Hello [Recipient Name],</p>"
				+ "<p>To ensure the security of your account, we have sent a verification code.</p>"
				+ "<p>Please use the code below to verify your account:</p>" + "<p><strong>Verification Code:</strong> "
				+ verificationCode + "</p>"
				+ "<p>If you did not request this verification code, please disregard this email. The security of your account is important to us.</p>"
				+ "<p>Thank you,<br/>The RawData Library Support Team</p>" + "</body></html>";
		helper.setText(htmlContent, true); 

		javaMailSender.send(message);
	}

	@Override
	public void sendForgetPasswordCode(String email, String forgetPasswordCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromMail);
		message.setTo(email);
		message.setSubject("Forget Password Code");
		message.setText("Hello,\n\nYour forget password code is: " + forgetPasswordCode);

		javaMailSender.send(message);
	}

	@Override
	public String generateVerificationCode() {
		SecureRandom random = new SecureRandom();
		StringBuilder code = new StringBuilder(CODE_LENGTH);

		for (int i = 0; i < CODE_LENGTH; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			code.append(CHARACTERS.charAt(randomIndex));
		}

		return code.toString();
	}

	@Override
	public boolean userEmailVerfication(EmailVerficationRequest emailVerficationRequest) {
		try {
			User user = userRepository.findByEmail(emailVerficationRequest.getEmail());

			if (user.getEmailVerfication().equals(emailVerficationRequest.getCode())) {

				user.setEmailVerficationStatus(true);
				userRepository.save(user);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void sendInvite(String email, String firstname, String lastname) {
	    try {
	        MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setFrom(fromMail);
	        helper.setTo(email);
	        helper.setSubject("Monetize Your Research: The Raw Data Library Invitation");

	        String htmlContent = "<html lang=\"en\">" +
	                "<head>" +
	                "<meta charset=\"UTF-8\">" +
	                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
	                "<title>Raw Data Library Message</title>" +
	                "<style>" +
	                "body {" +
	                "font-family: 'Arial', sans-serif;" +
	                "margin: 20px;" +
	                "line-height: 1.6;" +
	                "color: #333;" +
	                "}" +
	                "p {" +
	                "margin-bottom: 10px;" +
	                "}" +
	                "strong {" +
	                "font-weight: bold;" +
	                "}" +
	                "em {" +
	                "font-style: italic;" +
	                "color: #007BFF;" +
	                "}" +
	                "a {" +
	                "color: #007BFF;" +
	                "text-decoration: none;" +
	                "}" +
	                "a:hover {" +
	                "text-decoration: underline;" +
	                "}" +
	                ".container {" +
	                "max-width: 600px;" +
	                "}" +
	                ".signature {" +
	                "margin-top: 20px;" +
	                "}" +
	                "</style>" +
	                "</head>" +
	                "<body>" +
	                "<div class=\"container\">" +
	                "<p><strong>Dear <em>%s %s</em>,</strong></p>".formatted(firstname, lastname) +
	                "<p>We are writing to you regarding your <strong>previously published papers</strong>.</p>" +
	                "<p>As you are aware, huge efforts are made by researchers to obtain <strong> experimental raw data </strong> , develop <strong>  analytical models </strong> using different software programs and conduct <strong>questionary surveys.</strong> </p>" +
	                "<p>Obtained raw data are needed to document for use by others in order to encourage data owners to conduct additional research and increase the number of citations.</p>" +
	                "<p>In case you upload your raw data to our site, <strong>it will be purchased by other </strong> researchers.</p>"+
	                "<p><strong>Researchers</strong> who upload their <strong> Raw Data </strong>to the system are paid according to the number of downloads of their data through the Raw Data Library company.</p>" +
	                "<p>The download of each raw data is <strong> subject to a fee.</strong> The data download fee for 2024 is set at 50$. The prices of company test data or computer modellings may vary.</p>" +
	                "<p><strong>70% of the revenue </strong>generated from downloading your raw data by other researchers is paid equally to the authors. Authors are paid for each new download.</strong/></p>"+
	                "<p>Payments to authors are paid equally to authors after their study reaches each new 5 downloads.</p>"+
	                "<p>After the number of downloads for the relevant raw data reaches 5, the technical team contacts the authors to process the payments.</p>"+
	                "<p><strong>Authors can also track the number of times their data has been downloaded from their own profile page.</strong></p>"+
	                "<p>Co-authors can upload the same article to their own profile pages. Intermediate payments are made to the co-authors for the data purchased from each profile.</p>"+
					"<p>After the interim payments for the relevant data are made, the payment information is uploaded to the profile page of each author by technical team. Thus, if any of the co-authors has not received the possible interim payments for various reasons, the payment is made again for the relevant co-author.</p>"+
	                "<p>If any of your articles <strong>have not been published</strong> anywhere, you can also upload the full text of the relevant article “such as conference paper or your own studies” to the system separately along with the raw data.</p>"+
	                "<p>If your article <strong>has been published</strong> somewhere and if it is not an open access, you can upload only the raw data of the relevant article to the system with the title of the published paper. </p>"+
	                "<p class=\"signature\"><strong>Sincerely,<br>Raw Data Library<br><a href=\"http://www.rawdatalibrary.net\" target=\"_blank\">www.rawdatalibrary.net</a></strong></p>" +
	                "</div>" +
	                "</body>" +
	                "</html>";
	                

	        helper.setText(htmlContent, true);

	        javaMailSender.send(message);
	    } catch (Exception e) {
	        // Log or handle the exception as needed
	        e.printStackTrace();
	    }
	}


}
