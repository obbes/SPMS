package com.revature.controller;

import java.util.HashSet;
import java.util.Set;

import com.revature.beans.InfoRequest;
import com.revature.beans.Person;
import com.revature.beans.Pitch;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.service.InfoRequestService;
import com.revature.service.InfoRequestServiceImpl;
import com.revature.service.PersonService;
import com.revature.service.PersonServiceImpl;
import com.revature.service.PitchService;
import com.revature.service.PitchServiceImpl;

import io.javalin.http.Context;

public class PersonController {
	private static PersonService personServ = new PersonServiceImpl();
	private static PitchService pitchServ = new PitchServiceImpl();
	public static InfoRequestService irServ = new InfoRequestServiceImpl();
	public static void checkLogin(Context ctx) {
		System.out.println("Checking login");
		Person p = ctx.sessionAttribute("user");
		if (p != null) {

			ctx.json(p);
			ctx.status(200);
		} else {
			System.out.println("Not logged in");
			ctx.status(400);
		}
	}
	public static void logIn(Context ctx) {
		System.out.println("Logging in");
		String username = ctx.queryParam("user");
		String password = ctx.queryParam("pass");
		
		Person p = personServ.getPersonByUsername(username);
		if (p != null) {
			if (p.getPasswd().equals(password))
			{
				
				ctx.status(200);
				ctx.json(p);
				ctx.sessionAttribute("user", p);
			}
			else
			{
				System.out.println("Wrong credenitals");
				// password mismatch
				ctx.status(400);
			}
		}
		else
		{
			System.out.println("wrong credenitals part 2");
			// username not found
			ctx.status(404);
		}
	}
	
	public static void logOut(Context ctx) {
		System.out.println("Logging out");
		ctx.req.getSession().invalidate();
		ctx.status(200);
	}
	
	public static void registerUser(Context ctx) {
		Person newPerson = ctx.bodyAsClass(Person.class);
		try {
			personServ.addPerson(newPerson);
		}
		catch (NonUniqueUsernameException e) {
			System.out.println("Username already taken :(");
			ctx.status(409); // 409 = conflict
		}
		ctx.status(200);
	}
	
	public static void getUserById(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		Person p = personServ.getPersonById(id);
		if (p != null) {
			ctx.status(200);
			ctx.json(p);
		} else {
			ctx.status(404);
		}
	}
	
	public static void updateUser(Context ctx) {
		Person tempPerson = ctx.bodyAsClass(Person.class);
		personServ.updatePerson(tempPerson);
		ctx.status(202);
	}
	
	public static void deleteUser(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		Person person = personServ.getPersonById(id);
		personServ.deletePerson(person);
		ctx.status(204);
	}
	
	public static void getPitchesByUserId(Context ctx) {
		Set<Pitch> pitches = new HashSet<>();
		Person loggedPerson = ctx.sessionAttribute("user");
		pitches = personServ.getAllPitchesByPersonId(loggedPerson.getId());
		if (pitches != null ) {
		ctx.status(200);
		ctx.json(pitches);
		}else {
			ctx.status(404);
		}
	}
	
	public static void getRequestsByUserId(Context ctx) {
		Person loggedPerson = ctx.sessionAttribute("user");
		System.out.println("I recognize the user as " + loggedPerson.getUsername());
		Set<InfoRequest> reqs = new HashSet<>();
		reqs = irServ.getReqsByUserId(loggedPerson.getId());
		if(reqs != null) {
			ctx.status(200);
			ctx.json(reqs);
		}else {
			ctx.status(404);
		}
	}
	
	
	public static void deletePersonsPitch(Context ctx) {
		Person loggedPerson = ctx.sessionAttribute("user");
		Pitch pitch = pitchServ.getPitchById(Integer.valueOf(ctx.pathParam("id")));
		/**
		 * delete the pitch from the user first to attempt to get it out of person_pitch
		 * then delete the pitch to deal with cascading
		 * To do after requests files and drafts are added
		 */
	}
}
