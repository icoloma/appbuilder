package info.spain.opencatalog.repository;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public class StorageServiceImpl implements StorageService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	GridFsOperations gridFSTemplate;
	

	@Override
	public GridFSFile saveFile(InputStream inputStream, String filename, String contentType){
		return gridFSTemplate.store(inputStream, filename, contentType);
	}
		
	@Override
	public GridFsResource getByFilename(String filename){
		try {
		 return gridFSTemplate.getResource(filename);
		} catch (NullPointerException e){
			log.debug("File {} not found; returning null", filename);
			return null;
		}
	}

	@Override
	public void deleteFile(String filename) {
		gridFSTemplate.delete(query(whereFilename().is(filename)));
	}

	@Override
	public boolean existsFile(String filename) {
		GridFSDBFile result = gridFSTemplate.findOne(query(whereFilename().is(filename)));
		return result != null;
	}
	
}
