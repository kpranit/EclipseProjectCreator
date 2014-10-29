/**
 * Copyright (C) 2013. The COMPANY_NAME Home Media Server Project
 * EclProjectCreator.java Created on Sep 19, 2013 
 * 
 * The copyright of this code and related documentation together with any other associated intellectual property rights
 * are vested in COMPANY_NAME and may not be used except in accordance with the terms of the license that you have 
 * entered into with COMPANY_NAME. Use of this material without an express license from COMPANY_NAME shall be an
 * infringement of copyright and any other intellectual property rights that may be incorporated with this material.
 */
package org.creator.eclprojctc.creator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.creator.eclprojctc.dir.bean.FolderBean;
import org.creator.eclprojctc.metadata.projectxml.ProjectXMLMetadata;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * 
 * EclProjectCreator Author: Pranit Kumar Version: 1.0
 */
public class EclProjectCreator
{

	/**
	 * 
	 * @param projectName
	 * @param monitor
	 * @param projectXMLMetadata
	 * @return
	 * @throws CoreException
	 */
	public IJavaProject createJavaProject(String projectName, IProgressMonitor monitor, ProjectXMLMetadata projectXMLMetadata)
			throws CoreException
	{
		IJavaProject javaProject = createJavaProject(projectName, monitor);

		if (projectXMLMetadata.isJRELibToBeAdded())
		{
			addVariableEntry(javaProject, new Path("JRE_LIB"), new Path("JRE_SRC"), new Path("JRE_SRCROOT"), monitor);
		}
		
		addReferencedProjects(javaProject, projectXMLMetadata, monitor);
		addRefrencedLibraryToProject(projectXMLMetadata, javaProject, monitor);
		createSourceDirectories(javaProject, projectXMLMetadata, monitor);
		createFolderForProjects(javaProject, projectXMLMetadata, monitor);
		monitor.done();
		return javaProject;
	}

	/**
	 * 
	 * @param projectXMLMetadata
	 * @param javaProject
	 * @param monitor
	 * @throws CoreException
	 */
	private void addRefrencedLibraryToProject(ProjectXMLMetadata projectXMLMetadata, IJavaProject javaProject, IProgressMonitor monitor)
			throws CoreException
	{
		ArrayList<String> referencedLib = projectXMLMetadata.getReferencedLibraries();
		if (referencedLib != null)
		{
			for (int i = 0; i < referencedLib.size(); i++)
			{
				String referencedJar = (String) referencedLib.get(i);
				File classpathEntry = new File(referencedJar);
				if (classpathEntry.isFile())
				{
					addLibrary(javaProject, new Path(classpathEntry.getAbsolutePath()), monitor);
				}
				else
				{
					addContainer(javaProject, new Path(classpathEntry.getAbsolutePath()), monitor);
				}
			}
		}
	}

	/**
	 * 
	 * @param javaProject
	 * @param projectXMLMetadata
	 * @param monitor
	 * @throws CoreException
	 */
	private void createSourceDirectories(IJavaProject javaProject, ProjectXMLMetadata projectXMLMetadata, IProgressMonitor monitor)
			throws CoreException
	{
		ArrayList<FolderBean> srcBeanList = projectXMLMetadata.getSrcDirList();
		if (srcBeanList != null)
		{
			for (int i = 0; i < srcBeanList.size(); i++)
			{
				String srcDir = ((FolderBean) srcBeanList.get(i)).getFolderLocation();
				String srcDirectoryName = ((FolderBean) srcBeanList.get(i)).getFoldername();
				String destDirPath = srcDir;
				String destDirName = srcDirectoryName;
				addSourceContainer(javaProject, srcDirectoryName, srcDir, destDirName, destDirPath, monitor);
			}
		}
	}

	/**
	 * 
	 * @param javaProject
	 * @param projectXMLMetadata
	 * @param monitor
	 * @throws CoreException
	 */
	private void createFolderForProjects(IJavaProject javaProject, ProjectXMLMetadata projectXMLMetadata, IProgressMonitor monitor)
			throws CoreException
	{
		ArrayList<FolderBean> srcBeanList = projectXMLMetadata.getFolderList();
		if (srcBeanList != null)
		{
			for (int i = 0; i < srcBeanList.size(); i++)
			{
				String folderLocation = ((FolderBean) srcBeanList.get(i)).getFolderLocation();
				String folderName = ((FolderBean) srcBeanList.get(i)).getFoldername();
				IPath folderPath = new Path(folderLocation);
				addFolderToProject(javaProject, folderName, folderPath, monitor);
			}
		}
	}

	/**
	 * 
	 * @param javaProject
	 * @param projectXMLMetadata
	 * @param monitor
	 * @throws JavaModelException
	 */
	private void addReferencedProjects(IJavaProject javaProject, ProjectXMLMetadata projectXMLMetadata, IProgressMonitor monitor)
			throws JavaModelException
	{
		ArrayList<String> projectsTobelinked = projectXMLMetadata.getLinkedProjects();
		List<IJavaProject> javaProjects = Arrays.asList(javaProject.getJavaModel().getJavaProjects());
		if (projectsTobelinked != null)
		{
			for (int i = 0; i < javaProjects.size(); i++)
			{
				if (!javaProject.getElementName().equals(((IJavaProject) javaProjects.get(i)).getElementName())
						&& projectsTobelinked.contains(((IJavaProject) javaProjects.get(i)).getElementName()))
				{
					addProjects(javaProject, ((IJavaProject) javaProjects.get(i)).getPath(), monitor);
				}
			}
		}
	}

	/**
	 * 
	 * @param projectName
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	private IJavaProject createJavaProject(String projectName, IProgressMonitor monitor) throws CoreException
	{
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		if (!project.exists())
		{
				project.create(monitor);
		}
		else
		{
			project.refreshLocal(2, monitor);
		}
		if (!project.isOpen())
		{
			project.open(monitor);
		}
		if (!project.hasNature("org.eclipse.jdt.core.javanature"))
		{
			addNatureToProject(project, "org.eclipse.jdt.core.javanature", monitor);
		}
		IJavaProject jproject = JavaCore.create(project);
		jproject.setRawClasspath(new IClasspathEntry[0], monitor);
		return jproject;
	}

	/**
	 * 
	 * @param proj
	 * @param natureId
	 * @param monitor
	 * @throws CoreException
	 */
	private void addNatureToProject(IProject proj, String natureId, IProgressMonitor monitor) throws CoreException
	{
		IProjectDescription description = proj.getDescription();
		String prevNatures[] = description.getNatureIds();
		String newNatures[] = new String[prevNatures.length + 1];
		System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
		newNatures[prevNatures.length] = natureId;
		description.setNatureIds(newNatures);
		proj.setDescription(description, monitor);
	}

	/**
	 * 
	 * @param jproject
	 * @param srcName
	 * @param srcPath
	 * @param outputName
	 * @param outputPath
	 * @param monitor
	 * @throws CoreException
	 */
	private void addSourceContainer(IJavaProject jproject, String srcName, String srcPath, String outputName, String outputPath,
			IProgressMonitor monitor) throws CoreException
	{
		IProject project = jproject.getProject();
		IContainer container = null;
		if (srcName == null || srcName.length() == 0)
		{
			container = project;
		}
		else
		{
			IFolder folder = project.getFolder(srcName);
			if (!folder.exists())
			{
				folder.createLink(new Path(srcPath), 16, monitor);
			}
			container = folder;
		}
		IPackageFragmentRoot root = jproject.getPackageFragmentRoot(container);
		IPath output = null;
		if (outputName != null)
		{
			IFolder outputFolder = project.getFolder(outputName);
			if (!outputFolder.exists()) 
			{
				outputFolder.createLink(new Path(outputPath), 16, monitor);
			}
			output = outputFolder.getFullPath();
		}
		IClasspathEntry cpe = JavaCore.newSourceEntry(root.getPath(), new IPath[0], output);
		addToClasspath(jproject, cpe, monitor);
	}

	/**
	 * 
	 * @param jproject
	 * @param cpe
	 * @param monitor
	 * @throws JavaModelException
	 */
	private void addToClasspath(IJavaProject jproject, IClasspathEntry cpe, IProgressMonitor monitor) throws JavaModelException
	{
		IClasspathEntry oldEntries[] = jproject.getRawClasspath();
		for (int i = 0; i < oldEntries.length; i++)
		{
			if (oldEntries[i].equals(cpe))
			{
				return;
			}
		}

		int nEntries = oldEntries.length;
		IClasspathEntry newEntries[] = new IClasspathEntry[nEntries + 1];
		System.arraycopy(oldEntries, 0, newEntries, 0, nEntries);
		newEntries[nEntries] = cpe;
		jproject.setRawClasspath(newEntries, monitor);
	}

	/**
	 * 
	 * @param jproject
	 * @param path
	 * @param sourceAttachPath
	 * @param sourceAttachRoot
	 * @param monitor
	 * @throws JavaModelException
	 */
	private void addVariableEntry(IJavaProject jproject, IPath path, IPath sourceAttachPath,
		IPath sourceAttachRoot, IProgressMonitor monitor) throws JavaModelException
	{
		IClasspathEntry cpe = JavaCore.newVariableEntry(path, sourceAttachPath, sourceAttachRoot);
		addToClasspath(jproject, cpe, monitor);
	}

	/**
	 * 
	 * @param jproject
	 * @param path
	 * @param monitor
	 * @throws JavaModelException
	 */
	private void addLibrary(IJavaProject jproject, IPath path, IProgressMonitor monitor)
		throws JavaModelException
	{
		IClasspathEntry cpe = JavaCore.newLibraryEntry(path, null, null);
		addToClasspath(jproject, cpe, monitor);
	}

	/**
	 * 
	 * @param jproject
	 * @param path
	 * @param monitor
	 * @throws JavaModelException
	 */
	private void addProjects(IJavaProject jproject, IPath path, IProgressMonitor monitor) throws JavaModelException
	{
		IClasspathEntry cpe = JavaCore.newProjectEntry(path);
		addToClasspath(jproject, cpe, monitor);
	}

	/**
	 * 
	 * @param jproject
	 * @param path
	 * @param monitor
	 * @throws CoreException
	 */
	private void addContainer(IJavaProject jproject, IPath path, IProgressMonitor monitor) throws CoreException
	{
		IClasspathEntry cpe = JavaCore.newContainerEntry(path);
		addToClasspath(jproject, cpe, monitor);
		IProject project = jproject.getProject();
		IFolder folder = project.getFolder(path.lastSegment());
		if (!folder.exists())
		{
			folder.createLink(path, 16, monitor);
		}
	}

	/**
	 * 
	 * @param jproject
	 * @param folderName
	 * @param path
	 * @param monitor
	 * @throws CoreException
	 */
	private void addFolderToProject(IJavaProject jproject, String folderName, IPath path, IProgressMonitor monitor) throws CoreException
	{
		IClasspathEntry cpe = JavaCore.newContainerEntry(path);
		addToClasspath(jproject, cpe, monitor);
		IProject project = jproject.getProject();
		IFolder folder = project.getFolder(folderName);
		if (!folder.exists())
		{
			folder.createLink(path, 16, monitor);
		}
	}
}
