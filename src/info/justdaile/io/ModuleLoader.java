package info.justdaile.io;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONObject;

import info.justdaile.application.json.JSONArrayList;
import info.justdaile.application.json.Module;

/**
 * Loads each 'module' or questionnaire in the res/modules folder
 * and saves them to an array list.
 *
 */
public class ModuleLoader {

	// TODO may move this to an interface so as to be it global
	public static String MODULE_ROOT = "res/modules";
	
	private File rootDir;
	private List<Module> modules;
	
	private ModuleLoader(){
		this.rootDir = new File(ModuleLoader.MODULE_ROOT);
		this.modules = new JSONArrayList();
		// ensure directory exists or create it.
		if(!this.rootDir.isDirectory()){			
			this.rootDir.mkdirs();
		}
		Arrays.asList(rootDir.listFiles())
				.stream()
				.map(i -> Module.fromJSONObject(new JSONObject(this.loadData(i))))
				.forEach(m -> modules.add(m));
	}

	// a nice little method using functional interfaces in Java 8
	// allows a consumer method to handle a new loader object
	// and disposes of it after use.
	// I quite like this.
	public static void handle(Consumer<ModuleLoader> handle){
		ModuleLoader loader = new ModuleLoader();
		try{
			handle.accept(loader);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			loader.close();
		}
	}

	// get a list of modules
	public List<Module> getModules(){
		return this.modules;
	}

	// private method used in the constructor to read file content and create the module.
	private String loadData(File file) {
		StringBuffer collection = new StringBuffer();
		try{
			FileReader reader = new FileReader(file);
			char[] buffer = new char[(int) (file.length())];
			while(reader.read(buffer) != -1){
				collection.append(buffer);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return collection.toString();
	}

	// clears resources to java garbage collection can clean up
	private void close(){
		this.rootDir = null;
		modules.clear();
	}

}
