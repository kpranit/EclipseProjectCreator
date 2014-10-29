/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * NewWizardMessages.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.wizards;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * NewWizardMessages Author: Pranit Kumar
 * Version: 1.0
 */
public class NewWizardMessages extends NLS
{
    private static final String BUNDLE_NAME = "org.creator.eclprojctc.wizards.messages";
    public static String CustomProjectNewWizard_Create_something_custom;
    public static String CustomProjectNewWizard_Custom_Plugin_Project;
    public static String Invalid_Location_Error_Message;
    public static String New_Project_Page_One_Title;
    public static String New_Project_Page_One_Description;
    public static String Invalid_Project_name_Error_Message;
    public static String Empty_loocation_Error_Message;
    public static String Project_Already_Exist_Error_Message;
    public static String Project_File_Message;
    public static String Wrong_Display_For_Image_Message;

    static 
    {
        NLS.initializeMessages(BUNDLE_NAME, NewWizardMessages.class);
    }
	

}
