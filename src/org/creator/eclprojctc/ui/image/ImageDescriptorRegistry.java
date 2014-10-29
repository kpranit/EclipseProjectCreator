/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * ImageDescriptorRegistry.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.ui.image;

import java.util.HashMap;
import java.util.Iterator;

import org.creator.eclprojctc.ui.display.plugin.UIPlugin;
import org.creator.eclprojctc.wizards.NewWizardMessages;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * ImageDescriptorRegistry Author: Pranit Kumar Version: 1.0
 */
public class ImageDescriptorRegistry
{
	private HashMap<ImageDescriptor, Image> fRegistry;
	private Display fDisplay;

	/**
	 * 
	 */
	public ImageDescriptorRegistry()
	{
		this(UIPlugin.getStandardDisplay());
	}

	/**
	 * 
	 * @param display
	 */
	public ImageDescriptorRegistry(Display display)
	{
		fRegistry = new HashMap<ImageDescriptor, Image>(10);
		fDisplay = display;
		Assert.isNotNull(fDisplay);
		hookDisplay();
	}

	/**
	 * 
	 * @param descriptor
	 * @return
	 */
	public Image get(ImageDescriptor descriptor)
	{
		if (descriptor == null) descriptor = ImageDescriptor.getMissingImageDescriptor();
		Image result = (Image) fRegistry.get(descriptor);
		if (result != null) return result;
		Assert.isTrue(fDisplay == UIPlugin.getStandardDisplay(), NewWizardMessages.Wrong_Display_For_Image_Message);
		result = descriptor.createImage();
		if (result != null)
		{
			fRegistry.put(descriptor, result);
		}
		return result;
	}

	/**
	 * 
	 */
	public void dispose()
	{
		Image image;
		for (Iterator<Image> iter = fRegistry.values().iterator(); iter.hasNext(); image.dispose())
		{
			image = iter.next();
		}

		fRegistry.clear();
	}

	/**
	 * 
	 */
	private void hookDisplay()
	{
		fDisplay.disposeExec(new Runnable()
		{
			public void run()
			{
				dispose();
			}
		});
	}

}
