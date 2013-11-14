package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import BLogic.*;

class salesReport extends JInternalFrame implements ActionListener
{
	JPanel
		rep_table = new JPanel(),
		buttons = new JPanel();
	
	JButton
		b_print = new JButton("Print",new ImageIcon("Res/Print-Active.gif")),
		b_cancel = new JButton("Cancel",new ImageIcon("Res/cloapp.gif"));

	sales 
		s = new sales(desktop.con);

	JTable 
		table = new JTable();

	JScrollPane 
		sp = new JScrollPane(table);

	TableColumn
			column= null;

	public salesReport()
	{
		setSize(1015,650);
		setTitle("Sales Details");
		setLayout(new FlowLayout());		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setVisible(true);

		addControls();
	}

	public void addControls()
	{
		//--------Setting Layout-------
		sp.setPreferredSize(new Dimension(990,500));
		rep_table.setLayout(new BorderLayout());
		rep_table.add(sp);

		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		buttons.add(b_print);
		buttons.add(b_cancel);

		//=========Adding ActionListeners===========
		b_print.addActionListener(this);		
		b_cancel.addActionListener(this);

		//--------Setting table properties----------
		table.setModel(new table_demo());
		table.setRowHeight(25);
		table.setRowMargin(5);
		table.setDragEnabled(false);
		table.setGridColor(Color.BLACK);
		
		//-------Setting Column Width---------
		for (int i = 0; i < 10; i++) 
		{
			column = table.getColumnModel().getColumn(i);
			if (i == 0 || i==1)
			{
				column.setPreferredWidth(3); //third column is bigger
			} 
			else if(i==2 || i==3||i==4)
			{
				column.setPreferredWidth(100);
			}
			else if (i==6)
			{
				column.setPreferredWidth(5);
			}
			else if(i==7||i==8)
			{
				column.setPreferredWidth(25);
			}
			else if(i==5||i==9)
			{
				column.setPreferredWidth(80);
			}
		}

		//---------Setting Font----------
		table.setFont(new Font("Verdana",Font.PLAIN,12));		

		this.getContentPane().add(rep_table);
		this.getContentPane().add(buttons);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_cancel)
		{
			this.dispose();
		}
		if(e.getSource()==b_print)
		{
			try
			{
				table.print(JTable.PrintMode.FIT_WIDTH);	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Print Error",0);
			}			
		}
	}

	class table_demo extends AbstractTableModel
	{		
		String [] col  = {"Bill Id","Item ID","Name","Supplier","Brand","Date","Qty","Price","Total Price","Section Name"};
		Vector data;
			public table_demo()	
			{
				data = new Vector();
				try
				{
					s.giveSalesDetails(data,col.length);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Table Error",0);
				}
			}
			public Object getValueAt(int c,int r)
			{
				String arr[] = (String[]) data.elementAt(c);
				return arr[r];
			}
			public int getRowCount()
			{
				return data.size();
			}
			public int getColumnCount()
			{	
				return col.length;
			}	
			public String getColumnName(int c)
			{
				return col[c].toString();
			}
		}	
}
