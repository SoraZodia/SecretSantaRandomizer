package sorazodia.random;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sorazodia.main.ListSystemContorl;
import sorazodia.parser.FileIO;

/**
 * Takes, randomize, and output an list of Strings
 * @author SoraZodia
 */
public class Randomizer {
	
	private static Random rand = new Random();
	private static ArrayList<String> list = new ArrayList<>();
	/**
	 * Randomize {@link #list} and output the result.
	 */
	public static String getReciever(String name){
		String text = "";
		int draws = 0;
		if(list.isEmpty()) text = "There's no more people to pick";
		if(!list.isEmpty()){
			for(int x = 0; x<1; x++){
				draws = rand.nextInt(list.size());
				text = list.get(draws);
				if(text.equalsIgnoreCase(name) && list.size() != 1) x--;
			}
			list.remove(draws);
		}
		return text;
	}

	
	public static void initRandomizer(){
		rand = new Random(rand.nextLong());
		for(String str: FileIO.getList())
			if(!list.contains(str))list.add(str);
	}
	
	private static String getCarrierMail(String code) {
		switch(code) {
		case "tmobile":
			return "@tmomail.net";
		case "att":
			return "@txt.att.net";
		case "verizon":
			return "@vtext.com";
		case "sprint":
			return "@messaging.sprintpcs.com";
		case "boost":
			return "@sms.myboostmobile.com";
		default:
			return "@tmomail.net";
		}
	}
	
	/**
	 * Send text message via email
	 */
	public static void sendEmail(String senderPhone, String receiver, String carrierCode)
	{
		// The sender's email, for this, an dummy email was used
        final String username = ListSystemContorl.getEmail(); 
        // The password to log into the above email address
        final String password = ListSystemContorl.getPass();
        // The reciever's email, in this case, it's the SMS gateway of the target's phone
        final String to = senderPhone.replaceAll("[\\(\\)-]", "") + getCarrierMail(carrierCode); 
        // Tells Java what kind of protocol it will be using when it is connected to the gateway
        // along with what server at which port that it will be using
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Creates an Session that would maintain an connection to the Google's mail server
        // this is where Java would log in and connect to their account on their behalf
        // two factor authentication needs to be disabled for this method to work properly
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        // Creates an small email to be send to the target's phone
        // the resulting message's body would look like "Wake Up Call / Yo, wake up"
        // following the format "<Subject> / <Massage>"
        try {

        	System.out.print("Sending message... ");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Secert Santa");
            message.setText("Gifting to " + receiver);

            Transport.send(message);

            System.out.println("Sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
	
	/**
	 * Nuke and remove all entries in {@link #list}
	 */
	public static void clear(){
		list.clear();
	}
	
	/**
	 * Checks if {@link #list} has any more elements in it
	 */
	public static boolean isListEmpty(){
		return list.isEmpty();
	}
	
}
