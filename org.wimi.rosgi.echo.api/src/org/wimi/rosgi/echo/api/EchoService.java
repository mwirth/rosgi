package org.wimi.rosgi.echo.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This is an example OSGi enRoute bundle that has a component that implements an API.
 */

@ProviderType
public interface EchoService
{

	public String echo(String msg);

	public CompletableFuture<String> echoAsync(String msg);

	public InputStream echoStream(String msg);

	public String echoStream2(InputStream msg) throws IOException;

}
