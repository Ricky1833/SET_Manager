package org.sdsu.cs532.set;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.sql.*;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.sdsu.cs532.jdbc.JDBCMySQLDemo;
import org.sdsu.cs532.jdbc.JDBCMySQLNavMenu;
import org.sdsu.cs532.jdbc.JDBCMySQLSetCombos;

public class RequirementEditorView extends ViewPart {

	public static final String ID = "org.sdsu.cs532.SET.requirementeditorview";

	/**
	 * The text control that's displaying the content of the email message.
	 */
	private Composite mainComposite;
	private ScrolledForm viewForm;
	private Composite formBody;
	/*
	 * Holds name, id, source, dates
	 */
	private Composite metaDataComposite;
	/*
	 * Holds functional area, allocation, status, and components
	 */
	private Composite functionalComposite;
	/*
	 * Holds module, tests
	 */
	private Composite testsComposite;
	private Composite dateComposite;
	private Section metaDataSection;
	private Section functionalSection;
	private Section testsSection;
	private Label nameLabel;
	private Text nameText;
	private Label reqIDLabel;
	private Text reqIDText;
	private Label reqSource;
	private Text reqSourceText;
	private Label descLabel;
	private Text descText;
	private Label functionalAreaLabel;
	private Combo functionalAreaCombo;
	private Label dateEnteredLabel;
	private Text dateEnteredText;
	private Button dateEnteredButton;
	private Label dateBaselinedLabel;
	private Text dateBasedlinedText;
	private Button dateBaselinedButton;
	private Label allocationLabel;
	private Combo allocationCombo;
	private Label statusLabel;
	private Combo statusCombo;
	private Label componentsLabel;
	private Text componentsText;
	private Label moduleLabel;
	private Text moduleText;
	private Label testsLabel;
	private Text testsText;
	private Composite buttonComposite;
	private Button saveButton;
	private Button clearButton;
	private Button deleteButton;
	private Image calendarImage;
	private Requirement req;
	private Button testButton;
	private List<String> funcList;
	private List<String> allList;
	private List<String> statList;
	
	public void createPartControl(Composite parent) {
		createTopLevelComponents(parent);
		setTopLevelLayouts();
		setComboLists();
		createComponents(parent);
		setListeners(parent);
		setLayouts();
	}
	
	private void createTopLevelComponents(Composite parent) {
		this.viewForm = new ScrolledForm(parent);
		if(this.nameText != null) {
			this.viewForm.setText("Requirement Editor " + this.nameText.getText());
		} else {
			this.viewForm.setText("Requirement Editor ");
		}
		
		this.formBody = this.viewForm.getBody();
		
		this.mainComposite = new Composite(this.formBody, SWT.NONE);
		
		this.dateComposite = new Composite(this.mainComposite, SWT.BORDER);
		
		this.metaDataSection = new Section(this.mainComposite, Section.EXPANDED 
				| Section.CLIENT_INDENT | Section.TREE_NODE);
		this.metaDataSection.setText("Meta Data");
		this.metaDataComposite = new Composite(this.metaDataSection, SWT.BORDER);
		this.metaDataSection.setClient(this.metaDataComposite);
		
		this.functionalSection = new Section(this.mainComposite, Section.EXPANDED
				| Section.CLIENT_INDENT | Section.TREE_NODE);
		this.functionalSection.setText("Functional Data");
		this.functionalComposite = new Composite(this.functionalSection, SWT.BORDER);
		this.functionalSection.setClient(this.functionalComposite);
		
		this.testsSection = new Section(this.mainComposite, Section.EXPANDED
				| Section.CLIENT_INDENT | Section.TREE_NODE);
		this.testsSection.setText("Test Data");
		this.testsComposite = new Composite(this.testsSection, SWT.BORDER);
		this.testsSection.setClient(this.testsComposite);
		
		this.viewForm.layout(true, true);
	}
	
	private void setTopLevelLayouts() {
		GridLayout layout = new GridLayout();
		this.formBody.setLayout(layout);
		
		GridData gd = new GridData(SWT.LEFT, SWT.TOP, true, true);
		this.formBody.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.verticalSpacing = 15;
		this.mainComposite.setLayout(layout);
		
		gd = new GridData(SWT.LEFT, SWT.TOP, true, true);
		this.mainComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		this.metaDataSection.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		this.metaDataSection.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.numColumns = 4;
		layout.horizontalSpacing = 20;
		this.metaDataComposite.setLayout(layout);
		
		gd = new GridData(SWT.LEFT, SWT.TOP, true, false);
		gd.horizontalSpan = 2;
		this.metaDataComposite.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		this.dateComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.numColumns = 7;
		this.dateComposite.setLayout(layout);
		
		layout = new GridLayout();
		this.functionalSection.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.functionalSection.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.numColumns = 6;
		layout.horizontalSpacing = 20;
		this.functionalComposite.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.functionalComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		layout.numColumns = 4;
		layout.horizontalSpacing = 20;
		this.testsComposite.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.testsComposite.setLayoutData(gd);
		
		this.viewForm.layout(true, true);
	}
	
	private void createComponents(final Composite parent) {
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("/icons/calendar_icon.gif");
		this.calendarImage = new Image(this.viewForm.getDisplay(), input);
		
		this.dateEnteredLabel = new Label(this.dateComposite, SWT.NONE);
		this.dateEnteredLabel.setText("Date Created");
		
		this.dateEnteredText = new Text(this.dateComposite, SWT.BORDER);
		
		this.dateEnteredButton = new Button(this.dateComposite, SWT.FLAT);
		this.dateEnteredButton.setImage(calendarImage);
		
		Label space = new Label(this.dateComposite, SWT.NONE);
		space.setText("  ");
		GridData d = new GridData(SWT.FILL, SWT.FILL, true, true);
		space.setLayoutData(d);
		
		this.dateBaselinedLabel = new Label(this.dateComposite, SWT.NONE);
		this.dateBaselinedLabel.setText("Date Baselined");
		
		this.dateBasedlinedText = new Text(this.dateComposite, SWT.BORDER);
		
		this.dateBaselinedButton = new Button(this.dateComposite, SWT.FLAT);
		this.dateBaselinedButton.setImage(calendarImage);
		
		//Meta data area
		this.nameLabel = new Label(this.metaDataComposite, SWT.NONE);
		this.nameLabel.setText("Name");
		
		this.nameText = new Text(this.metaDataComposite, SWT.BORDER);
		
		this.reqIDLabel = new Label(this.metaDataComposite, SWT.NONE);
		this.reqIDLabel.setText("Requirement ID");
		
		this.reqIDText = new Text(this.metaDataComposite, SWT.BORDER);
		
		this.reqSource = new Label(this.metaDataComposite, SWT.NONE);
		this.reqSource.setText("Source");
		
		this.reqSourceText = new Text(this.metaDataComposite, SWT.BORDER);
		
		this.descLabel = new Label(this.metaDataComposite, SWT.NONE);
		this.descLabel.setText("Description");
		
		this.descText = new Text(this.metaDataComposite, SWT.BORDER);
		
		//Functional area
		this.functionalAreaLabel = new Label(this.functionalComposite, SWT.NONE);
		this.functionalAreaLabel.setText("Functional Area");
		//functional areas, need to populate with what areas are deemed needed, untill rbac is deployed.
		this.functionalAreaCombo = new Combo(this.functionalComposite, SWT.READ_ONLY);
		String[] areas = new String[this.funcList.size()];
		this.funcList.toArray(areas);
		this.functionalAreaCombo.setItems(areas);
		
		this.allocationLabel = new Label(this.functionalComposite, SWT.NONE);
		this.allocationLabel.setText("Allocation");
		
		this.allocationCombo = new Combo(this.functionalComposite, SWT.READ_ONLY);
		String[] allo = new String[this.allList.size()];
		this.allList.toArray(allo);
		this.allocationCombo.setItems(allo);
		
		this.statusLabel = new Label(this.functionalComposite, SWT.NONE);
		this.statusLabel.setText("Status");
		
		this.statusCombo = new Combo(this.functionalComposite, SWT.READ_ONLY);
		String[] stats = new String[this.statList.size()];
		this.statList.toArray(stats);
		this.statusCombo.setItems(stats);
		
		this.componentsLabel = new Label(this.functionalComposite, SWT.NONE);
		this.componentsLabel.setText("Components");
		
		this.componentsText = new Text(this.functionalComposite, SWT.BORDER);
		
		this.moduleLabel = new Label(this.testsComposite, SWT.NONE);
		this.moduleLabel.setText("Module");
		
		this.moduleText = new Text(this.testsComposite, SWT.BORDER);
		
		this.testsLabel = new Label(this.testsComposite, SWT.NONE);
		this.testsLabel.setText("Tests");
		
		this.testsText = new Text(this.testsComposite, SWT.BORDER);
		
		this.buttonComposite = new Composite(this.mainComposite, SWT.BORDER);
		
		this.saveButton = new Button(this.buttonComposite, SWT.FLAT);
		this.saveButton.setText("Save");
		
		this.clearButton = new Button(this.buttonComposite, SWT.FLAT);
		this.clearButton.setText("Clear");
		
		this.deleteButton = new Button(this.buttonComposite, SWT.FLAT);
		this.deleteButton.setText("Delete");
		
		this.testButton = new Button(this.buttonComposite, SWT.FLAT);
		this.testButton.setText("Test");
		
		this.metaDataSection.setExpanded(false);
		this.metaDataSection.setExpanded(true);
		this.functionalSection.setExpanded(false);
		this.functionalSection.setExpanded(true);
		this.testsSection.setExpanded(false);
		this.testsSection.setExpanded(true);
		this.dateComposite.layout(true, true);
		this.viewForm.layout(true, true);
	}
	
	private void setComboLists() {
		JDBCMySQLSetCombos setCombos = new JDBCMySQLSetCombos();
		this.allList = setCombos.getAllocationList();
		this.funcList = setCombos.getFunctionalList();
		this.statList = setCombos.getStatusList();
	}
	
	private void setLayouts() {
		GridData gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gd.horizontalSpan = 1;
		this.nameLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 3;
		this.nameText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.reqIDLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.reqIDText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.reqSource.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.reqSourceText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.descLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 90;
		gd.widthHint = 140;
		gd.horizontalSpan = 3;
		this.descText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.dateEnteredLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.dateEnteredText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.dateEnteredButton.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.dateBaselinedLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.dateBasedlinedText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.dateBaselinedButton.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.functionalAreaLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.functionalAreaCombo.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.allocationLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.allocationCombo.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.TOP, false, false);
		this.statusLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.TOP, true, false);
		this.statusCombo.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.componentsLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.heightHint = 80;
		gd.widthHint = 120;
		gd.horizontalSpan = 4;
		this.componentsText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.moduleLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.widthHint = 140;
		gd.heightHint = 50;
		this.moduleText.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.testsLabel.setLayoutData(gd);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.widthHint = 140;
		gd.heightHint = 50;
		this.testsText.setLayoutData(gd);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		this.buttonComposite.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.buttonComposite.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.saveButton.setLayoutData(gd);
		
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		this.clearButton.setLayoutData(gd);
		
		this.viewForm.layout(true, true);
	}
	
	public void setListeners(final Composite parent) {
		this.dateEnteredButton.addSelectionListener (new SelectionAdapter () {
		    public void widgetSelected (SelectionEvent e) {
		        final Shell dialog = new Shell (parent.getShell(), SWT.DIALOG_TRIM);
		        dialog.setLayout (new GridLayout (3, false));

		        final DateTime calendar = new DateTime (dialog, SWT.CALENDAR | SWT.BORDER);
		        final DateTime date = new DateTime (dialog, SWT.DATE | SWT.SHORT);
		        final DateTime time = new DateTime (dialog, SWT.TIME | SWT.SHORT);

		        new Label (dialog, SWT.NONE);
		        new Label (dialog, SWT.NONE);
		        Button ok = new Button (dialog, SWT.PUSH);
		        ok.setText ("OK");
		        ok.setLayoutData(new GridData (SWT.FILL, SWT.CENTER, false, false));
		        ok.addSelectionListener (new SelectionAdapter () {
		          public void widgetSelected (SelectionEvent e) {
		        	  RequirementEditorView.this.dateEnteredText.setText(Integer.toString(calendar.getMonth() + 1) + "/" + Integer.toString(calendar.getDay())
		        			  + "/" + Integer.toString(calendar.getYear()));
		            dialog.close ();
		          }
		        });
		        dialog.setDefaultButton (ok);
		        dialog.pack ();
		        dialog.open ();
		      }
		    });
		
		this.dateBaselinedButton.addSelectionListener (new SelectionAdapter () {
		    public void widgetSelected (SelectionEvent e) {
		        final Shell dialog = new Shell (parent.getShell(), SWT.DIALOG_TRIM);
		        dialog.setLayout (new GridLayout (3, false));

		        final DateTime calendar = new DateTime (dialog, SWT.CALENDAR | SWT.BORDER);
		        final DateTime date = new DateTime (dialog, SWT.DATE | SWT.SHORT);
		        final DateTime time = new DateTime (dialog, SWT.TIME | SWT.SHORT);

		        new Label (dialog, SWT.NONE);
		        new Label (dialog, SWT.NONE);
		        Button ok = new Button (dialog, SWT.PUSH);
		        ok.setText ("OK");
		        ok.setLayoutData(new GridData (SWT.FILL, SWT.CENTER, false, false));
		        ok.addSelectionListener (new SelectionAdapter () {
		          public void widgetSelected (SelectionEvent e) {
		        	  RequirementEditorView.this.dateBasedlinedText.setText(Integer.toString(calendar.getMonth() + 1) + "/" + Integer.toString(calendar.getDay())
		        			  + "/" + Integer.toString(calendar.getYear()));
		            dialog.close ();
		            deleteButton.setEnabled(false);
		          }
		        });
		        dialog.setDefaultButton (ok);
		        dialog.pack ();
		        dialog.open ();
		      }
		    });
		
		this.saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
				req = new Requirement(RequirementEditorView.this.nameText.getText(),
						RequirementEditorView.this.reqIDText.getText(),
						Integer.toString(RequirementEditorView.this.functionalAreaCombo.getSelectionIndex()),//.getItem(RequirementEditorView.this.functionalAreaCombo.getSelectionIndex()),
						RequirementEditorView.this.dateEnteredText.getText(),
						RequirementEditorView.this.dateBasedlinedText.getText(),
						RequirementEditorView.this.allocationCombo.getItem(RequirementEditorView.this.allocationCombo.getSelectionIndex()),
						RequirementEditorView.this.statusCombo.getItem(RequirementEditorView.this.statusCombo.getSelectionIndex()),
						RequirementEditorView.this.componentsText.getText(),
						RequirementEditorView.this.reqSourceText.getText(),
						RequirementEditorView.this.moduleText.getText(),
						RequirementEditorView.this.testsText.getText());
				} catch (IllegalArgumentException exception) {
					MessageDialog.openError(Display.getDefault().getActiveShell(),"Invalid Selection",exception.toString()+
							"\nPlease verify Functional Data selections are not blank.");
				}
			}
		});
		
		this.clearButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				RequirementEditorView.this.nameText.setText("");
				RequirementEditorView.this.reqIDText.setText("");
				RequirementEditorView.this.descText.setText("");
				RequirementEditorView.this.functionalAreaCombo.select(0);
				RequirementEditorView.this.dateEnteredText.setText("");
				RequirementEditorView.this.dateBasedlinedText.setText("");
				RequirementEditorView.this.reqSourceText.setText("");
				RequirementEditorView.this.allocationCombo.select(0);
				RequirementEditorView.this.statusCombo.select(0);
				RequirementEditorView.this.componentsText.setText("");
				RequirementEditorView.this.moduleText.setText("");
				RequirementEditorView.this.testsText.setText("");
			}
		});
		
		this.testButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				connect();
			}
		});
	}

	public void setFocus() {
		this.nameText.setFocus();
	}
	
	public void connect() {
		JDBCMySQLNavMenu nav = new JDBCMySQLNavMenu();
//		 JDBCMySQLDemo demo = new JDBCMySQLDemo();
	}
}
