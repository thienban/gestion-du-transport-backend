package gdp.api.services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;

@Service
public class MailJetService {
	private MailjetClient mailJetClient;

	public MailJetService() throws JSONException, MailjetException, MailjetSocketTimeoutException {
		mailJetClient = new MailjetClient(System.getenv("MAILJET_KEY"), System.getenv("MAILJET_SECRET"),
				new ClientOptions("v3.1"));
	}

	public MailjetResponse sendCancellationEmailTo(String toEmail) throws JSONException {
		MailjetRequest request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject()
						.put(Emailv31.Message.FROM,
								new JSONObject().put("Email", "alexandre.behaghel@club-internet.fr").put("Name",
										"Mailjet Pilot"))
						.put(Emailv31.Message.TO,
								new JSONArray().put(new JSONObject().put("Email", toEmail).put("Name", "passenger 1")))
						.put(Emailv31.Message.SUBJECT, "Your email flight plan!")
						.put(Emailv31.Message.TEXTPART,
								"Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
						.put(Emailv31.Message.HTMLPART,
								"<h3>Dear passenger 1, welcome to Mailjet!</h3><br />May the delivery force be with you!")));
		MailjetResponse response;
		try {
			response = getMailJetClient().post(request);
			System.out.println(response.getStatus());
			System.out.println(response.getData());
			return response;
		} catch (MailjetException | MailjetSocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	private MailjetClient getMailJetClient() {
		System.out.println(System.getenv("MAILJET_KEY"));
		if (mailJetClient == null) {
			mailJetClient = new MailjetClient(System.getenv("MAILJET_KEY"), System.getenv("MAILJET_SECRET"),
					new ClientOptions("v3.1"));
		}
		return mailJetClient;
	}
}
