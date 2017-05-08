package info.justdaile.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import info.justdaile.application.json.UserDataModule;
import info.justdaile.application.strings.JSONKeys;

public class UserFileManager {
	
	private JSONObject saveData;
	private File dir = new File("res/data/");
	private File file = new File("res/data/save");
	
	public UserFileManager(){
		if(!dir.exists()){
			dir.mkdirs();
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
	
	public JSONArray getModules(){
		return this.saveData.getJSONArray(JSONKeys.USER_SAVE_MODULES);
	}
	
	public JSONObject getLastModule(){
		JSONArray a =  this.getModules();
		return a.getJSONObject(a.length());
	}
	
	public void addModule(UserDataModule data){
		this.getModules().put(this.getModules().length(), data.toJSON());
	}
	
	public void save(){
		try{
			FileWriter w = new FileWriter(file);
			w.write(saveData.toString(2));
			w.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void createNewFile(){
		try {
			file.createNewFile();
			this.saveData = new JSONObject();
			this.saveData.put(JSONKeys.USER_SAVE_MODULES, new JSONArray());
			this.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
