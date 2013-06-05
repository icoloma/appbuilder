package info.spain.opencatalog.repository;

import java.io.InputStream;

import org.springframework.data.mongodb.gridfs.GridFsResource;

import com.mongodb.gridfs.GridFSFile;


public interface StorageService {
	
	public GridFSFile saveFile(InputStream inputStream, String filename, String contentType);
	public GridFsResource getByFilename(String filename);
	public void deleteFile(String filename);
	public boolean existsFile(String filename);

}
