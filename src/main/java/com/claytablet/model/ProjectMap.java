package com.claytablet.model;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.thoughtworks.xstream.XStream;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at <a
 * href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>
 * 
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Holds mappings between clay tablet platform projects and provider system
 * projects.
 * 
 * <p>
 * Clay Tablet projects have a zero-to-many relationship with provider system
 * projects. Some provider systems do not support the concept of projects in
 * which case this mapping is not needed, some providers support one project for
 * multiple languages (one-to-one) and some providers only support one language
 * per project (one-to-many).
 * 
 * <p>
 * Behaviour delegates to a Hashmap where the key is the provider's project
 * identifier and the value is a ProjectMapping class containing the clay tablet
 * project identifier, a source language, and a target language.
 * 
 * <p>
 * @see ProjectMapping
 */
public class ProjectMap {

	protected final Log log = LogFactory.getLog(getClass());

	protected ConnectionContext context;

	protected Hashtable<String, ProjectMapping> mappings = new Hashtable<String, ProjectMapping>();

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param context
	 */
	@Inject
	public ProjectMap(ConnectionContext context) {

		this.context = context;

		try {
			load();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Forecfully loads (deserializes) the project map from an xml file.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void load() throws IOException {

		// retrieve the source account so we can get the xml data directory
		// where the mappings are stored.
		String path = context.getXmlDataDirectory() + "projectMap.xml";

		log.debug("Load the project map xml from: " + path);
		String xml = FileUtils.readFileToString(new File(path));

		log
				.debug("Clear the project map and load in (deserialize) the new one.");
		this.clear();
		this.mappings = (Hashtable) getXStream().fromXML(xml);
	}

	/**
	 * Saves (serializes) the project map to an xml file.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {

		String path = context.getXmlDataDirectory() + "projectMap.xml";

		log.debug("Save the project map to: " + path);

		log.debug("Serialize the project map to xml.");
		String xml = getXStream().toXML(this.mappings);

		log.debug("Save the project map xml to: " + path);
		FileUtils.writeStringToFile(new File(path), xml);
	}

	/**
	 * Retrieves the XStream instance to use for serializeing / deserializing
	 * objects to / from XML.
	 * 
	 * @return The XStream instance.
	 */
	private XStream getXStream() {

		XStream xstream = new XStream();
		xstream.alias("ProjectMap", Hashtable.class);
		xstream.alias("ProjectMapping", ProjectMapping.class);

		return xstream;
	}

	/**
	 * 
	 * @see java.util.Hashtable#clear()
	 */
	public void clear() {
		mappings.clear();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#containsKey(java.lang.Object)
	 */
	public boolean containsKey(String key) {
		return mappings.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Hashtable#containsValue(java.lang.Object)
	 */
	public boolean containsValue(ProjectMapping value) {
		return mappings.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#get(java.lang.Object)
	 */
	public ProjectMapping get(String key) {
		return mappings.get(key);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#isEmpty()
	 */
	public boolean isEmpty() {
		return mappings.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#keySet()
	 */
	public Collection<String> keys() {
		return mappings.keySet();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#values()
	 */
	public Collection<ProjectMapping> values() {
		return mappings.values();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
	 */
	public ProjectMapping put(String key, ProjectMapping value) {
		return mappings.put(key, value);
	}

	/**
	 * @param t
	 * @see java.util.Hashtable#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends String, ? extends ProjectMapping> t) {
		mappings.putAll(t);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#remove(java.lang.Object)
	 */
	public ProjectMapping remove(String key) {
		return mappings.remove(key);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#size()
	 */
	public int size() {
		return mappings.size();
	}

}
