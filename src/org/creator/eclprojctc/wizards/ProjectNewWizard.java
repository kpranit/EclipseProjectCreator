/**
 * Copyright (C) 2011. The Eclipse Project Creator plug-in Project.
 * ProjectNewWizard.java Created on Mar 19, 2011 
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

import org.creator.eclprojctc.creator.page.EclProjectCreatorPage;
import org.creator.eclprojctc.ui.image.UIImages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * 
 * ProjectNewWizard Author: Pranit Kumar
 * Version: 1.0
 */
public class ProjectNewWizard extends Wizard
	implements INewWizard, IExecutableExtension
{
    private static final String WIZARD_NAME = "New Java Project";
    private static final String PAGE_NAME = "Custom Java Project Wizard";
    private EclProjectCreatorPage projectCreationWizardPage;
    private IConfigurationElement _configurationElement;

    /**
     * 
     */
    public ProjectNewWizard()
    {
        setWindowTitle("New custom java project");
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish()
    {
    	return projectCreationWizardPage.createProject();
	}

    /**
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
	public void addPages()
	{
		super.addPages();
	    projectCreationWizardPage = new EclProjectCreatorPage("Custom Java Project Wizard");
	    projectCreationWizardPage.setTitle(NewWizardMessages.CustomProjectNewWizard_Custom_Plugin_Project);
	    projectCreationWizardPage.setDescription(NewWizardMessages.CustomProjectNewWizard_Create_something_custom);
	    addPage(projectCreationWizardPage);
	}

	/**
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init
	 * 	(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
	    setDefaultPageImageDescriptor(UIImages.getImageDescriptor("icons"));
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData
	 * 	(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException
	{
	    _configurationElement = config;
	}
}
