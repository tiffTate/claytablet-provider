package com.claytablet.model;

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
 * Holds a single mapping between a clay tablet platform project and a provider
 * system project.
 * 
 * <p>
 * Clay Tablet projects have a zero-to-many relationship with provider system
 * projects. Some provider systems do not support the concept of projects in
 * which case this mapping is not needed, some providers support one project for
 * multiple languages (one-to-one) and some providers only support one language
 * per project (one-to-many).
 * 
 * <p>
 * @see ProjectMap
 */
public class ProjectMapping {

	private String cttProjectId;

	private String sourceLanguageCode;

	private String targetLanguageCode;

	/**
	 * Empty constructor.
	 * 
	 */
	public ProjectMapping() {
	}

	/**
	 * Value constructor.
	 * 
	 * @param cttProjectId
	 * @param sourceLanguageCode
	 * @param targetLanguageCode
	 */
	public ProjectMapping(String cttProjectId, String sourceLanguageCode,
			String targetLanguageCode) {
		this.cttProjectId = cttProjectId;
		this.sourceLanguageCode = sourceLanguageCode;
		this.targetLanguageCode = targetLanguageCode;
	}

	/**
	 * @return the cttProjectId
	 */
	public String getCttProjectId() {
		return cttProjectId;
	}

	/**
	 * @param cttProjectId
	 *            the cttProjectId to set
	 */
	public void setCttProjectId(String cttProjectId) {
		this.cttProjectId = cttProjectId;
	}

	/**
	 * @return the sourceLanguageCode
	 */
	public String getSourceLanguageCode() {
		return sourceLanguageCode;
	}

	/**
	 * @param sourceLanguageCode
	 *            the sourceLanguageCode to set
	 */
	public void setSourceLanguageCode(String sourceLanguageCode) {
		this.sourceLanguageCode = sourceLanguageCode;
	}

	/**
	 * @return the targetLanguageCode
	 */
	public String getTargetLanguageCode() {
		return targetLanguageCode;
	}

	/**
	 * @param targetLanguageCode
	 *            the targetLanguageCode to set
	 */
	public void setTargetLanguageCode(String targetLanguageCode) {
		this.targetLanguageCode = targetLanguageCode;
	}

}
