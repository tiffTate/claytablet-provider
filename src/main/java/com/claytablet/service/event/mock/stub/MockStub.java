package com.claytablet.service.event.mock.stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.LanguageMap;
import com.claytablet.provider.LanguageMapProvider;
import com.google.inject.Inject;

public class MockStub {

	private final Log log = LogFactory.getLog(getClass());

	private final LanguageMapProvider lmp;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param lmp
	 */
	@Inject
	public MockStub(LanguageMapProvider lmp) {
		this.lmp = lmp;
	}

	public void sample() {

		// Provides language code mappings between the clay tablet platform and
		// the connecting system. Behavior is the same as a Hashtable. Use
		// get(key) to retrieve the mapping, where key is the clay tablet
		// platform language code.
		LanguageMap languageMap = lmp.get();
		if (languageMap == null) {
			log
					.debug("No mappings for provider. Mappings must be specified in ./accounts/languageMap.xml");
		}

	}
}
