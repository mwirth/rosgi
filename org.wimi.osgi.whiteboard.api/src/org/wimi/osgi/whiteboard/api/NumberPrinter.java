package org.wimi.osgi.whiteboard.api;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This is an example OSGi enRoute bundle that has a component that implements an API.
 */

@ProviderType
public interface NumberPrinter
{
	String printNumber(long value);

	String getLabel();
}
