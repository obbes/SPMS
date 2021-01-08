package com.revature.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

import com.revature.beans.AdditionalFile;
import com.revature.beans.Pitch;
import com.revature.data.AddFileDAO;
import com.revature.data.AddFileHibernate;
import com.revature.data.PitchDAO;
import com.revature.data.PitchHibernate;

public class PitchServiceImpl implements PitchService {
	private PitchDAO pDao = new PitchHibernate();
	private AddFileDAO afDao = new AddFileHibernate(); 
	@Override
	public Integer addPitch(Pitch p) {
		return pDao.add(p).getId();
	}

	@Override
	public Pitch getPitchById(Integer id) {
		return pDao.getById(id);
	}
	
	@Override
	public Set<Pitch> getPitches() {
		return pDao.getAll();
	}
	@Override
	public void updatePitch(Pitch p) {
		pDao.update(p);
	}

	@Override
	public void deletePitch(Pitch p) {
		pDao.delete(p);
	}

	@Override
	public Set<Pitch> getCommitteePitches(Integer id) {
		return pDao.getPitchesByCommitteeId(id);
	}
	
	@Override
	public String updateFilePaths(String file) {

		String path = "./src/main/resources/files/temp/" + file;
		
		AdditionalFile af = afDao.getByPath(path);
//		System.out.println(af);
		Pitch p = pDao.getByAdditionalFile(af);
//		System.out.println(p);
		
		String updatedPath = "./src/main/resources/files/pitch_" + p.getId() + "/pitch/";
		File newPath = new File(updatedPath);
		if (!newPath.exists()) {
			Boolean success = newPath.mkdirs();
			if (success) {
				System.out.println("Successfully created new file system");
			} else {
				System.out.println("Failed to create new file system");
			}
		}
		updatedPath += file;
		
		System.out.println(path);
		System.out.println(updatedPath);
		af.setPath(updatedPath);
		try {
			afDao.update(af);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File original = Paths.get(path).toFile();
		if (original.renameTo(Paths.get(updatedPath).toFile())) {
			original.delete();
			System.out.println("Successfully moved from " + path + " to " + updatedPath);
		} else {
			System.out.println("Failed to move file");
		}
		
		return updatedPath;
	}



}
