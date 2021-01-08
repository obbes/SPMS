package com.revature.app;
import static io.javalin.apibuilder.ApiBuilder.*;

import com.revature.controller.CommitteeController;
import com.revature.controller.PersonController;
import com.revature.controller.PitchController;
import com.revature.controller.RequestController;
import com.revature.controller.StageController;

import io.javalin.Javalin;

public class SPMSJavalinApp {
	
	public static void main(String[] args) {

		Javalin app = Javalin.create((config) -> {
			config.addStaticFiles("/static"); // pulling from src/main/resources
			config.enableCorsForAllOrigins();
		});
		
		app.start(8080);
		
		app.routes(() -> {
			
			path("users", () -> {
				get(PersonController::checkLogin); // get logged in user
				put(PersonController::logIn); // log in user
				post(PersonController::registerUser); // register new user
				delete(PersonController::logOut); // log out user
				path("pitches", () -> {
					get(PersonController::getPitchesByUserId);
					path(":id", () -> {
						delete(PersonController::deletePersonsPitch);
					});
				});
				path("requests", () -> {
					path(":id", () -> {
						get(PersonController::getRequestsByUserId);
					});
				});
				path (":id", () -> {
					get(PersonController::getUserById); // get user by id
					//can only have one get request - try getting as part of person object?
					// //gets pitches for user
					put(PersonController::updateUser); // update user
					delete(PersonController::deleteUser); // delete user
				});

			});
			
		
			path("pitch", () -> { 
				get(PitchController::getPitches);
				post(PitchController::addPitch); 	
				path(":id", () -> {
					get(PitchController::getPitchById); 
					put(PitchController::updatePitch); 
					delete(PitchController::deletePitch);
				});
				path("file", () ->{
					post(PitchController::uploadFile);
				});
				path("committees/:id", () -> {
				get(PitchController::getCommitteePitches);
				});
			});

			path("stage", () -> {
				path(":id", () ->{
				get(StageController::getStageById);
				put(StageController::pitchAcceptedStageCheck);
				});
			});
			
			path("committees", () -> {
				get(CommitteeController::getAllCommittees); 
			});
			path("requests", () ->{
				post(RequestController::addRequest);
				path(":id", () -> {
					get(RequestController::getRequestById);
					put(RequestController::updateRequest);
					delete(RequestController::deleteRequest);
				});
			});

		});
	}
}
