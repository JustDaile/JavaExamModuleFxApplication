package info.justdaile.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import info.justdaile.application.json.UserDataModule;
import info.justdaile.application.strings.JSONKeys;

/**
 * Essentially a singleton type file, I should really refactor this code or come up with a more
 * suitable way to deal with external file reading/writing.
 *
 * This file only deals with the loading/saving of the res/data/save file
 * that holds the results of completed surveys.
 */
public class UserFileManager {
	
	private JSONObject saveData;
	private File dir = new File("res/data/");
	private File file = new File("res/data/save");

	// on constructor do some checks to see if we need to create the save file
	// create or read the save file into saveData JSONObject
	public UserFileManager(){
		if(!this.dir.exists()){
			this.dir.mkdirs();
			this.createNewFile();
		}else if(!file.exists()){
			this.createNewFile();
		}
		StringBuffer collection = new StringBuffer();
		try{
			FileReader reader = new FileReader(file);
			char[] buffer = new char[4];
			while(reader.read(buffer) != -1){
				collection.append(buffer);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		this.saveData = new JSONObject(collection.toString());
	}

	// get the JSON node 'Modules' from the JSONObject 'saveData'
	public JSONArray getModules(){
		return this.saveData.getJSONArray(JSONKeys.USER_SAVE_MODULES);
	}

	// adds a new module to the end of the saveData Json array
	// TODO I need to work on naming here for sure, it is a bit confusing.
	// TODO I want to change 'module' to something a little more understandable.
	// something like survey or questionnaire may be a little better.
	public void addModule(UserDataModule data){
		this.getModules().put(this.getModules().length(), data.toJSON());
	}

	// we wouldn't be at this point without a save file or data being initialized.
	// so we can do a basic write to file.
	public void save(){
		try{
			FileWriter w = new FileWriter(this.file);
			w.write(this.saveData.toString(2));
			w.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// on create new file
	// save an empty array to prevent errors from trying to read invalid JSON
	// with the stleary JSON parser
	private void createNewFile(){
		try {
			this.file.createNewFile();
			this.saveData = new JSONObject();
			this.saveData.put(JSONKeys.USER_SAVE_MODULES, new JSONArray());
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
