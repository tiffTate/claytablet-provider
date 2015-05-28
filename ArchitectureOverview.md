[![](http://farm3.static.flickr.com/2391/2037232863_f21b1d90b1.jpg)](http://www.flickr.com/photos/rycoleman/2037232863/)

# Architecture Overview #

## Introduction ##

> This page outlines the high-level architecture of Clay Tablet 2.0.

## High-Level Architecture ##

> [![](http://farm3.static.flickr.com/2307/2037930856_3c07191100_o.jpg)](http://www.flickr.com/photos/rycoleman/2037930856/)

> In the context of implementation all source systems (CMS, ECMS, Data Feeds, etc.) are considered "Producer Systems" and all destination systems (TMS, GMS, SBMT, RBMT, etc.) are considered "Provider Systems".

> You are currently viewing the "Provider" open source client documentation & code.

> Clay Tablet maintains a centralized Platform application that consists of an Inbound Message Queue, where all requests from both Producers & Providers are sent, and a series of Processing Servers which manage the routing and handling of these requests.

> Each unique connected system also has its own dedicated message queue and remote file repository which it uses to receive events and transfer files to the Clay Tablet Platform.

### Hierarchy ###

> [![](http://farm3.static.flickr.com/2012/2038001398_7645711a99_o.jpg)](http://www.flickr.com/photos/rycoleman/2038001398/)

> A "Provider" is a single client or entity responsible for one or more producer systems.

> Each provider system has a unique **account** which is associated with a specific provider.

### Anatomy of a Connection ###

> [![](http://farm3.static.flickr.com/2385/2037204769_29d6a48e6f_o.jpg)](http://www.flickr.com/photos/rycoleman/2037204769/)

> Clay Tablet’s high-availability asynchronous platform uses Message Queues **(1)** to communicate with connected systems on both the content management and translation technology sides.

> The connected system interacts with these message queues through REST-based (SOAP also supported) web service calls. **(2)**

> Outgoing events are transmitted to Clay Tablet’s platform message queue **(3)** and each connected system has their own dedicated inbound message queue **(4)** for receiving events from the Clay Tablet platform.