package com.hotel.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "com.hotel.ws.MessageGen",
serviceName="messageGenService")
public class MessageGenImpl implements MessageGen {

	public String getMessage() {
		
		return "Works!";
	}

}

