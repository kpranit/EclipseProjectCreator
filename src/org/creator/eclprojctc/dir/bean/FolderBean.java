/**
 * Copyright (C) 2013. The Eclipse Project Creator plug-in Project.
 * FolderBean.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.dir.bean;

/**
 * 
 * FolderBean Author: Pranit Kumar
 * Version: 1.0
 */
public class FolderBean
{
    private String folderName;
    private String folderLocation;

    /**
     * 
     */
    public FolderBean()
    {
    }

    /**
     * 
     * @param srcFoldername
     */
    public void setFoldername(String srcFoldername)
    {
        folderName = srcFoldername;
    }

    /**
     * 
     * @return
     */
    public String getFoldername()
    {
        return folderName;
    }

    /**
     * 
     * @param folderLocation
     */
	public void setFolderLocation(String folderLocation)
    {
        this.folderLocation = folderLocation;
    }

	/**
	 *
	 * @return
	 */
	public String getFolderLocation()
    {
        return folderLocation;
    }

}