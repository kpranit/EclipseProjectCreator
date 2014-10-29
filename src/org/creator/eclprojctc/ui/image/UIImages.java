/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * UIImages.java Created on Mar 19, 2011 
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

import java.net.URL;

import org.creator.eclprojctc.ui.display.plugin.UIPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * 
 * UIImages Author: Pranit Kumar
 * Version: 1.0
 */
public class UIImages
{
    private static ImageRegistry imageRegistry;
    private static ImageDescriptorRegistry imageDescriptorRegistry;
    private static String ICONS_PATH = "icons/";

    /**
     * 
     */
    private static void declareImages()
    {
        declareRegistryImage("icons", (new StringBuilder(String.valueOf(ICONS_PATH))).append("epclogo.png").toString());
    }

    /**
     * 
     * @param key
     * @param path
     */
    private static final void declareRegistryImage(String key, String path)
    {
        ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
        Bundle bundle = Platform.getBundle("org.creator.eclprojctc");
        URL url = null;
        if(bundle != null)
        {
            url = FileLocator.find(bundle, new Path(path), null);
            desc = ImageDescriptor.createFromURL(url);
        }
        imageRegistry.put(key, desc);
    }

    /**
     * 
     * @return
     */
    public static ImageRegistry getImageRegistry()
    {
        if(imageRegistry == null)
        {
            initializeImageRegistry();        	
        }
        return imageRegistry;
    }

    /**
     * 
     * @return
     */
    public static ImageRegistry initializeImageRegistry()
    {
        imageRegistry = new ImageRegistry(UIPlugin.getStandardDisplay());
        declareImages();
        return imageRegistry;
    }

    /**
     * 
     * @param key
     * @return
     */
    public static Image getImage(String key)
    {
        return getImageRegistry().get(key);
    }

    /**
     * 
     * @param key
     * @return
     */
    public static ImageDescriptor getImageDescriptor(String key)
    {
        return getImageRegistry().getDescriptor(key);
    }

    /**
     * 
     */
    public static void disposeImageDescriptorRegistry()
    {
        if(imageDescriptorRegistry != null)
        {
            imageDescriptorRegistry.dispose();        	
        }
    }

    /**
     *
     * @return
     */
    public static synchronized boolean isInitialized()
    {
        return imageDescriptorRegistry != null;
    }
}
