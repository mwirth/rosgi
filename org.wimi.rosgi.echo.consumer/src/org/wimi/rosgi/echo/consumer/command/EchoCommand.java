package org.wimi.rosgi.echo.consumer.command;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.wimi.rosgi.echo.api.EchoService;

import osgi.enroute.debug.api.Debug;

@Component(service = EchoCommand.class, property = { Debug.COMMAND_SCOPE + "=rosgi", Debug.COMMAND_FUNCTION + "=echo",
	Debug.COMMAND_FUNCTION + "=echoAsync", Debug.COMMAND_FUNCTION + "=echoStream", Debug.COMMAND_FUNCTION + "=echoStream2"

})
public class EchoCommand
{
	// @Reference
	// private LogService log;

	private EchoService echoService;

	public void echo(String message)
	{
		System.out.println("Sending to echo service: echo -> " + message);
		System.out.println(echoService.echo(message));
	}

	public void echoAsync(String message)
	{
		System.out.println("Sending to echo service: async -> " + message);
		echoService.echoAsync(message).thenRun(() -> System.out.println("Good morning Async"));
	}

	public void echoStream(String message) throws IOException
	{
		System.out.println("Sending to echo service: stream -> " + message);
		InputStream inputStream = echoService.echoStream(message);
		try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
		{
			System.out.println(r.readLine());
		}
	}

	public void echoStream2(String message) throws IOException
	{
		System.out.println("Sending to echo service: stream2 - >" + message);
		System.out.println(echoService.echoStream2(new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8))));

	}

	@Reference
	public void setEchoService(EchoService echoService) throws IOException
	{
		this.echoService = echoService;
	}
}
