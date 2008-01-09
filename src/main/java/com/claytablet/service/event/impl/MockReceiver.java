package com.claytablet.service.event.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.ConnectionContext;
import com.claytablet.model.event.Account;
import com.claytablet.model.event.platform.ApprovedAssetTask;
import com.claytablet.model.event.platform.CanceledAssetTask;
import com.claytablet.model.event.platform.CanceledSupportAsset;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.RejectedAssetTask;
import com.claytablet.model.event.platform.StartAssetTask;
import com.claytablet.model.event.platform.StartSupportAsset;
import com.claytablet.model.event.provider.SubmitAssetTask;
import com.claytablet.provider.SourceAccountProvider;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProviderReceiver;
import com.claytablet.service.event.stubs.MockStub;
import com.claytablet.storage.service.StorageClientService;
import com.claytablet.storage.service.StorageServiceException;
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
 * This is the mock implementation for the provider receiver.
 * 
 * <p>
 * @see ConnectionContext
 * @see SourceAccountProvider
 * @see MockStub
 * @see StorageClientService
 */
@Singleton
public class MockReceiver implements ProviderReceiver {

	private final Log log = LogFactory.getLog(getClass());

	private final ConnectionContext context;

	private final SourceAccountProvider sap;

	private final MockStub mockStub;

	private StorageClientService storageClientService;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param context
	 * @param sap
	 * @param storageClientService
	 * @param providerSender
	 * @param mockStub
	 */
	@Inject
	public MockReceiver(final ConnectionContext context,
			final SourceAccountProvider sap, final MockStub mockStub,
			StorageClientService storageClientService) {

		this.context = context;
		this.sap = sap;
		this.storageClientService = storageClientService;
		this.mockStub = mockStub;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.ApprovedAssetTask)
	 */
	public void receiveEvent(ApprovedAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// check to see if new content was submitted with the event
		if (event.isWithContent()) {
			log.debug("A new asset task revision was sent with the approval.");

			// initialize the storage client for the source account.
			initStorageClient();

			log.debug("Download the latest asset task revision for: "
					+ event.getAssetTaskId());
			String downloadPath = storageClientService
					.downloadLatestAssetTaskVersion(event.getAssetTaskId(),
							"./files/received/");

			log.debug("Downloaded an asset task version file to: "
					+ downloadPath);
		}

		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.CanceledAssetTask)
	 */
	public void receiveEvent(CanceledAssetTask event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProviderReceiver#receiveEvent(com.claytablet.model.event.platform.CanceledSupportAsset)
	 */
	public void receiveEvent(CanceledSupportAsset event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// TODO - provider integration code goes here.

		// If an exception is thrown the event will remain on the queue.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProvider#receiveEvent(com.claytablet.model.event.platform.ProcessingError)
	 */
	public void receiveEvent(ProcessingError event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing

		log.error(event.getErrorMessage());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ReceiverProvider#receiveEvent(com.claytablet.model.event.producer.RejectedAssetTask)
	 */
	public void receiveEvent(RejectedAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// check to see if new content was submitted with the event
		if (event.isWithContent()) {
			log.debug("A new asset task revision was sent with the rejection.");

			// initialize the storage client for the source account.
			initStorageClient();

			log.debug("Download the latest asset task revision for: "
					+ event.getAssetTaskId());
			String downloadPath = storageClientService
					.downloadLatestAssetTaskVersion(event.getAssetTaskId(),
							"./files/received/");

			log.debug("Downloaded an asset task version file to: "
					+ downloadPath);

			// If it's a text file, convert all of the text to lower case
			if (event.getFileExt().equals("txt")) {
				log.debug("Convert all of the text to lower case.");
				try {
					String contents = FileUtils.readFileToString(new File(
							downloadPath));
					contents = contents.toLowerCase();
					FileUtils.writeStringToFile(new File(downloadPath),
							contents);
				} catch (IOException e) {
					log.error(e);
				}
			}

			log.debug("Submit the mock event.");
			SubmitAssetTask event2 = new SubmitAssetTask();
			event2.setAssetTaskId(event.getAssetTaskId());
			event2.setNativeState("Mock State");

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProvider#receiveEvent(com.claytablet.model.event.platform.StartAssetTask)
	 */
	public void receiveEvent(StartAssetTask event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// initialize the storage client for the source account.
		initStorageClient();

		log.debug("Download the latest asset task revision for: "
				+ event.getAssetTaskId());
		String downloadPath = storageClientService
				.downloadLatestAssetTaskVersion(event.getAssetTaskId(),
						"./files/received/");

		log.debug("Downloaded an asset task version file to: " + downloadPath);

		// If it's a text file, convert all of the text to upper case
		if (event.getFileExt().equals("txt")) {
			log.debug("Convert all of the text to upper case.");
			try {
				String contents = FileUtils.readFileToString(new File(
						downloadPath));
				contents = contents.toUpperCase();
				FileUtils.writeStringToFile(new File(downloadPath), contents);
			} catch (IOException e) {
				log.error(e);
			}
		}

		log.debug("Submit the mock event.");
		SubmitAssetTask event2 = new SubmitAssetTask();
		event2.setAssetTaskId(event.getAssetTaskId());
		event2.setNativeState("Mock State");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProviderReceiver#receiveEvent(com.claytablet.model.event.platform.StartSupportAsset)
	 */
	public void receiveEvent(StartSupportAsset event)
			throws StorageServiceException, EventServiceException {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// initialize the storage client for the source account.
		initStorageClient();

		log.debug("Download the support asset for: "
				+ event.getSupportAssetId());
		String downloadPath = storageClientService.downloadSupportAsset(event
				.getSupportAssetId(), event.getFileExt(), "./files/received/");

		log.debug("Downloaded a support asset file to: " + downloadPath);

	}

	/**
	 * Initializes the storage client with the source account values
	 * (credentials and defaults).
	 */
	private void initStorageClient() {

		log.debug("Retrieve the source account from the provider.");
		Account sourceAccount = sap.get();

		log.debug("Initialize the storage client service.");
		storageClientService.setPublicKey(sourceAccount.getPublicKey());
		storageClientService.setPrivateKey(sourceAccount.getPrivateKey());
		storageClientService.setStorageBucket(sourceAccount.getStorageBucket());
		storageClientService.setDefaultLocalSourceDirectory(context
				.getSourceDirectory());
		storageClientService.setDefaultLocalTargetDirectory(context
				.getTargetDirectory());
	}

}
