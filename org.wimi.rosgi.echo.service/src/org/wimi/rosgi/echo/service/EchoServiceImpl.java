package org.wimi.rosgi.echo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.wimi.rosgi.echo.api.EchoService;

@Designate(ocd = EchoServiceImpl.Config.class, factory = true)
// @Component(immediate = true, name = "org.wimi.rosgi.echo.service", property = { "service.exported.interfaces=*",
// "service.exported.configs=aries.fastbin" })
@Component(immediate = true, property = { "service.exported.interfaces=*", "service.exported.configs=aries.fastbin" })
public class EchoServiceImpl implements EchoService
{

	@Reference
	private LogService logService;

	@ObjectClassDefinition
	@interface Config
	{
		String name() default "InitEcho";
	}

	private String name;

	@Activate
	void activate(Config config)
	{
		System.out.println("activating");
		logService.log(LogService.LOG_INFO, "activating");
		this.name = config.name();
	}

	@Deactivate
	void deactivate()
	{
		System.out.println("deactivating");
	}

	@Override
	public String echo(String msg)
	{
		return String.format("Echo from %s -> %s", name, msg);
	}

	@Override
	public CompletableFuture<String> echoAsync(String msg)
	{
		return CompletableFuture.supplyAsync(() -> {
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				throw new RuntimeException(e);
			}
			return String.format("Echo from %s -> %s", name, msg);
		});
	}

	@Override
	public InputStream echoStream(String msg)
	{
		String formattedString = String.format("Echo from %s -> %s", name, msg);
		return new ByteArrayInputStream(formattedString.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String echoStream2(InputStream msg) throws IOException
	{
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while (( length = msg.read(buffer) ) != -1)
		{
			result.write(buffer, 0, length);
		}
		String formattedString = String.format("Echo from %s -> %s", name, result.toString("UTF-8"));
		return formattedString;
	}

}
