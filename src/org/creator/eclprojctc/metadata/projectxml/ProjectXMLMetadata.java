/**
 * Copyright (C) 2013. The Eclipse Project Creator plug-in Project.
 * ProjectXMLMetadata.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.metadata.projectxml;

import java.util.ArrayList;

import org.creator.eclprojctc.dir.bean.FolderBean;


/**
 * 
 * ProjectXMLMetadata Author: Pranit Kumar
 * Version: 1.0
 */
public class ProjectXMLMetadata
{
    private String projectName;
    private String baseDir;
    private boolean isJRELibToBeAdded;
    private ArrayList<FolderBean> srcDirList;
    private ArrayList<FolderBean> folderList;
    private ArrayList<String> linkedProjects;
    private ArrayList<String> referencedLibraries;

    /**
     * 
     * @return
     */
    public ArrayList<String> getReferencedLibraries()
    {
    	return referencedLibraries;
	}

    /**
     * 
     * @param referencedLibraries
     */
	public void setReferencedLibraries(ArrayList<String> referencedLibraries)
	{
		this.referencedLibraries = referencedLibraries;
	}

	/**
	 * 
	 * @return
	 */
	public String getBaseDir()
	{
		return baseDir;
	}

	/**
	 * 
	 * @param baseDir
	 */
	public void setBaseDir(String baseDir)
	{
		this.baseDir = baseDir;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<FolderBean> getSrcDirList()
	{
		return srcDirList;
	}

	/**
	 * 
	 * @param srcDirList
	 */
	public void setSrcDirList(ArrayList<FolderBean> srcDirList)
	{
		this.srcDirList = srcDirList;
	}

	/**
	 * 
	 * @return
	 */
	public String getProjectName()
	{
		return projectName;
	}

	/**
	 * 
	 * @param projectName
	 */
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<FolderBean> getFolderList()
	{
		return folderList;
	}

	/**
	 * 
	 * @param folderList
	 */
	public void setFolderList(ArrayList<FolderBean> folderList)
	{
		this.folderList = folderList;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getLinkedProjects()
	{
		return linkedProjects;
	}

	/**
	 * 
	 * @param linkedProjects
	 */
	public void setLinkedProjects(ArrayList<String> linkedProjects)
	{
		this.linkedProjects = linkedProjects;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isJRELibToBeAdded()
	{
		return isJRELibToBeAdded;
	}

	/**
	 * 
	 * @param isJRELibAdded
	 */
	public void setJRELibToBeAdded(boolean isJRELibAdded)
	{
		isJRELibToBeAdded = isJRELibAdded;
	}
}
