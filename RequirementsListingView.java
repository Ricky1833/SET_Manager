package org.sdsu.cs532.set;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.sdsu.cs532.jdbc.JDBCMySQLNavMenu;
import org.sdsu.cs532.jdbc.JDBCMySQLRequirementListing;

public class RequirementsListingView extends ViewPart {
	
	public static final String ID = "org.sdsu.cs532.SET.reqeditorview";

	private Composite formBody;
	private Composite mainComposite;
	private Composite tableComposite;
	private Composite buttonComposite;
	private ScrolledForm viewForm;
	private Table requirementsTable;
	private TableViewer requirementsTableViewer;
	private List<Requirement> requirementsList;
	
	
	@Override
	public void createPartControl(Composite parent) {
		getRequirementsFromDb();
		createTopLevelComponents(parent);
		setTopLevelLayout();
		createComponents(parent);
		setLayout();
	}
	
	public void getRequirementsFromDb() {
		JDBCMySQLRequirementListing listing = new JDBCMySQLRequirementListing();
		listing.assembleRequirements();
		this.requirementsList = listing.getListOfRequirements();
//		System.out.println(this.requirementsList.toString());
	}
	
	public void createTopLevelComponents(Composite parent) {
		this.viewForm = new ScrolledForm(parent);
		this.viewForm.setText("Requirements Listing");
		
		this.formBody = this.viewForm.getBody();
		
		this.mainComposite = new Composite(this.formBody, SWT.NONE);
		
		this.tableComposite = new Composite(this.mainComposite, SWT.BORDER);
		
		this.buttonComposite = new Composite(this.mainComposite, SWT.BORDER);
	}
	
	public void setTopLevelLayout() {
		GridLayout layout = new GridLayout();
		this.formBody.setLayout(layout);
		
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.formBody.setLayoutData(gd);
		
		layout = new GridLayout();
		this.mainComposite.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.mainComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		this.tableComposite.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.tableComposite.setLayoutData(gd);
		
		layout = new GridLayout();
		this.buttonComposite.setLayout(layout);
		
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.buttonComposite.setLayoutData(gd);
	}
	
	public void createComponents(Composite parent) {
		this.requirementsTable = new Table(this.tableComposite, SWT.READ_ONLY | SWT.SINGLE);
		this.requirementsTable.setHeaderVisible(true);
		
		this.requirementsTableViewer = new TableViewer(this.requirementsTable);
		
		createColumns(this.tableComposite, this.requirementsTableViewer);
		
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			data.add("Row " + i);
		}
		
		this.requirementsTableViewer.setContentProvider(new ArrayContentProvider());
//		this.requirementsTableViewer.setLabelProvider(new LabelProvider() {
//			@Override
//			public String getText(Object element) {
//				if (element instanceof String) {
//					return (String) element;
//				}
//				return "Table";//element.toString();
//			}
//		});
		this.requirementsTableViewer.setInput(this.requirementsList.toArray());
		
		this.tableComposite.pack();
		this.viewForm.layout(true, true);
	}
	
	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = {"Id", "Project ID", "Requirement ID", "Description", "Source",
				"Functional Area", "Date Entered", "Date Baselined", "Status", "Allocation"};
		int[] bounds = {50, 80, 95, 500, 60, 95, 95, 95, 60, 75};
		
		//First Column, id
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return "String";//(String) element;
				} else if (element instanceof Integer) {
					return "Int";//((Integer) element).toString();
				} else if (element instanceof Requirement) {
					return Integer.toString(((Requirement) element).getIdNum());
				}
				return "Not Working";//element.toString();
			}
		});
		
		//Second column, project id
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return Integer.toString(((Requirement) element).getProjectParentId());
				}
				return element.toString();
			}
		});
		
		//3rd column, req id
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return ((Requirement) element).getID();
				}
				return element.toString();
			}
		});
		
		//4th column, desc
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return ((Requirement) element).getDesc();
				}
				return element.toString();
			}
		});
		
		//5th column, source
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return Integer.toString(((Requirement) element).getSourceId());
				}
				return element.toString();
			}
		});
		
		//6th, functional area
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return Integer.toString(((Requirement) element).getFaSubsystemId());
				}
				return element.toString();
			}
		});
		
		//7th, date entered
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return ((Requirement) element).getCreated();// return ((Requirement) element).getDateEntered().toString();
				}
				return element.toString();
			}
		});
		
		//8th, date baselined
		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return  ((Requirement) element).getBaselined();//.getDateBLined().toString();
				}
				return element.toString();
			}
		});
		
		//9th, status
		col = createTableViewerColumn(titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return Integer.toString(((Requirement) element).getStatusId());
				}
				return element.toString();
			}
		});
		
		//10th, allocation
		col = createTableViewerColumn(titles[9], bounds[9], 9);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					return (String) element;
				} else if (element instanceof Requirement) {
					return Integer.toString(((Requirement) element).getAllocationId());
				}
				return element.toString();
			}
		});
	}
	
	public void setLayout() {
		GridLayout layout = new GridLayout();
		this.requirementsTable.setLayout(layout);
		
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.requirementsTable.setLayoutData(gd);
		
	}
	
	private TableViewerColumn createTableViewerColumn(String title, int bound,
		      final int colNumber) {
		    final TableViewerColumn viewerColumn = new TableViewerColumn(this.requirementsTableViewer,
		        SWT.NONE);
		    final TableColumn column = viewerColumn.getColumn();
		    column.setText(title);
		    column.setWidth(bound);
		    column.setResizable(true);
		    column.setMoveable(true);
		    column.setToolTipText(column.getText());
		    column.addSelectionListener(getSelectionAdapter(column, colNumber));
		    return viewerColumn;
		  }

		  private SelectionAdapter getSelectionAdapter(final TableColumn column,
		      final int index) {
		    SelectionAdapter selectionAdapter = new SelectionAdapter() {
		      @Override
		      public void widgetSelected(SelectionEvent e) {
//		        comparator.setColumn(index);
//		        int dir = comparator.getDirection();
//		        requirementsTableViewer.getTable().setSortDirection(dir);
//		        requirementsTableViewer.refresh();
		      }
		    };
		    return selectionAdapter;
		  }

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
