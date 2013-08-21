package info.spain.opencatalog.repository;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

/**
 * TODO: Comprobar si hay que crear un índice sobre el nombre del fichero  
 */
public class StorageServiceImpl implements StorageService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GridFsOperations gridFSTemplate;
	

	@Override
	public GridFSFile saveFile(InputStream inputStream, String filename, String contentType){
		return gridFSTemplate.store(inputStream, filename, contentType);
	}
		
	@Override
	public GridFsResource getByFilename(String filename){
		if (Strings.isNullOrEmpty(filename)){
			return null;
		}
		try {
		 return gridFSTemplate.getResource(filename);
		} catch (NullPointerException e){
			log.debug("File {} not found; returning null", filename);
			return null;
		}
	}

	@Override
	public void deleteFile(String filename) {
		// OJO, parece que solo está borrando de db.fs.chunks y asingando length=0 en db.fs.files
		gridFSTemplate.delete(query(whereFilename().is(filename)));
	}

	@Override
	public boolean existsFile(String filename) {
		GridFSDBFile result = gridFSTemplate.findOne(query(whereFilename().is(filename)));
		return result != null;
	}
	
	public List<String> getFilenamesLike(String filename) {
		List<GridFSDBFile> files = gridFSTemplate.find(query(whereFilename().regex(filename)).with(new Sort(Direction.ASC, "uploadDate")));
		List<String> result = Lists.newArrayList();
		for (GridFSDBFile file : files) {
			if (file.getLength() >0 ) {
				// Comprobamos la longitud debido a que al borrar solo borra en db.fs.chunks y pone lenght=0 en db.fs.files
				result.add(file.getFilename());
			}
		}
		return result;
	}
	
}
