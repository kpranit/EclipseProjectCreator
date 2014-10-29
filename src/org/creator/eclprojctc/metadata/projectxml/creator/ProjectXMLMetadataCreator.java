/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * ProjectXMLMetadataCreator.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.metadata.projectxml.creator;

import java.io.File;
import java.util.ArrayList;

import org.creator.eclprojctc.dir.bean.FolderBean;
import org.creator.eclprojctc.metadata.projectxml.ProjectXMLMetadata;
import org.creator.eclprojctc.xml.parser.XMLProjectFileParser;
import org.creator.eclprojctc.xml.parser.pathfinder.RelativePathFinder;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * 
 * ProjectXMLMetadataCreator Author: Pranit Kumar Version: 1.0
 */
public class ProjectXMLMetadataCreator
{
	private ProjectXMLMetadata projectXMLMetadata;

	/**
	 * 
	 * @param file
	 * @return
	 */
	public ProjectXMLMetadata createProjectXMLMetadata(File file)
	{
		projectXMLMetadata = new ProjectXMLMetadata();
		if (file != null)
		{
			XMLProjectFileParser projectFileParser = new XMLProjectFileParser(file);
			Element rootElement = projectFileParser.getDocumentRoot();
			if (rootElement != null)
			{
				String baseDir = projectFileParser.getTextValue(rootElement, "basedir");
				String fileDirectoryPath = file.getParent();
				projectXMLMetadata.setBaseDir(prepareBaseDirValue(baseDir, fileDirectoryPath));
				projectXMLMetadata.setJRELibToBeAdded(isJREToBeAdded(projectFileParser, rootElement));
				projectXMLMetadata.setSrcDirList(createSrcBeanList(projectFileParser, rootElement));
				projectXMLMetadata.setFolderList(createFolderList(projectFileParser, rootElement));
				projectXMLMetadata.setProjectName(projectFileParser.getTextValue(rootElement, "Project-Name"));
				ArrayList<String> refrencedJarList = createStringList(projectFileParser, rootElement, "Referenced-Libraries", "referenced-jar");
				RelativePathFinder relativePathFinder = new RelativePathFinder();
				for (int i = 0; i < refrencedJarList.size(); i++)
				{
					String referencedJar = relativePathFinder.findAbsolutePath((String) refrencedJarList.get(i), projectXMLMetadata.getBaseDir());
					refrencedJarList.set(i, referencedJar);
				}

				projectXMLMetadata.setReferencedLibraries(refrencedJarList);
				projectXMLMetadata.setLinkedProjects(createStringList(projectFileParser, rootElement, "Linked-Projects", "project-name"));
			}
		}
		return projectXMLMetadata;
	}

	/**
	 * 
	 * @param parser
	 * @param rootElement
	 * @return
	 */
	private boolean isJREToBeAdded(XMLProjectFileParser parser, Element rootElement)
	{
		String isJREToBeAdded = parser.getTextValue(rootElement, "isJreLibAdded");
		return "YES".equalsIgnoreCase(isJREToBeAdded);
	}

	/**
	 * 
	 * @param baseDirTagValue
	 * @param fileDirectoryPath
	 * @return
	 */
	private String prepareBaseDirValue(String baseDirTagValue, String fileDirectoryPath)
	{
		String baseDir = fileDirectoryPath;
		if (baseDirTagValue != null && baseDirTagValue.length() != 0) if (".".equals(baseDirTagValue))
		{
			baseDir = fileDirectoryPath;
		}
		else
		{
			if (baseDirTagValue.startsWith("/") || baseDirTagValue.startsWith("\\")) baseDirTagValue = baseDirTagValue.substring(1,
					baseDirTagValue.length());
			if (baseDirTagValue.endsWith("/") || baseDirTagValue.endsWith("\\")) baseDirTagValue = baseDirTagValue.substring(0,
					baseDirTagValue.length() - 1);
			baseDir = (new StringBuilder(String.valueOf(fileDirectoryPath))).append("/").append(baseDirTagValue).toString();
		}
		return baseDir;
	}

	/**
	 * 
	 * @param parser
	 * @param rootElement
	 * @param parentTag
	 * @param childTag
	 * @return
	 */
	private ArrayList<String> createStringList(XMLProjectFileParser parser, Element rootElement, String parentTag, String childTag)
	{
		ArrayList<String> stringList = new ArrayList<String>();
		Element parentNode = parser.getFirstNodeForTagGiven(rootElement, parentTag);
		if (parentNode != null)
		{
			NodeList childNodeList = parser.getChildNodesForGivenNodeWithName(parentNode, childTag);
			if (childNodeList != null)
			{
				int childNodeCount = childNodeList.getLength();
				for (int i = 0; i < childNodeCount; i++)
				{
					Element childNode = (Element) childNodeList.item(i);
					stringList.add(childNode.getFirstChild().getNodeValue());
				}

			}
		}
		return stringList;
	}

	/**
	 * 
	 * @param parser
	 * @param rootElement
	 * @return
	 */
	private ArrayList<FolderBean> createFolderList(XMLProjectFileParser parser, Element rootElement)
	{
		Element sourceDirsNode = parser.getFirstNodeForTagGiven(rootElement, "Directories");
		NodeList srcNodes = parser.getChildNodesForGivenNodeWithName(sourceDirsNode, "folder");
		return createFolderBeanList(parser, srcNodes);
	}

	/**
	 * 
	 * @param parser
	 * @param rootElement
	 * @return
	 */
	private ArrayList<FolderBean> createSrcBeanList(XMLProjectFileParser parser, Element rootElement)
	{
		Element sourceDirsNode = parser.getFirstNodeForTagGiven(rootElement, "Source-Dirs");
		NodeList srcNodes = parser.getChildNodesForGivenNodeWithName(sourceDirsNode, "src");
		return createFolderBeanList(parser, srcNodes);
	}

	/**
	 * 
	 * @param parser
	 * @param folders
	 * @return
	 */
	private ArrayList<FolderBean> createFolderBeanList(XMLProjectFileParser parser, NodeList folders)
	{
		ArrayList<FolderBean> folderBeanList = new ArrayList<FolderBean>();
		RelativePathFinder relativePathFinder = new RelativePathFinder();
		if (folders != null)
		{
			for (int i = 0; i < folders.getLength(); i++)
			{
				Element srcNode = (Element) folders.item(i);
				String folderName = parser.getTextValue(srcNode, "name");
				String folderLocatilon = parser.getTextValue(srcNode, "location");
				FolderBean folderBean = new FolderBean();
				folderLocatilon = relativePathFinder.findAbsolutePath(folderLocatilon, projectXMLMetadata.getBaseDir());
				folderBean.setFolderLocation(folderLocatilon);
				folderBean.setFoldername(folderName);
				folderBeanList.add(folderBean);
			}

		}
		return folderBeanList;
	}

	/**
	 * 
	 * @return
	 */
	public ProjectXMLMetadata getProjectXMLMetadata()
	{
		return projectXMLMetadata;
	}
}
