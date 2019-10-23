package com.visiolending;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.visiolending.rules.Rule;

/**
 * This function will create a list of rules from a JSON file.
 */
public class RulesLoader {
	
	public static List<Rule> loadRules(String filePath) {
		
		Gson gson = new Gson();
		List<Rule> loadedRules = null;
		try {
			RulesLoader rl = new RulesLoader();
			File file = rl.getFileFromResources(filePath);
			loadedRules = gson.fromJson(new FileReader(file), new TypeToken<List<Rule>>(){}.getType());
		} catch (JsonSyntaxException | JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return loadedRules;
		
	}

    private File getFileFromResources(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
    
}
