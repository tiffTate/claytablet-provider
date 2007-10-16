package com.claytablet.service.event;

import com.claytablet.model.event.platform.ApprovedAssetTask;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.RejectedAssetTask;
import com.claytablet.model.event.platform.StartAssetTask;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.model.event.producer.CancelAssetTask;
import com.claytablet.model.event.producer.RejectAssetTask;
import com.claytablet.service.event.impl.ProviderReceiverImpl;
import com.claytablet.storage.service.StorageServiceException;
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
 * The provider receiver is reponsible for trapping and processing provider
 * specific events sent by the Clay Tablet platform.
 * 
 * <p>
 * Implementations of this interface should contain provider specific to hook up
 * to native TMS systems etc.
 * 
 * <p>
 * @see ProviderReceiverImpl
 * @see ApproveAssetTask
 * @see CancelAssetTask
 * @see ProcessingError
 * @see RejectAssetTask
 * @see StartAssetTask
 */
@ImplementedBy(ProviderReceiverImpl.class)
public interface ProviderReceiver extends AbsEventClient {

	/**
	 * Receives an approved asset task event.
	 * 
	 * <p>
	 * This is how providers are notified of approved work.
	 * 
	 * @param event
	 *            The event to process.
	 * @throws StorageServiceException
	 * @throws EventServiceException
	 */
	public void receiveEvent(ApprovedAssetTask event)
			throws StorageServiceException, EventServiceException;

	/**
	 * Receives a cancel asset task event.
	 * 
	 * <p>
	 * This is how providers are notified of cancelled work. Asset tasks
	 * represent a work task to be performed by the provider for an asset. One
	 * of the properties is a project identified which associates related tasks
	 * and should be consulted to obtain a more complete context for the work to
	 * be performed.
	 * 
	 * @param event
	 *            The event to process.
	 */
	public void receiveEvent(CancelAssetTask event);

	/**
	 * Receives a processing error event.
	 * 
	 * <p>
	 * These are errors that occur while processing an event that has been sent
	 * to the Clay Tablet Platform. The processing error should be examined
	 * since it will usually indicate necessary action by the provider. I.e. If
	 * an asset task was submitted but could not be processed because the file
	 * had not been uploaded.
	 * 
	 * @param event
	 *            The event to process.
	 */
	public void receiveEvent(ProcessingError event);

	/**
	 * Receives a rejected asset task event.
	 * 
	 * <p>
	 * This is how providers are notified of rejected work.
	 * 
	 * @param event
	 *            The event to process.
	 * @throws StorageServiceException
	 * @throws EventServiceException
	 */
	public void receiveEvent(RejectedAssetTask event)
			throws StorageServiceException, EventServiceException;

	/**
	 * Receives a start asset task event.
	 * 
	 * <p>
	 * This is how providers are notified of assigned work. Asset tasks
	 * represent a work task to be performed by the provider for an asset. One
	 * of the properties is a project identified which associates related tasks
	 * and should be consulted to obtain a more complete context for the work to
	 * be performed.
	 * 
	 * @param event
	 *            The event to process.
	 * @throws StorageServiceException
	 * @throws EventServiceException
	 */
	public void receiveEvent(StartAssetTask event)
			throws StorageServiceException, EventServiceException;

}
