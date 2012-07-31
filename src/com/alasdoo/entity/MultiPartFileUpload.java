package com.alasdoo.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.gmr.web.multipart.GMultipartFile;

/**
 * A support class for file Upload
 * 
 * @author Vinofil
 *
 */
public class MultiPartFileUpload {


	private String filename;

	private List<GMultipartFile> file = new ArrayList<GMultipartFile>();

		
	@SuppressWarnings("unchecked")
	public MultiPartFileUpload(){
		//FIXME: check a way to use the LazyList decorator, this way can lead to performance issues.
		//file = LazyList.decorate( new ArrayList<GMultipartFile>(), FactoryUtils.instantiateFactory(GMultipartFile.class));

	}
	public String getName() {
		return filename;
	}

	public void setName(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<GMultipartFile> getFile() {
		return file;
	}
	public void setFile(List<GMultipartFile> file) {
		this.file = file;
	}
	public List<GMultipartFile> getFiles(){
	    List<GMultipartFile> files = new ArrayList<GMultipartFile>();
		
		for (GMultipartFile f : file){
			if (f != null)
				if (f.getFileItem().getSize() > 0)
					files.add(f);
		}
		return files; 
	}
}

