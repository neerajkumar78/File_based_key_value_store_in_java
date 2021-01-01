import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
/**
 * This class is saving all the  Settings to a database file
 * 
 *
 */
public class DatabaseHandler {
	
	private Properties keys;
	
	/** This executor does the commit job. */
	private static final ExecutorService updateExecutorService = Executors.newSingleThreadExecutor();
	
	/**
	 * Using this variable when i want to prevent update of database happen
	 */
	private boolean updateDatabaseLocked;
	
	/**
	 * The absolute path of the pdatabase file
	 */
	private String fileAbsolutePath;
	
	/**
	 * Constructor
	 */
	public DatabaseHandler(String fileAbsolutePath, boolean updatePropertiesLocked) {
		
		//Fields
		this.fileAbsolutePath = fileAbsolutePath;
		this.updateDatabaseLocked = updatePropertiesLocked;
		this.keys = new Properties();
	}
	
	/**
	 * Updates or Creates the given key , warning also updateDatabase can be locked , if you want to unlock it or check if locked check the method is
	 * `isUpdateDatabaseLocked()`
	 * 
	 * @param key
	 * @param value
	 */
	public void updateKey(String key , JSONObject value) {
		if (updateDatabaseLocked)
			return;
		File file = new File(fileAbsolutePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Submit it to the executors Service
		updateExecutorService.submit(() -> {
			try (InputStream inStream = new FileInputStream(fileAbsolutePath); OutputStream outStream = new FileOutputStream(fileAbsolutePath)) {
				
				//load  database
				keys.load(inStream);
				
				// set the database value
				keys.setProperty(key, value.toString());
				
				// save database 
				keys.store(outStream, null);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}
	
	/**
	 * Remove that key from the Database file
	 */
	public void deleteKey(String key) {
		//Check if exists 
		if (new File(fileAbsolutePath).exists())
			
			//Submit it to the executors Service
			updateExecutorService.submit(() -> {
				try (InputStream inStream = new FileInputStream(fileAbsolutePath); OutputStream outStream = new FileOutputStream(fileAbsolutePath)) {
					
					//load  database
					keys.load(inStream);
					
					// remove that from database
					keys.remove(key);
					
					// save database 
					keys.store(outStream, null);
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			});
		
	}
	
	/**
	 * Loads the Database
	 */
	public Properties loadKeys() {
		
		//Check if exists 
		if (new File(fileAbsolutePath).exists())
			
			//Load the properties file
			try (InputStream inStream = new FileInputStream(fileAbsolutePath)) {
				
				keys.load(inStream);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		
		return keys;
	}
	
	/**
	 * Get the properties instance of this class
	 * 
	 * @return the properties
	 */
	public Properties getProperties() {
		return keys;
	}
	
	/**
	 * Check if properties update is locked
	 * 
	 * @return the canUpdateProperty
	 */
	public boolean isUpdateDatabaseLocked() {
		return updateDatabaseLocked;
	}
	
	/**
	 * Lock or unlock the update of properties
	 * 
	 * @param canUpdateProperty
	 *            the canUpdateProperty to set
	 */
	public void setUpdatePropertiesLocked(boolean updateDatabaseLocked) {
		this.updateDatabaseLocked = updateDatabaseLocked;
	}
	
	/**
	 * @param fileAbsolutePath
	 *            The new absolute path of the properties file
	 */
	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}
	
	/**
	 * @return the propertiesAbsolutePath
	 */
	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}
	
}