/**
 * Copyright (C) 2013. The COMPANY_NAME Home Media Server Project
 * EclProjectCreatorPage.java Created on Sep 19, 2013 
 * 
 * The copyright of this code and related documentation together with any other associated intellectual property rights
 * are vested in COMPANY_NAME and may not be used except in accordance with the terms of the license that you have 
 * entered into with COMPANY_NAME. Use of this material without an express license from COMPANY_NAME shall be an
 * infringement of copyright and any other intellectual property rights that may be incorporated with this material.
 */
package org.creator.eclprojctc.creator.page;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.creator.eclprojctc.creator.EclProjectCreator;
import org.creator.eclprojctc.metadata.projectxml.ProjectXMLMetadata;
import org.creator.eclprojctc.metadata.projectxml.creator.ProjectXMLMetadataCreator;
import org.creator.eclprojctc.validator.file.FileValidator;
import org.creator.eclprojctc.validator.xml.ProjectBuildXMLValidator;
import org.creator.eclprojctc.wizards.NewWizardMessages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * EclProjectCreatorPage Author: Pranit Kumar Version: 1.0
 */
public class EclProjectCreatorPage extends WizardPage
{
	private ModifyListener fLocationModifyListener;
	private ModifyListener fNameModifyListener;
	private Text fProjectNameField;
	private Text fLocationPathField;
	private Button fBrowseButton;
	private ProjectXMLMetadata projectXMLMetadata;

	/**
	 *
	 * @param pageName
	 */
	public EclProjectCreatorPage(String pageName)
	{
		super(pageName);
		fLocationModifyListener = new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				ProjectXMLMetadataCreator projectXMLMetadataCreator = new ProjectXMLMetadataCreator();
				String projectXMLFilePath = getProjectLocationFieldValue();
				File buildFile = new File(projectXMLFilePath);
				FileValidator fileValidator = new FileValidator();
				if (fileValidator.isFileExisisting(buildFile))
				{
					projectXMLMetadata = projectXMLMetadataCreator.createProjectXMLMetadata(buildFile);
					fProjectNameField.setText(projectXMLMetadata.getProjectName());
					setPageComplete(validatePage());
				}
				else
				{
					setErrorMessage(NewWizardMessages.Invalid_Location_Error_Message);
					setPageComplete(false);
				}
			}
		};
		fNameModifyListener = new ModifyListener()
		{

			public void modifyText(ModifyEvent e)
			{
				setPageComplete(validatePage());
			}
		};
		setPageComplete(false);
		setTitle(NewWizardMessages.New_Project_Page_One_Title);
		setDescription(NewWizardMessages.New_Project_Page_One_Description);
	}

	/**
	 * 
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent)
	{
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 7;
		layout.marginWidth = 7;
		layout.verticalSpacing = convertVerticalDLUsToPixels(4);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(4);
		layout.numColumns = 3;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(1808));
		composite.setFont(parent.getFont());
		createProjectNameGroup(composite);
		createProjectLocationGroup(composite);
		GridData gd = new GridData();
		gd.horizontalAlignment = 4;
		gd.grabExcessHorizontalSpace = false;
		gd.horizontalSpan = 2;
		validatePage();
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	/**
	 * 
	 * @return
	 */
	private boolean validatePage()
	{
		ProjectBuildXMLValidator projectBuildXMLValidator = new ProjectBuildXMLValidator();
		String projNameInNameField = fProjectNameField.getText();
		String xmlLocationInLocationField = fLocationPathField.getText();
		boolean isProjNameValid = projectBuildXMLValidator.isNonNullAndNonEmptyString(projNameInNameField);
		boolean isXMlLocationValid = projectBuildXMLValidator.isNonNullAndNonEmptyString(xmlLocationInLocationField);
		if (!isProjNameValid)
		{
			setErrorMessage(NewWizardMessages.Invalid_Project_name_Error_Message);
			return false;
		}
		if (!isXMlLocationValid)
		{
			setErrorMessage(NewWizardMessages.Empty_loocation_Error_Message);
			return false;
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projNameInNameField);
		if (project.exists())
		{
			setErrorMessage(NewWizardMessages.Project_Already_Exist_Error_Message);
			return false;
		}
		else
		{
			setErrorMessage(null);
			setMessage(null);
			return true;
		}
	}

	/**
	 * 
	 * @param parent
	 */
	private final void createProjectLocationGroup(Composite parent)
	{
		Label projectContentsLabel = new Label(parent, 0);
		projectContentsLabel.setText(NewWizardMessages.Project_File_Message);
		projectContentsLabel.setFont(parent.getFont());
		createUserSpecifiedProjectLocationGroup(parent);
	}

	/**
	 * 
	 * @param parent
	 */
	private final void createProjectNameGroup(Composite parent)
	{
		org.eclipse.swt.graphics.Font dialogFont = parent.getFont();
		Label projectLabel = new Label(parent, 0);
		projectLabel.setText("Project Name");
		projectLabel.setFont(dialogFont);
		GridData gd = new GridData(256);
		projectLabel.setLayoutData(gd);
		fProjectNameField = new Text(parent, 2048);
		gd = new GridData();
		gd.horizontalAlignment = 4;
		gd.grabExcessHorizontalSpace = false;
		gd.horizontalSpan = 2;
		fProjectNameField.setLayoutData(gd);
		fProjectNameField.setFont(dialogFont);
		fProjectNameField.addModifyListener(fNameModifyListener);
	}

	/**
	 * 
	 * @param projectGroup
	 */
	private void createUserSpecifiedProjectLocationGroup(Composite projectGroup)
	{
		org.eclipse.swt.graphics.Font dialogFont = projectGroup.getFont();
		fLocationPathField = new Text(projectGroup, 2048);
		GridData data = new GridData(768);
		data.widthHint = 250;
		fLocationPathField.setLayoutData(data);
		fLocationPathField.setFont(dialogFont);
		fBrowseButton = new Button(projectGroup, 8);
		fBrowseButton.setText("Browse");
		fBrowseButton.setFont(dialogFont);
		setButtonLayoutData(fBrowseButton);
		fBrowseButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				handleBrowseButtonPressed();
			}
		});
		fLocationPathField.addModifyListener(fLocationModifyListener);
	}

	/**
	 * 
	 * @return
	 */
	private String getProjectNameFieldValue()
	{
		if (fProjectNameField == null)
		{
			return "";
		}
		else
		{
			return fProjectNameField.getText().trim();
		}
	}

	/**
	 * 
	 * @return
	 */
	private String getProjectLocationFieldValue()
	{
		return fLocationPathField.getText().trim();
	}

	/**
	 * 
	 */
	private void handleBrowseButtonPressed()
	{
		String lastUsedPath = "";
		FileDialog dialog = new FileDialog(getShell(), 4);
		dialog.setFilterExtensions(new String[] { "*.xml" });
		dialog.setFilterPath(lastUsedPath);
		String result = dialog.open();
		if (result == null)
		{
			return;
		}
		else
		{
			IPath filterPath = new Path(dialog.getFilterPath());
			String buildFileName = dialog.getFileName();
			IPath path = filterPath.append(buildFileName).makeAbsolute();
			fLocationPathField.setText(path.toOSString());
			return;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean createProject()
	{
		final IJavaProject result[] = new IJavaProject[1];
		final String projectName = getProjectNameFieldValue();
		WorkspaceModifyOperation op = new WorkspaceModifyOperation()
		{

			protected void execute(IProgressMonitor monitor) throws CoreException
			{
				EclProjectCreator creator = new EclProjectCreator();
				IJavaProject javaProject = creator.createJavaProject(projectName, monitor, projectXMLMetadata);
				result[0] = javaProject;
			}
		};
		try
		{
			getContainer().run(true, true, op);
		}
		catch (InterruptedException _ex)
		{
			_ex.printStackTrace();
			return false;
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}