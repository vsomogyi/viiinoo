package com.alasdoo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.gmr.web.multipart.GMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;

import com.alasdoo.entity.DeletePictureForm;
import com.alasdoo.entity.MultiPartFileUpload;
import com.alasdoo.entity.Picture;
import com.google.appengine.api.datastore.Blob;

/**
 * Services for presisting pictures. 
 * @author Vinofil
 *
 */ 
@Service
public class PictureService  extends JdoDaoSupport {

	@Autowired
	protected PersistenceManagerFactory persistenceManagerFactory;


	@PostConstruct
	public void init() {

		super.setPersistenceManagerFactory(persistenceManagerFactory);

	}


	public PictureService() {
		super();
	}

	/**
	 * Saves the selected picture to the db.
	 * @param picture
	 */
	public void save(Picture picture) {
		PersistenceManager pm = getPersistenceManager(true);

		try {
			pm.makePersistent(picture);			
		}
		catch (Exception e) {
			//TODO: some kind of error message or logging
			e.printStackTrace();
		}
		finally {
			releasePersistenceManager(pm);
		}

	}
	/**
	 * Gets every picture from the DB
	 * @return
	 */
	public List<Picture> getAll() {
		PersistenceManager pm = getPersistenceManager(true);

		String query = "select from "+Picture.class.getName();

		@SuppressWarnings("unchecked")
		List<Picture> res = ((List<Picture>) pm.newQuery(query).execute());

		return res;
	}

	/**
	 * Gets the Picture from DB by Id
	 * @param picId
	 * @return
	 */
	public Picture get(Long picId) {

		PersistenceManager pm = getPersistenceManager(true);
		Picture result = null;
		try{
			result =  pm.getObjectById(Picture.class, picId);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;
	}
	/**
	 * Exctracts the picture files form the upload.
	 * Use this in the controller to get the list of picture from the MultiPartFileUpload
	 * @param upload
	 * @param name
	 * @return
	 */
	private List<Picture> extractPictures(MultiPartFileUpload upload,String name){
		List<Picture> pictures = new ArrayList<Picture>();
		for (GMultipartFile file : upload.getFiles())
			pictures.add(new Picture(name, new Blob(file.getBytes())));

		return pictures;
	}
	public List<Long> processUploadedPicture(MultiPartFileUpload upload,String name){
		List<Long> result = new ArrayList<Long>();

		List<Picture> pictures =  extractPictures(upload,name);

		for (Picture picture : pictures){
			if (picture !=null){		
				save(picture);
				result.add(picture.getId());
			}

		}
		return result;
	}
	/**
	 * Make  picutreUrl from picID
	 * @param pictureId
	 * @return
	 */
	public String getPictureUrl(Long pictureId) {
		return "/pictures/" + pictureId.toString();
	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	public List<String> getPicturesListFromPictureKeys(List<Long> keys){
		List<String> picturesList = new ArrayList<String>();		

		for (Long picture : keys)	{
			picturesList.add(getPictureUrl(picture));
		}
		return picturesList;
	}
	/**
	 * 
	 * @param keys
	 * @param minPicNumber
	 * @return
	 */
	public List<String> getPicturesListFromPictureKeysForUploadComponenet(List<Long> keys, Integer minPicNumber){
		List<String> picturesList = new ArrayList<String>();
		for (int i=0;picturesList.size() < minPicNumber;i++){
			picturesList.add("");
		}
		return picturesList;

	}
	/**
	 * Sets the active profile picture in the object which need to have the HasPicture interface implemented
	 * The profile picture change is based upon the DeletePictureForm
	 * This function should be used in the controller
	 * @param delForm
	 * @param object
	 */
	public void setActiveProfilePicture(DeletePictureForm delForm,HasPicture object){
		if (delForm.getDefaultPicture() != null){
			if (object.getPictures().isEmpty())
				object.setProfilePicture(null);
			else{
				boolean status = true;
				for (Long picId : object.getPictures()){
					if (delForm.getDefaultPicture().contains(picId.toString())){
						object.setProfilePicture(picId);
						status = false;
						break;
					}
				}
				if (status)
					object.setProfilePicture(object.getPictures().get(0));					
			}
		}else{
			// on first ime upload make the first picture the profilePicutre
			if (!object.getPictures().isEmpty())
				object.setProfilePicture(object.getPictures().get(0));	
		}

	}
/**
 * Delete the selected pictures in delForm from the object to have the HasPicture interface implemented
 * This function should be used in the controller
 * @param delForm
 * @param object
 */
	public void deleteSelectedPictures(DeletePictureForm delForm, HasPicture object){
		if (delForm.getPicUrl() != null)
			for (String data : delForm.getPicUrl()){				
				for (Long picId : object.getPictures()){
					if (data.contains(picId.toString())){
						object.getPictures().remove(picId);
						break;
					}
				}
			}	
	}
	/**
	 * 
	 * @param request
	 * @param object
	 */
	public void uploadPicture(MultiPartFileUpload upload, HttpServletRequest request, HasPicture object){
		if (ServletFileUpload.isMultipartContent(request)){
			// process the upload
			List<Long> picturesId = processUploadedPicture(upload,object.getPictureName());
			for (Long picId : picturesId){						
				object.addPicture(picId);		
			}			
		}
	}
	
	
}
