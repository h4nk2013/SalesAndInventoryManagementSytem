package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import BLogic.*;

class addSupplier extends JPanel implements ActionListener,FocusListener
{
	JLabel 
		l_id = new JLabel("Supplier Id"),
		l_sname = new JLabel("Supplier Name"),
		l_brand = new JLabel("Supplier Brand"),
		l_add = new JLabel("Address"),
		l_city = new JLabel("City"),
		l_phone = new JLabel("Phone No."),
		l_email = new JLabel("E-mail");
	
	JTextField
		t_id = new JTextField(12),
		t_sname = new JTextField(12),
		t_brand = new JTextField(12),
		t_add = new JTextField(12),
		t_city = new JTextField(12),
		t_phone = new JTextField(15),
		t_email = new JTextField(15);
	
	JCheckBox
		c_phone = new JCheckBox("TelePhone No."),
		c_email = new JCheckBox("Email Address");

	JButton
		b_add = new JButton("Add Supplier",new ImageIcon("Res/UII_Icons/24x24/user_add.png")),
		b_reset = new JButton("Reset",new ImageIcon("Res/UII_Icons/24x24/refresh.png")),
		b_addBrand = new JButton("Add Brand",new ImageIcon("Res/UII_Icons/24x24/user_add.png")),
		b_removeBrand = new JButton("Remove Brand",new ImageIcon("Res/remove.gif"));
	

	JPanel
		fields = new JPanel(),
		supp_details = new JPanel(),
		s_city = new JPanel(),
		checkbox = new JPanel(),
		buttons = new JPanel(),
		brand_fields = new JPanel(),
		table = new JPanel();

	supplier 
		s_details = new supplier(desktop.con);;
	

	//---------------------------

	
	JEditorPane
		ep = new JEditorPane();

	JTable
		table_brand = new JTable();

	JScrollPane
		sp_ep = new JScrollPane(ep),
		sp_table = new JScrollPane(table_brand);

	table_demo
		mod = new table_demo();

	TableColumn
		column = null;

	Vector 
		data = new Vector();

	int id;

	public addSupplier()
	{
		setSize(1015,660);
		setLayout(new FlowLayout(FlowLayout.CENTER,25,20));
		setVisible(true);	
		
		addControls();
		getId();			
	}

	public void addControls()
	{
		//--------Setting Layout---------
		fields.setLayout(new GridLayout(2,2,12,12));

		fields.add(l_id);
		fields.add(t_id);
		fields.add(l_sname);
		fields.add(t_sname);
		
		supp_details.setLayout(new GridLayout(1,2,12,12));

		supp_details.add(l_add);
		supp_details.add(sp_ep);

		ep.setContentType("text/plain");
		ep.setPreferredSize(new Dimension(150,80));

		brand_fields.setLayout(new GridLayout(1,2,12,12));
		brand_fields.add(l_brand);
		brand_fields.add(t_brand);		

		table.setLayout(new BorderLayout());
		table.add(sp_table);
		
		sp_table.setPreferredSize(new Dimension(180,150));
		table_brand.setModel(mod);
		table_brand.setRowHeight(25);
		table_brand.setFont(new Font("Verdana",Font.BOLD,11));		
		table_brand.setRowMargin(3);

		s_city.setLayout(new GridLayout(1,2,12,12));
		s_city.add(l_city);
		s_city.add(t_city);

		checkbox.setLayout(new GridLayout(2,2,10,10));
		checkbox.add(c_phone);
		checkbox.add(t_phone);
		checkbox.add(c_email);
		checkbox.add(t_email);

		t_phone.setEnabled(false);
		t_email.setEnabled(false);
		t_id.setEnabled(false);
		t_id.setBackground(Color.BLACK);
		t_id.setForeground(Color.RED);
						
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

		fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		supp_details.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		s_city.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		checkbox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),"Select any Choice"));

		buttons.add(b_add);
		buttons.add(b_reset);	

		//-----------Setting Fonts-----------
		t_id.setFont(new Font("Verdana",Font.BOLD,12));
		t_sname.setFont(new Font("Verdana",Font.BOLD,12));
		t_brand.setFont(new Font("Verdana",Font.BOLD,12));
		t_add.setFont(new Font("Verdana",Font.BOLD,12));
		t_city.setFont(new Font("Verdana",Font.BOLD,12));
		t_phone.setFont(new Font("Verdana",Font.BOLD,12));
		t_email.setFont(new Font("Verdana",Font.BOLD,12));

		ep.setFont(new Font("Verdana",Font.BOLD,12));

		b_add.addActionListener(this);
		b_reset.addActionListener(this);
		b_addBrand.addActionListener(this);
		b_removeBrand.addActionListener(this);
		c_phone.addActionListener(this);
		c_email.addActionListener(this);

		t_phone.addKeyListener(new validate());
		t_email.addFocusListener(this);
		
		
		add(fields);
		add(brand_fields);
		add(b_addBrand);
		add(b_removeBrand);
		add(table);
		add(supp_details);
		add(s_city);
		add(checkbox);
		add(buttons);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_reset)
		{
			t_sname.setText("");
			t_add.setText("");
			t_city.setText("");
			t_phone.setText("");
			t_email.setText("");
			ep.setText("");
			getId();
			mod.removeAllData();
		}
		if(e.getSource()==b_add)
		{
			if(t_sname.getText().length()==0 || ep.getText().length()==0 || t_city.getText().length()==0 || c_phone.isSelected() && t_phone.getText().length()==0 ||c_email.isSelected() && t_email.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this,"Please Enter Supplier's Details","Add Supplier Error",2);
			}
			else
			{
				try
				{					
					for(int i=0;i<data.size();i++)
					{						
						s_details.setSuppId(Integer.parseInt(t_id.getText()));
						s_details.setSuppName(t_sname.getText());	
						s_details.setSuppBrand(mod.getValueAt(i,0).toString());
						s_details.setSuppAdd(ep.getText());
						s_details.setSuppCity(t_city.getText());
						s_details.setSuppPhone(t_phone.getText());
						s_details.setSuppEmail(t_email.getText());

						s_details.insertSupplier();						
					}					
					JOptionPane.showMessageDialog(this,"Supplier has been added","Add Supplier",1);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(this,ex.getMessage(),"Add Supplier Error",0);
				}
			}
		}

		if(e.getSource()==b_addBrand)
		{
			if(t_brand.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this,"Please Enter Supplier's Brand","Supplier Brand Error",2);
			}
			else
			{
				try
				{
					mod.addData();
					t_brand.setText("");
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Supplier Brand Error",0);
				}
			}
		}

		if (e.getSource()==b_removeBrand)
		{
			try
			{
				mod.removeData();		
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Supplier Brand Error",0);
			}			
		}

		if(e.getSource()==c_phone)
        {
			if(c_phone.isSelected())
			{
				t_phone.setEnabled(true); 
			}
			else
			{
				t_phone.setEnabled(false); 
			}             
        }
		
		if(e.getSource()==c_email)
        {
			if(c_email.isSelected())
			{
				t_email.setEnabled(true); 
			}
			else
			{
				t_email.setEnabled(false); 
			}             
        }		
	}

	public void getId()
	{
		try
		{
			int i= s_details.autoGenerate();
			t_id.setText(Integer.toString(i));
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Id error",0);
		}
	}

	public void focusGained(FocusEvent e)
	{
	}

	public void focusLost(FocusEvent e)
	{
		int count=0,c=0;
		try
		{
			if(e.getSource()==t_email)
			{
				/*for(int i=0;i<t_email.getText().length();i++)
				{
					String ch= (String)(t_email.getText().substring(i));
					if(ch.equals('@'))
					{
						count = count +1;
					}
					if(ch.equals('.'))
					{
						c= c + 1;
					}
				}
				if(count ==0)
				{
					JOptionPane.showMessageDialog(null,"Atleast 1 @ should be used","Email error",0);
					t_email.requestFocusInWindow();
				}
				if(count> 1)
				{
					JOptionPane.showMessageDialog(null,"Only 1 @ should be used","Email error",0);
					t_email.requestFocusInWindow();
				}
				if(c==0)
				{
					JOptionPane.showMessageDialog(null,"Atleast 1 . should be used","Email error",0);
					t_email.requestFocusInWindow();
				}*/
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"Error Ocurred!!..",2);
		}
	}
	
	class validate extends KeyAdapter implements KeyListener
	{
		long phone;		
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_phone)
				{
					phone = new Long(Long.parseLong(t_phone.getText()));					
				}				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);												
				t_phone.setText("");
			}			
		}		
	}

	class table_demo extends AbstractTableModel
	{		
		String col [] = {"Supplier Brand"};
		
		public Object getValueAt(int r,int c)
		{
			String arr [] = (String[])data.elementAt(r);
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
		public void addData()
		{
			String arr[] = new String[col.length];
			arr[0] = t_brand.getText();
			data.addElement(arr);
			this.fireTableRowsInserted(0,data.size());
		}
		public void removeData()
		{
			if (table_brand.isRowSelected(table_brand.getSelectedRow()))
			{
				String arr[] = new String[col.length];
				arr[0]= mod.getValueAt(table_brand.getSelectedRow(),0).toString();
				data.removeElementAt(table_brand.getSelectedRow());
				this.fireTableRowsDeleted(0,data.size());
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please Select A Row","Row not Selected",3);
			}
		}
		public void removeAllData()
		{
			data.removeAllElements();
			this.fireTableRowsDeleted(0,data.size());
		}
	}
}
