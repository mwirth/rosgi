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
@Component(immediate = true, name = "org.wimi.rosgi.echo.service", property = { "service.exported.interfaces=*",
	"service.exported.configs=aries.fastbin" })
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
		logService.log(LogService.LOG_INFO, "activating");
		this.name = config.name();
	}

	@Deactivate
	void deactivate()
	{
		logService.log(LogService.LOG_INFO, "deactivating");
	}

	@Override
	public String echo(String msg)
	{
		logService.log(LogService.LOG_INFO, "echo called -> " + msg);
		return String.format("Echo from %s -> %s", name, msg);
	}

	@Override
	public CompletableFuture<String> echoAsync(String msg)
	{
		logService.log(LogService.LOG_INFO, "echoAsync called -> " + msg);
		return CompletableFuture.supplyAsync(() -> {
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				throw new RuntimeException(e);
			}
			String retString = String.format("Echo from %s -> %s", name, msg);
			logService.log(LogService.LOG_INFO, "echoAsync return -> " + retString);
			return retString;
		});
	}

	@Override
	public InputStream echoReturnStream(String msg)
	{
		logService.log(LogService.LOG_INFO, "echoStream called -> " + msg);
		String retString = String.format("Echo from %s -> %s", name, msg);
		logService.log(LogService.LOG_INFO, "echoStream return -> " + retString);
		return new ByteArrayInputStream(retString.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String echoParamStream(InputStream msg) throws IOException
	{
		logService.log(LogService.LOG_INFO, "echoStream2 called -> " + msg);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while (( length = msg.read(buffer) ) != -1)
		{
			result.write(buffer, 0, length);
		}
		String restString = String.format("Echo from %s -> %s", name, result.toString("UTF-8"));
		logService.log(LogService.LOG_INFO, "echoStream2 return -> " + restString);
		return restString;
	}

}
