import com.claytablet.model.ProjectMap;
import com.claytablet.model.ProjectMapping;
import com.google.inject.Guice;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Create a default project map file named ProjectMap.xml.
 * 
 */
public class InitProjectMap {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		ProjectMap projectMap = Guice.createInjector().getInstance(
				ProjectMap.class);

		projectMap.put("mock-provider-project-id1", new ProjectMapping(
				"ctt-project-id", "en-us", "fr"));
		projectMap.put("mock-provider-project-id2", new ProjectMapping(
				"ctt-project-id", "en-us", "de"));

		projectMap.save();

	}
}
