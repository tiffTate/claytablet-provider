package com.claytablet.service.event;

import com.claytablet.model.event.provider.UpdateAssetTaskState;
import com.claytablet.service.event.impl.ProviderStatePollerImpl;
import com.google.inject.ImplementedBy;

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
 * The provider state poller is reponsible for trapping and processing provider
 * initiated asset task state changes and sending the back to the clay tablet
 * platform.
 * 
 * <p>
 * Implementations of this interface should contain provider specific code to
 * hook up to native TMS systems etc.
 * 
 * <p>
 * @see ProviderStatePollerImpl
 * @see ProviderSender
 * @see UpdateAssetTaskState
 */
@ImplementedBy(ProviderStatePollerImpl.class)
public interface ProviderStatePoller {

	/**
	 * Polls the TMS for asset task state changes, and sends important updates
	 * back to the platform.
	 * 
	 * @throws Exception
	 */
	public void poll() throws Exception;

}
