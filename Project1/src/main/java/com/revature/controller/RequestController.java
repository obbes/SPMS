package com.revature.controller;

import com.revature.beans.InfoRequest;
import com.revature.service.InfoRequestService;
import com.revature.service.InfoRequestServiceImpl;
import com.revature.service.PersonService;
import com.revature.service.PersonServiceImpl;

import io.javalin.http.Context;

public class RequestController {
	private static InfoRequestService irServ = new InfoRequestServiceImpl();
	private static PersonService pServ = new PersonServiceImpl();
	
	
	public static void getRequestById(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		InfoRequest ir = irServ.getInfoRequestById(id);
		
		if(ir != null) {
			ctx.status(200);
			ctx.json(ir);
		}else {
			ctx.status(404);
		}
	}
	
	
	public static void addRequest(Context ctx) {

		InfoRequest ir = ctx.bodyAsClass(InfoRequest.class);
	
		ir.setRecipient(pServ.getPersonById(ir.getRecipient().getId()));
		ir.setSender(pServ.getPersonById(ir.getSender().getId()));
		ir.setQuestion(ir.getQuestion());
		ir.setAnswer("Waiting for an answer");
		System.out.println("Sending the following request to the db: revieving-"+ir.getRecipient().getUsername() 
				+"sending-"+ ir.getSender().getUsername() +
				"question- "+ir.getQuestion());
		irServ.addInfoRequest(ir);
		ctx.status(201);
	}
	
	
	public static void updateRequest(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		
		InfoRequest ir = irServ.getInfoRequestById(id);
		InfoRequest uIr = ctx.bodyAsClass(InfoRequest.class);

		//sender, recipient and id should not change or oquestion
//		if(uIr.getQuestion() != ir.getQuestion()) {
//			ir.setQuestion(uIr.getQuestion());
//		}
		if(uIr.getAnswer() != ir.getAnswer()) {
			ir.setAnswer(uIr.getAnswer());
		}
		//old is set to new and updated accordingly
		irServ.updateInfoRequest(ir);
		ctx.status(202);
	}
	
	public static void deleteRequest(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		InfoRequest ir = irServ.getInfoRequestById(id);
		irServ.deleteInfoRequest(ir);
		ctx.status(204);
	}
}
