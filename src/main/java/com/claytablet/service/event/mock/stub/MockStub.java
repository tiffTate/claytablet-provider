package com.claytablet.service.event.mock.stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.LanguageMap;
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

	private final LanguageMap languageMap;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param languageMap
	 */
	@Inject
	public MockStub(final LanguageMap languageMap) {
		this.languageMap = languageMap;
	}

	public void sample() {

		// Provides language code mappings between the clay tablet platform and
		// the connecting system. Behavior is the same as a Hashtable. Use
		// get(key) to retrieve the mapping, where key is the clay tablet
		// platform language code.
		if (languageMap == null) {
			log
					.debug("No mappings for provider. Mappings must be specified in ./accounts/languageMap.xml");
		}

	}
}
