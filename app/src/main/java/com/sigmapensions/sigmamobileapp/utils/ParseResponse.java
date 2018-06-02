package com.sigmapensions.sigmamobileapp.utils;
import java.util.HashMap;


public class ParseResponse {
	HashMap<String, String> responseMap;

	int lengthOfXml; 
	int beginingIndex; 
	int indexOfStartTag = 0;
	int indexOfStartText = 0;
	boolean startTag = false;
	boolean startText = false;
	int nextIndex = 0;
	String data;

	String tagName = "";
	String text = "";

	public ParseResponse(String data){
		responseMap = new HashMap<String, String>();
		this.data = data;
		lengthOfXml = data.length();
		if(data.indexOf("soap:Fault") > 1){
			beginingIndex = data.indexOf("faultcode");
		}else{
			beginingIndex = data.indexOf("ResponseNewPFA");
		}
		
	}
	
	public HashMap<String, String> extractPencomResponse(){
		while(beginingIndex < lengthOfXml){
			char c = data.charAt(beginingIndex);
			if(c == '<'){
				startTag = true;
				indexOfStartTag = ++beginingIndex;
			}else{
				beginingIndex++;
			}
			if(startTag){
				nextIndex = indexOfStartTag;

				while(data.charAt(nextIndex) != '>'){
					tagName += data.charAt(nextIndex);
					nextIndex++;
				}
				try{
					indexOfStartText = ++nextIndex;
					while(data.charAt(indexOfStartText) != '<'){
						startText = true;
						if(startText){
							text += data.charAt(indexOfStartText);
							indexOfStartText++;
						}

					}
				}catch(Exception e){

				}

				startText = false;

				beginingIndex = nextIndex++;
				startTag = false;
				if(!tagName.contains("/") && !tagName.contains("?")){
					responseMap.put(tagName, text);
				}
				text = "";
				tagName = "";

			}
		}
		return responseMap;
	}

}
