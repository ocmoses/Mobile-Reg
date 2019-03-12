package com.sigmapensions.sigmamobileapp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class PostXML {
	public static String[] recipients;
	public static String xmlrecipients;
	public static String username;
	public static String apikey;
	public static String sendername;
	public static String message;
	public static final String flash = "0";
	public static String feedBack;

	public static String send(String username, String apikey, String sendername, String message, String[] recipients){
		String randmsgid = Double.toString(Math.random());
		for( int i =0; i < recipients.length; i++ ){
			xmlrecipients += "<gsm><msidn>"+ recipients[i] + "</msidn><msgid>" + randmsgid +
					"_" + i + "</msgid>" + "</gsm>";
		}
		String xmlrequest =
				"<SMS>\n"
						+ "<auth>"
						+ "<username>" + username + "</username>\n"
						+ "<apikey>" + apikey + "</apikey>\n"
						+ "</auth>\n"
						+ "<message>"
						+ "<sender>" + sendername + "</sender>\n"
						+ "<messagetext>" + message + "</messagetext>\n"
						+ "<flash>" + flash + "</flash>\n"
						+ "</message>\n"
						+ "<recipients>\n"
						+ xmlrecipients
						+ "</recipients>\n"
						+ "</SMS>";


		String theurl = "http://api.ebulksms.com:8080/sendsms.xml";
		PostXML requester = new PostXML();
		feedBack = requester.postXMLData(xmlrequest, theurl);
		Log.e("MobileReg", "Feedback: " + feedBack);
		return feedBack;
	}

	public String postXMLData(String xmldata, String urlpath){
		String result = "";
		try {
			URL myurl = new URL(urlpath);
			URLConnection urlconn = myurl.openConnection();

			urlconn.setRequestProperty("Content-Type", "text/xml");
			urlconn.setDoOutput(true);
			urlconn.setDoInput(true);
			urlconn.connect();

			//Create a writer to the url
			PrintWriter pw = new PrintWriter(urlconn.getOutputStream());
			pw.write(xmldata, 0, xmldata.length());
			pw.close();

			//Create a reader for the output of the connection
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				result = result + line + "\n";
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
