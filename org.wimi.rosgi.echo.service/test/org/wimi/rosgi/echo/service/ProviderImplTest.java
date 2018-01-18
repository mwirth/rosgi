package org.wimi.rosgi.echo.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.wimi.rosgi.echo.service.EchoServiceImpl;

/*
 * Example JUNit test case
 *
 */

public class ProviderImplTest {

	/*
	 * Example test method
	 */

	@Test
	public void simple() {
		EchoServiceImpl impl = new EchoServiceImpl();
		assertNotNull(impl);
	}

}
