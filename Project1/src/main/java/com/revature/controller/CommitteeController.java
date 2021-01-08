package com.revature.controller;

import java.util.Set;

import com.revature.beans.Committee;
import com.revature.beans.Pitch;
import com.revature.service.CommitteeService;
import com.revature.service.CommitteeServiceImpl;
import com.revature.service.PitchService;
import com.revature.service.PitchServiceImpl;

import io.javalin.http.Context;

public class CommitteeController {
	private static CommitteeService comServ = new CommitteeServiceImpl();
	
	public static void getAllCommittees(Context ctx) {
		Set<Committee> coms = comServ.getCommittees();
		if(coms != null) {

			ctx.status(200);
			ctx.json(coms);
		}else {
			System.out.println("Oh my gosh there are no committees!?");
			ctx.status(404);
		}
	}
	



}
