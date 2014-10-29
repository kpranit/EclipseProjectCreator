/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * RelativePathFinder.java Created on Mar 19, 2011 
 * 
 * This file is part of Eclipse Project Creator plug-in Project.
 *          
 * Eclipse Project Creator is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * Eclipse Project Creator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * See the GNU General Public License for more details.
 */
package org.creator.eclprojctc.xml.parser.pathfinder;

/**
 * 
 * RelativePathFinder Author: Pranit Kumar
 * Version: 1.0
 */
public class RelativePathFinder
{

    public static final String FORWARD_SLASH = "/";
    public static final String BACKWARD_SLASH = "\\";
    public static final String BASE_DIR_TAG = "{basedir}";

    /**
     *
     * @param path
     * @param baseDirTagValue
     * @return
     */
    public String findAbsolutePath(String path, String baseDirTagValue)
    {
        int pathIndex = -1;
        if((pathIndex = path.indexOf("{basedir}")) != -1)
        {
            int indexToBeTrimmed = pathIndex + "{basedir}".length();
            path = (new StringBuilder(String.valueOf(baseDirTagValue))).append(path.substring(indexToBeTrimmed)).toString();
        }
        return path;
    }
}
