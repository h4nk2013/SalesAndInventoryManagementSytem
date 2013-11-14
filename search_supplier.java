package Lifestyle.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.table.*;
import BLogic.*;

class search_supplier extends JPanel implements ActionListener
{
	JLabel
		l_supp = new JLabel("Enter Supplier Name");

	JTextField
		t_supp = new JTextField(10);

	JButton
		b_supp = new JButton("Search",new ImageIcon("Res/Search_R.gif"));

	Product
		p = new Product(desktop.con);

	JTable
		table_sname = new JTable();

	JScrollPane
		sp_sname = new JScrollPane(table_sname);

	TableColumn
		column = null;

	JPanel
		fields = new JPanel(),
		table = new JPanel(),
		buttons = new JPanel();

	public search_supplier()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));		
		
		fields.setLayout(new GridLayout(1,2,10,10));
		fields.add(l_supp);
		fields.add(t_supp);

		buttons.setLayout(new FlowLayout());
		buttons.add(b_supp);

		table.add(sp_sname);

		table_sname.setModel(new table_demo());
		table_sname.setRowHeight(25);
		table_sname.setFont(new Font("Verdana",Font.PLAIN,12));
		table_sname.setDragEnabled(false);
		table_sname.setRowMargin(3);

		add(fields);
		add(buttons);
		add(table);

		sp_sname.setPreferredSize(new Dimension(975,400));

		t_supp.setFont(new Font("verdana",Font.BOLD,12));
		t_supp.setForeground(Color.BLUE);

		b_supp.addActionListener(this);
		t_supp.addKeyListener(new validate());

		setVisible(true);
	}		

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_supp)
		{			
			if(t_supp.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Enter the Supplier Name","Product Name",2);
				t_supp.requestFocusInWindow();
			}
			else
			{
				table_sname.setModel(new table_demo());
				for (int i = 0; i < 7; i++) 
				{
					column = table_sname.getColumnModel().getColumn(i);
					if (i==0)
					{
						column.setPreferredWidth(10);
					} 
					else if (i==1||i==2||i==3||i==4||i==5||i==6)
					{
						column.setPreferredWidth(50);
					}
				}
			}	
		}
	}

	class validate extends KeyAdapter
	{
		String total,phone;
		String pname;
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_supp.getText())
				{
					total = t_supp.getText();
				}
				table_sname.setModel(new table_demo());
				for (int i = 0; i < 7; i++) 
				{
					column = table_sname.getColumnModel().getColumn(i);
					if (i==0)
					{
						column.setPreferredWidth(10);
					} 
					else if (i==1||i==2||i==3||i==4||i==5||i==6)
					{
						column.setPreferredWidth(50);
					}
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getMessage(),"Error Ocurred!!!..",2);				
			}
		}
	}

	class table_demo extends AbstractTableModel 
	{
		String col [] = {"Supp ID","Supp Name","Supp Brand","Supp Add","City","Telephone","Email"};
		Vector data;
		public table_demo()
		{
			data = new Vector();
			try
			{
					p.searchSupplierName(data,col.length,t_supp.getText());				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Search Table Error",0);
			}
		}
		public Object getValueAt(int r,int c)
		{
			String arr[] = (String[]) data.elementAt(r);
			return arr[c];
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
