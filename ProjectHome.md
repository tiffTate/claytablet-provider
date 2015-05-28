This is the base / example client SDK for providers to the Clay Tablet Technologies Translation Platform.

Use this as a starting point when building a new provider system client.

Typical provider systems are Translation Management Systems.

[See Architecture Overview](http://code.google.com/p/claytablet-provider/wiki/ArchitectureOverview)

In order to properly test and use the client you first need to request an account. Please send account requests to [mailto:rcoleman@clay-tablet.com](mailto:rcoleman@clay-tablet.com). Please include your name, your company name, and that you require a provider account in the request.

The Client SDK API Documentation can be found in the downloads section.

Once you have downloaded the source and are beginning integration you will want to first drop the source.xml account file we send you into:
/src/main/resources/account

The source code is organized similar to a typical Apache project. ./src/main/java/ contains the source for the main application, and ./src/test/java contain the source for the various test classes. Ant is used to compile, test, and can be use to launch the cron and send some mock test events & files to the platform for processing.

You can retrieve the source code using subversion. For details, please click on the "Source" tab above.

The following are main points of interest for integration work:

1) [Mock Cron](http://claytablet-provider.googlecode.com/svn/trunk/src/main/java/com/claytablet/app/MockCron.java) - This runs a never ending loop that will call the receive to check for new platform events and the poller to check for state changes in the connected system (TMS).

2) [Mock Receiver](http://claytablet-provider.googlecode.com/svn/trunk/src/main/java/com/claytablet/service/event/impl/MockReceiver.java) - This checks the incoming message queue for new events from the platform and performs whatever work is necessary, like downloading attachments and calling the connection stub (interface to the TMS).


3) [Mock State Poller](http://claytablet-provider.googlecode.com/svn/trunk/src/main/java/com/claytablet/service/event/impl/MockStatePoller.java) - This checks the connected system via the connection stub for state changes in the TMS, and sends important update events and files to the platform queue.


4) [Mock Stub](http://claytablet-provider.googlecode.com/svn/trunk/src/main/java/com/claytablet/service/event/stubs/MockStub.java) - This is the interface to the connected system (TMS).

5)
[Mock Module](http://claytablet-provider.googlecode.com/svn/trunk/src/main/java/com/claytablet/module/MockModule.java) - Guice DI configuration module. Same idea as a Spring applicationContext.xml.


**If you need to build a custom client in a language other than Java please see:
[Building a Custom Client](http://code.google.com/p/claytablet-provider/wiki/BuildingCustomClient)**