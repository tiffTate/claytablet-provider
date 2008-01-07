package com.claytablet.model;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.provider.SourceAccountProvider;
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

	private final Log log = LogFactory.getLog(getClass());

	private final SourceAccountProvider sap;

	private Hashtable<String, ProjectMapping> projectMappings = new Hashtable<String, ProjectMapping>();

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param sap
	 */
	@Inject
	public ProjectMap(final SourceAccountProvider sap) {
		this.sap = sap;
	}

	/**
	 * Loads (deserializes) the project map from an xml file.
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {

		// check if we've already loaded the mappings.
		if (this.projectMappings == null || this.projectMappings.size() == 0) {
			// nothing loaded, call refresh to pull the mappings from disk.
			refresh();
		}
	}

	/**
	 * Loads (deserializes) the project map from an xml file.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void refresh() throws IOException {

		// retrieve the source account so we can get the xml data directory
		// where the mappings are stored.
		String path = sap.get().getXmlDataDirectory() + "projectMap.xml";

		log.debug("Refresh the project map from: " + path);

		// load the xml string from disk
		String xml = FileUtils.readFileToString(new File(path));

		// clear the existing hashtable and load in the new one
		this.clear();
		this.projectMappings = (Hashtable) getXStream().fromXML(xml);
	}

	/**
	 * Saves (serializes) the project map to an xml file.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {

		String path = sap.get().getXmlDataDirectory() + "projectMap.xml";

		log.debug("Save the project map to: " + path);

		// serialize the object to an xml string
		String xml = getXStream().toXML(this.projectMappings);

		// save the string to disk
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
		projectMappings.clear();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#clone()
	 */
	public Object clone() {
		return projectMappings.clone();
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Hashtable#contains(java.lang.Object)
	 */
	public boolean contains(ProjectMapping value) {
		return projectMappings.contains(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#containsKey(java.lang.Object)
	 */
	public boolean containsKey(String key) {
		return projectMappings.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Hashtable#containsValue(java.lang.Object)
	 */
	public boolean containsValue(ProjectMapping value) {
		return projectMappings.containsValue(value);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#elements()
	 */
	public Enumeration<ProjectMapping> elements() {
		return projectMappings.elements();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#entrySet()
	 */
	public Set<Entry<String, ProjectMapping>> entrySet() {
		return projectMappings.entrySet();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Hashtable#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return projectMappings.equals(o);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#get(java.lang.Object)
	 */
	public ProjectMapping get(String key) {
		return projectMappings.get(key);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#hashCode()
	 */
	public int hashCode() {
		return projectMappings.hashCode();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#isEmpty()
	 */
	public boolean isEmpty() {
		return projectMappings.isEmpty();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#keys()
	 */
	public Enumeration<String> keys() {
		return projectMappings.keys();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#keySet()
	 */
	public Set<String> keySet() {
		return projectMappings.keySet();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
	 */
	public ProjectMapping put(String key, ProjectMapping value) {
		return projectMappings.put(key, value);
	}

	/**
	 * @param t
	 * @see java.util.Hashtable#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends String, ? extends ProjectMapping> t) {
		projectMappings.putAll(t);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Hashtable#remove(java.lang.Object)
	 */
	public ProjectMapping remove(String key) {
		return projectMappings.remove(key);
	}

	/**
	 * @return
	 * @see java.util.Hashtable#size()
	 */
	public int size() {
		return projectMappings.size();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#toString()
	 */
	public String toString() {
		return projectMappings.toString();
	}

	/**
	 * @return
	 * @see java.util.Hashtable#values()
	 */
	public Collection<ProjectMapping> values() {
		return projectMappings.values();
	}

}
