package com.hotel.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "com.hotel.ws.MessageGen",
serviceName="messageGenService")
public class MessageGenImpl implements MessageGen {
	
	@Override
	public String getMessage() {
		
		return "Works!";
	}

}

