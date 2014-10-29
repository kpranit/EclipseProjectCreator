/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * XMLProjectFileParser.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.xml.parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * XMLProjectFileParser Author: Pranit Kumar Version: 1.0
 */
public class XMLProjectFileParser
{
	private File file;

	/**
	 * 
	 * @param file
	 */
	public XMLProjectFileParser(File file)
	{
		this.file = file;
	}

	/**
	 * 
	 * @return
	 */
	private Document createXMLDocument()
	{
		Document document = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			document = docBuilder.parse(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 
	 * @return
	 */
	public Element getDocumentRoot()
	{
		Document document = createXMLDocument();
		Element documentRoot = document.getDocumentElement();
		return documentRoot;
	}

	/**
	 * 
	 * @param node
	 * @param tagName
	 * @return
	 */
	public NodeList getChildNodesForGivenNodeWithName(Element node, String tagName)
	{
		NodeList childNodes = null;
		if (node != null) childNodes = node.getElementsByTagName(tagName);
		return childNodes;
	}

	/**
	 * 
	 * @param documentRoot
	 * @param tagName
	 * @return
	 */
	public Element getFirstNodeForTagGiven(Element documentRoot, String tagName)
	{
		Element firstNode = null;
		NodeList nodeList = documentRoot.getElementsByTagName(tagName);
		if (nodeList != null) firstNode = (Element) nodeList.item(0);
		return firstNode;
	}

	/**
	 * 
	 * @param element
	 * @param nodeName
	 * @return
	 */
	public String getTextValue(Element element, String nodeName)
	{
		String textVal = "";
		NodeList nodeList = element.getElementsByTagName(nodeName);
		if (nodeList != null && nodeList.getLength() > 0)
		{
			Element childElement = (Element) nodeList.item(0);
			textVal = childElement.getFirstChild().getNodeValue();
		}
		return textVal;
	}
}
