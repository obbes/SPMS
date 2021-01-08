package com.revature.controller;

import com.revature.beans.Pitch;
import com.revature.beans.PitchPriority;
import com.revature.beans.PitchStage;
import com.revature.beans.Status;
import com.revature.beans.StoryType;
import com.revature.service.PitchPriorityService;
import com.revature.service.PitchPriorityServiceImpl;
import com.revature.service.PitchService;
import com.revature.service.PitchServiceImpl;
import com.revature.service.PitchStageService;
import com.revature.service.PitchStageServiceImpl;
import com.revature.service.StatusService;
import com.revature.service.StatusServiceImpl;
import com.revature.service.StoryTypeService;
import com.revature.service.StoryTypeServiceImpl;

import io.javalin.http.Context;

public class StageController {
	private static PitchStageService psServ = new PitchStageServiceImpl();
	private static StatusService statServ = new StatusServiceImpl();
	private static PitchService pServ = new PitchServiceImpl();
	private static StoryTypeService stServ = new StoryTypeServiceImpl();
	private static PitchPriorityService ppServ = new PitchPriorityServiceImpl();


	public static void getStageById(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		
		PitchStage stage = psServ.getPitchStageById(id);
		if(stage != null) {
			ctx.status(200);
			ctx.json(stage);
		}else {
			ctx.status(404);
		}
	}
	
	public static void pitchAcceptedStageCheck(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		Pitch pitch = pServ.getPitchById(id);
		PitchStage stage = pitch.getStage();
		//ensures all highprioritys are normal priority
		pitch.setPriority(ppServ.getPitchPriorityById(1));
		if(pitch.getStage().getId().equals(1)) {
			pitch.setStage(psServ.getPitchStageById(2));
			pServ.updatePitch(pitch);
		}else if(stage.getName().equals("general approval")) {
			pitch.setStage(psServ.getPitchStageById(3));
			pServ.updatePitch(pitch);
		}else if(stage.getName().equals("senior approval")) {
			pitch.setStage(psServ.getPitchStageById(4));
			pServ.updatePitch(pitch);
		}else if(stage.getName().equals("final approval")) {
			pitch.setStatus(statServ.getStatusById(2));
			StoryType st = stServ.getStoryTypeById(pitch.getStory_type().getId());
			st.setPoints(0);
			pitch.setStory_type(st);
			pitch.setPriority( ppServ.getPitchPriorityById(3));
			pitch.setStage(psServ.getPitchStageById(6));
			pServ.updatePitch(pitch);
		}
	}
	
}
