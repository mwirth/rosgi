**Repository to test calling remote osgi services using zookeeper and aries.fastbin**

The example is based on the *Apache aries-rsa example* <http://github.com/apache/aries-rsa> but does not use karaf.


## how to use

1. Start `debug_service.bndrun`
2. Start `debug_consumer.bndrun`
3. in the consumer execute one of the following gogo commands

		rosgi:echo message
		rosgi:echoAsync message
		rosgi:echoReturnStream message
		rosgi:echoParamStream 'calling using stream'

4. the name can be changed during runtime in `org.wimi.rosgi.echo.service.cfg`
5. stop the service `org.wimi.rosgi.echo.service`  --> the consumer gets informed and the gogo commands are not available anymore
6. start the service `org.wimi.rosgi.echo.service` --> the consumer gets infomred and the gogo commands are available again
7. the bndrund-files can also be exported to a jar and started standalone. The file structure can look link this:

		.
		|__consumer
		| |__configuration_consumer
		| | |__configuration.json
		| | |__org.apache.aries.rsa.discovery.zookeeper.cfg
		| | |__org.apache.aries.rsa.provider.fastbin.cfg
		| | |__org.ops4j.pax.logging.cfg
		| | |__org.wimi.rosgi.echo.service.cfg
		| |__consumer.jar
		| |__log
		| | |__log-consumer.log
		| | |__wimi-consumer.log
		|__service
		| |__configuration_service
		| | |__configuration.json
		| | |__org.apache.aries.rsa.discovery.zookeeper.cfg
		| | |__org.apache.aries.rsa.discovery.zookeeper.server.cfg
		| | |__org.apache.aries.rsa.provider.fastbin.cfg
		| | |__org.ops4j.pax.logging.cfg
		| |__log
		| | |__wimi.log
		| |__service.jar


