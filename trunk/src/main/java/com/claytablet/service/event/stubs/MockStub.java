package com.claytablet.service.event.stubs;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.AssetTaskMap;
import com.claytablet.model.ConnectionContext;
import com.claytablet.model.LanguageMap;
import com.claytablet.model.ProjectMap;
import com.claytablet.model.ProjectMapping;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
 * This is the mock implementation for a connected system.
 * 
 * @see LanguageMapProvider
 */
@Singleton
public class MockStub {

	private final Log log = LogFactory.getLog(getClass());

	private final ConnectionContext context;

	private final LanguageMap languageMap;

	private final ProjectMap projectMap;

	private final AssetTaskMap assetTaskMap;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param context
	 * @param languageMap
	 * @param projectMap
	 * @param assetTaskMap
	 */
	@Inject
	public MockStub(final ConnectionContext context,
			final LanguageMap languageMap, final ProjectMap projectMap,
			final AssetTaskMap assetTaskMap) {

		this.context = context;
		this.languageMap = languageMap;
		this.projectMap = projectMap;
		this.assetTaskMap = assetTaskMap;
	}

	public void logConfig() throws IOException {

		log.debug("** Connection parameters **");
		Hashtable<String, String> parms = context.getConnectionParms();
		for (String key : parms.keySet()) {
			log.debug(key + ": " + parms.get(key));
		}

		log.debug("** Language mappings **");
		for (String key : languageMap.keys()) {
			log.debug(key + ": " + languageMap.get(key));
		}

		log.debug("** Project mappings **");
		for (String key : projectMap.keys()) {
			log.debug("Provider Project ID -- " + key + " --");
			ProjectMapping mapping = projectMap.get(key);
			log.debug("CTT Project ID: " + mapping.getCttProjectId());
			log.debug("Source language: " + mapping.getSourceLanguageCode());
			log.debug("Target language: " + mapping.getTargetLanguageCode());

		}

		log.debug("** Asset task mappings **");
		for (String key : assetTaskMap.keys()) {
			log.debug(key + ": " + assetTaskMap.get(key));
		}

	}

	public void ApproveAssetTask(String assetTaskId, String downloadPath) {

		// TODO - inform the TMS that an asset task has been approved and send
		// it the new file if downloadPath is not null.
	}

	public void CancelAssetTask(String assetTaskId) {

		// TODO - inform the TMS that an asset task has been cancelled if
		// supported.
	}

	public void CancelSupportAsset(String supportAssetId) {

		// TODO - inform the TMS that a support asset has been cancelled if
		// supported.
	}

	public void RejectAssetTask(String assetTaskId, String downloadPath) {

		// TODO - inform the TMS that an asset task has been rejected and
		// include the changed file if downloadPath is not null.

		// For the mock implementation we're simply going to convert all of the
		// text to lower case and let the poller know that it's been
		// re-translated.
		log.debug("Convert all of the text to lower case.");
		try {
			String contents = FileUtils
					.readFileToString(new File(downloadPath));
			contents = contents.toLowerCase();
			FileUtils.writeStringToFile(new File(downloadPath), contents);
		} catch (IOException e) {
			log.error(e);
		}

		// TODO - we need to let the poller know that something has changed

	}

	public void StartAssetTask(String assetTaskId, String downloadPath) {

		// TODO - submit the new asset task to the TMS.

		// For the mock implementation we're simply going to convert all of the
		// text to lower case and let the poller know that it's been translated.
		log.debug("Convert all of the text to lower case.");
		try {
			String contents = FileUtils
					.readFileToString(new File(downloadPath));
			contents = contents.toLowerCase();
			FileUtils.writeStringToFile(new File(downloadPath), contents);
		} catch (IOException e) {
			log.error(e);
		}

		// TODO - we need to let the poller know that something has changed

	}

	public void StartSupportAsset(String supportAssetId, String downloadPath) {

		// TODO - make the support asset available in the TMS if supported.

		// TODO - we need to let the poller know that something has changed
	}

}
