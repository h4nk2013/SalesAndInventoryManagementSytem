package Lifestyle.gui;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import BLogic.*;

public class prodReport extends JInternalFrame implements ActionListener
{
	JPanel
		rep_table = new JPanel(),
		buttons = new JPanel(),
		fields = new JPanel();
	
	JButton
		b_print = new JButton("Print",new ImageIcon("Res/Print-Active.gif")),
		b_cancel = new JButton("Cancel",new ImageIcon("Res/cloapp.gif"));

	supplier 
		s = new supplier(desktop.con);
		
	JEditorPane ep = new JEditorPane();
	Vector data = new Vector();

	/*JTable 
		table = new JTable(new table_demo());*/

	JScrollPane 
		sp = new JScrollPane(ep);

	/*TableColumn
		column =null;*/

	public prodReport()
	{
		setSize(1015,650);
		setTitle("Product Details");
		setLayout(new FlowLayout());		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		
		addControls();
		setVisible(true);
	}
	public void addControls()
	{
		//--------Setting Layout-------
		ep.setContentType("text/html");
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
		/*table.setRowHeight(25);
		table.setRowMargin(5);
		table.setDragEnabled(false);
		table.setGridColor(Color.BLACK);*/

		//-------Setting Column Width---------
		/*for (int i = 0; i < 10; i++) 
		{
			column = table.getColumnModel().getColumn(i);
			if (i == 0)
			{
				column.setPreferredWidth(5); //third column is bigger
			} 
			else if(i==1 || i==2 || i==3)
			{
				column.setPreferredWidth(120);
			}
			else if(i==4 || i==5 )
			{
				column.setPreferredWidth(10);
			}
			else if(i==6 || i==7 ||i==8)
			{
				column.setPreferredWidth(30);
			}
			else
			{
				column.setPreferredWidth(80);
			}
		}*/

		//--------Setting Font-------
		

		this.getContentPane().add(rep_table);
		this.getContentPane().add(buttons);
		generateReport();
	}
	
	public void generateReport() {
		try {
			s.giveProductData(data,10);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(this, ex, "Error", 0);
		}
		String rdata = "";
		rdata = rdata + "<html><center><table bordercolor=red border=0 style=font-family:verdana;font-size:11;><caption><h2>Product Details</h2></caption><tr bgcolor=red style=color:white>"
					+ "<th>Product Id</th><th>Name</td><th>Supplier</th><th>Brand</th><th>Quantity</th><th>Price(Indv.) </th><th>Total Price (Sotck unit)</th><th>Date</th><th>Department No.</th><th>Department name</th></tr>";
		for(int i = 0; i < data.size(); i++) {
			if(i % 2 != 0) rdata = rdata + "<tr bgcolor=AAAAAA>";
			else rdata = rdata + "<tr>";
			String arr [] = (String [])data.get(i);
			for(int j = 0; j < 10; j++) {
				rdata = rdata + "<td>" + arr[j] + "</td>";
			}
			rdata = rdata + "</tr>";
		}
		rdata = rdata + "</table><hr bgcolor=red></center></html>";
		ep.setEditable(false);
		ep.setText(rdata);
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
				ep.print();	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Print Error",0);
			}			
		}
	}

	/*class table_demo extends AbstractTableModel
	{
		String col [] = {"Product Id","Name","Supplier","Brand","Quantity","Price(Indv.) ","Total Price (Sotck unit)","Date","Department No.","Department name"};
		Vector data;
		public table_demo()
		{
			data = new Vector();
			try
			{
				s.giveProductData(data,col.length);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Table Error",0);
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
	}*/
}
