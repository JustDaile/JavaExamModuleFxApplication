package info.justdaile.io;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.json.JSONObject;

import info.justdaile.application.json.JSONArrayList;
import info.justdaile.application.json.Module;

public class ModuleLoader {
	
	public static String MODULE_ROOT = "res/modules";
	
	private File rootDir;
	private List<Module> modules;
	
	private ModuleLoader(){
		this.rootDir = new File(ModuleLoader.MODULE_ROOT);
		this.modules = new JSONArrayList<Module>();
		if(!this.rootDir.isDirectory()){			
			this.rootDir.mkdirs();
		}

		Arrays.asList(rootDir.listFiles()).stream()
		                                  .map(i -> Module.fromJSONObject(new JSONObject(this.loadData(i))))
		                                  .forEach(m -> modules.add(m));
	}
	
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
	
	public List<Module> getModules(){
		return this.modules;
	}
	
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
	
	private void close(){
		this.rootDir = null;
		modules.clear();
	}

}
