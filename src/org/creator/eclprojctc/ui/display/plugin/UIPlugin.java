/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * UIPlugin.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.ui.display.plugin;

import org.eclipse.swt.widgets.Display;

/**
 * 
 * UIPlugin Author: Pranit Kumar
 * Version: 1.0
 */
public class UIPlugin
{

	/**
	 * 
	 * @return
	 */
    public static Display getStandardDisplay()
    {
        Display display = Display.getCurrent();
        if(display == null)
        {
            display = Display.getDefault();        	
        }
        return display;
    }
}
