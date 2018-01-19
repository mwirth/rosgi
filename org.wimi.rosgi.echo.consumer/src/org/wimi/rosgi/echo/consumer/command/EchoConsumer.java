package org.wimi.rosgi.echo.consumer.command;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.wimi.rosgi.echo.api.EchoService;

@Component(immediate = true)
public class EchoConsumer
{

	EchoService echoService;

	@Activate
	public void activate() throws IOException
	{
		System.out.println("starting EchoConsumer");
	}

	@Deactivate
	private void stop()
	{
		System.out.println("stopping EchoConsumer");
	}

	public void activate2() throws IOException
	{
		System.out.println("Sending to echo service: echo");
		System.out.println(echoService.echo("Good morning"));

		System.out.println("Sending to echo service: async");
		echoService.echoAsync("Async Good morning").thenRun(() -> System.out.println("Good morning Async"));

		System.out.println("Sending to echo service: stream");
		InputStream inputStream = echoService.echoReturnStream("Good morning received as a stream");
		try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
		{
			System.out.println(r.readLine());
		}

		System.out.println("Sending to echo service: stream2");
		System.out.println(echoService
			.echoParamStream(new ByteArrayInputStream("Good morning send as a stream".getBytes(StandardCharsets.UTF_8))));

	}

	@Reference
	public void setEchoService(EchoService echoService) throws IOException
	{
		this.echoService = echoService;
	}
}
