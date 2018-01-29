package gdp.api.services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

import gdp.api.entities.Annonce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mailjet.client.ClientOptions;

@Service
public class MailJetService {
	@Value("${mailjet.sender}")
	String emailSender;

	private MailjetClient mailJetClient;

	public MailJetService() throws JSONException, MailjetException, MailjetSocketTimeoutException {
		mailJetClient = new MailjetClient(System.getenv("MAILJET_KEY"), System.getenv("MAILJET_SECRET"),
				new ClientOptions("v3.1"));
	}

	MailjetRequest getEmail(String toEmail) throws JSONException {
		MailjetRequest request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject().put(Emailv31.Message.FROM, new JSONObject()
						.put("Email", "alexandre.behaghel@club-internet.fr").put("Name", "Gestion de transports"))));
		return request;
	}

	private MailjetClient getMailJetClient() {
		if (mailJetClient == null) {
			mailJetClient = new MailjetClient(System.getenv("MAILJET_KEY"), System.getenv("MAILJET_SECRET"),
					new ClientOptions("v3.1"));
		}
		return mailJetClient;
	}

	public MailjetResponse sendResaCancellationEmailTo(String email, Annonce annonce)
			throws MailjetException, MailjetSocketTimeoutException {
		StringBuilder sb = new StringBuilder("<h3>Annulation bien prise en compte</h3>");
		sb.append("<br>");
		sb.append(annonce.getAdresseDepart() + " => " + annonce.getAdresseArrive());
		sb.append("<br>");
		sb.append(annonce.getDateDepart().toString());
		MailjetRequest request;
		try {
			request = new MailjetRequest(Emailv31.resource)
					.property(Emailv31.MESSAGES,
							new JSONArray().put(new JSONObject()
									.put(Emailv31.Message.FROM,
											new JSONObject().put("Email", emailSender).put("Name",
													"Mailjet Pilot"))
									.put(Emailv31.Message.TO,
											new JSONArray().put(
													new JSONObject().put("Email", email).put("Name", "collaborateur")))
									.put(Emailv31.Message.SUBJECT, "Annulation confirmée")
									.put(Emailv31.Message.HTMLPART, sb.toString())));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return getMailJetClient().post(request);
	}

	public MailjetResponse sendPassagerCancellationEmailTo(String email, Annonce annonce)
			throws MailjetException, MailjetSocketTimeoutException {
		MailjetRequest request;
		try {
			request = new MailjetRequest(Emailv31.resource)
					.property(Emailv31.MESSAGES,
							new JSONArray().put(new JSONObject()
									.put(Emailv31.Message.FROM,
											new JSONObject().put("Email", emailSender).put("Name",
													"Mailjet Pilot"))
									.put(Emailv31.Message.TO,
											new JSONArray().put(
													new JSONObject().put("Email", email).put("Name", "collaborateur")))
									.put(Emailv31.Message.SUBJECT, "Annulation d'un passager")
									.put(Emailv31.Message.HTMLPART,
											"<h3>Un de vos passager a annulé sa réservation</h3>")));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return getMailJetClient().post(request);
	}
}
