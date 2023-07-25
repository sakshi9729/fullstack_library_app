package com.luv2code.springbootlibrary.service.Utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {
	public static String payloadJWTExtraction(String token, String extraction) {
		//Remove bearer from the token
		token.replace("Bearer", "");
		//Extracting header , payload and signature from the token by breaking it
		String[] chunks=token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		//Decoding just the payload from the chunk
		String payload=new String(decoder.decode(chunks[1]));
		//Split payload by commas
		String[] entries = payload.split(",");
		
		Map<String, String> map=new HashMap<String, String>();
		for(String entry : entries) {
			//Loop through entries until we find the value of sub which is gng to be an email
			String[] keyValue=entry.split(":");
			if(keyValue[0].equals(extraction)) {
			int remove=1;
			if(keyValue[1].endsWith("}")) {
				remove=2;
			}
			keyValue[1]=keyValue[1].substring(0,keyValue[1].length()-remove);
			keyValue[1]=keyValue[1].substring(1);
			//Once we find sub we add that as a key and ending as the value
			map.put(keyValue[0], keyValue[1]);
			}
		}
		if(map.containsKey(extraction)){
			return map.get(extraction);
		}
		return null;
	}
}
