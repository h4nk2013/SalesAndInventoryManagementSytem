package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class addProdFrame extends JInternalFrame implements ActionListener
{
	JLabel
		l_id = new JLabel("Product Id : "),
		l_name = new JLabel("Product Name : "),
		l_supp = new JLabel("Product Supplier : "),
		l_brand = new JLabel("Product Brand : "),
		l_qty = new JLabel("Product Qty :"),
		l_price = new JLabel("Product Price : "),
		l_date = new JLabel("Date of Import : "),
		l_dept = new JLabel("Department Name :"),
		l_section = new JLabel("Section Name :"),
		l_deptno = new JLabel("Department No :"),
		ld_deptno = new JLabel("");

	JTextField
		t_id = new JTextField(15),
		t_name = new JTextField(15),
		t_qty = new JTextField(15),
		t_price = new JTextField(15),
		t_date = new JTextField(15);

	JComboBox
		cb_deptno = new JComboBox(),
		cb_dept = new JComboBox(),
		cb_supp = new JComboBox(),
		cb_brand = new JComboBox(),
		cb_section = new JComboBox();

	 JButton
		b_add = new JButton("Add New Product",new ImageIcon("Res/Add24.gif")),
		b_reset = new JButton("Reset",new ImageIcon("Res/UII_Icons/24x24/refresh.png")),
		b_cancel = new JButton("Cancel",new ImageIcon("Res/cloapp.gif"));

	JPanel
		fields = new JPanel(),
		upper = new JPanel(),	
		buttons = new JPanel();

	JSpinner
		s = new JSpinner();		

	SpinnerDateModel
		sdl = new SpinnerDateModel();

	int id,qty,price,tot;
	String name,supp,brand,deptno,dname;
	java.sql.Date date;

	Product 
		p = new Product(desktop.con);
		
	ArrayList a,b;
	
	public addProdFrame()
	{
		try
		{
			setSize(1015,660);
			setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));
			setTitle("Add New Product");
			setIconifiable(true);
			setClosable(true);
			setMaximizable(true);
			addControls();
			
			getId();
			addDept();
			addSection();
			setSupplier();
			setBrand();	

			setVisible(true);	
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
		}
		
	}
	public void addControls()
	{
		//----------Setting Layout---------
		fields.setLayout(new GridLayout(10,2,12,12));  

		fields.add(l_id);
		fields.add(t_id);
		fields.add(l_name);
		fields.add(t_name);
		fields.add(l_supp);
		fields.add(cb_supp);		
		fields.add(l_brand);
		fields.add(cb_brand);
		fields.add(l_qty);
		fields.add(t_qty);
		fields.add(l_price);
		fields.add(t_price);
		fields.add(l_date);
		fields.add(s);
		fields.add(l_dept);
		fields.add(cb_dept);			
		fields.add(l_section);
		fields.add(cb_section);
		fields.add(l_deptno);
		fields.add(ld_deptno);
		
		buttons.add(b_add);
		buttons.add(b_reset);
		buttons.add(b_cancel);
		
		//---------Spinner----------
		s.setModel(sdl);

		//----------upper Panel------
		upper.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Product Details"));
		upper.setFont(new Font("Verdana",Font.BOLD,18));
		upper.setLayout(new FlowLayout(FlowLayout.CENTER,25,25));
		upper.add(fields);				

		//----------Buttons Panel--------
		buttons.setLayout(new FlowLayout(FlowLayout.LEADING,25,25));

		//---------Setting Font----------
		t_id.setFont(new Font("Verdana",Font.BOLD,12));
		t_name.setFont(new Font("Verdana",Font.BOLD,12));
		t_qty.setFont(new Font("Verdana",Font.BOLD,12));
		t_price.setFont(new Font("Verdana",Font.BOLD,12));
		t_date.setFont(new Font("Verdana",Font.BOLD,12));

		t_id.setBackground(Color.BLACK);
		t_id.setForeground(Color.WHITE);
		t_id.setEnabled(false);

		//------Adding Listeners--------
		cb_dept.addActionListener(this);
		cb_deptno.addActionListener(this);
		cb_section.addActionListener(this);
		cb_supp.addActionListener(this);
		cb_brand.addActionListener(this);

		t_name.addKeyListener(new validate());
		t_qty.addKeyListener(new validate());
		t_price.addKeyListener(new validate());

		b_add.addActionListener(this);
		b_reset.addActionListener(this);
		b_cancel.addActionListener(this);			
		
		//---------Adding Panels--------
		this.getContentPane().add(upper);
		this.getContentPane().add(buttons);		
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_cancel)
		{
			this.dispose();
		}
		if(e.getSource()==b_reset)
		{
			t_name.setText("");
			t_qty.setText("");
			t_price.setText("");
			getId();
			new java.sql.Date(sdl.getDate().getTime());
		}
		if(e.getSource()==b_add)
		{
			try
			{
				if(t_name.getText().equals("") || t_qty.getText().length()==0 || t_price.getText().length()==0)
				{
					JOptionPane.showMessageDialog(this,"Please Enter All Details","Data Missing",3);
					t_name.requestFocusInWindow();
				}
				else if (Integer.parseInt(t_qty.getText()) < 0 || Integer.parseInt(t_price.getText()) < 0)
				{
					JOptionPane.showMessageDialog(this,"Quantity & Price Should be GREATER Than ZERO","Error",2);
				}	
				else
				{
					id = Integer.parseInt(t_id.getText());
					name = t_name.getText();
					supp = cb_supp.getSelectedItem().toString();
					brand = cb_brand.getSelectedItem().toString();
					qty = Integer.parseInt(t_qty.getText());
					price = Integer.parseInt(t_price.getText());
					tot = qty * price;
					date = new java.sql.Date(sdl.getDate().getTime());
					deptno = ld_deptno.getText();
					dname = cb_section.getSelectedItem().toString();
					try
					{
						p.newProduct(id,name,supp,brand,qty,price,tot,date,deptno,dname);
						JOptionPane.showMessageDialog(this,"Product Has been Added Successfully!!!...","Add Product",1);
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(this,"Please Check the Details\n"+ ex.getStackTrace() ,"Adding Product Error",0);
					}
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,"Enter Valid Details","Adding Product Error",0);
			}			
		}
		if(e.getSource()==cb_dept)
		{
			cb_section.removeActionListener(this);
			cb_section.removeAllItems();
			ld_deptno.setText("");
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery("select sub_dname,sub_deptno from sub_sections s,dept d where s.deptno = d.deptno and d.dname = '"+cb_dept.getSelectedItem().toString()+"'");
				if(rs.next())
				{
					ld_deptno.setText(rs.getString(2));
				}
				rs.previous();
				while(rs.next())
				{
					cb_section.addItem(rs.getString(1));					
				}					
				
				stmt.close();
				cb_section.addActionListener(this);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"cd_dept Error",0);
			}
		}		

		if(e.getSource()==cb_section)
		{
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);				
				ResultSet rs = stmt.executeQuery("select sub_deptno from sub_sections where sub_dname = '"+ cb_section.getSelectedItem().toString()+ "'");
				if(rs.next())
				{
					ld_deptno.setText(rs.getString(1));
				}
				stmt.close();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"cd_section Error",0);
			}
		}		
		if(e.getSource()==cb_supp)
		{
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);				
				ResultSet rs = stmt.executeQuery("select bname from supplier where sname = '"+ cb_supp.getSelectedItem().toString()+ "'");
				while(rs.next())
				{
					cb_brand.addItem(rs.getString(1));
				}
				stmt.close();
				cb_brand.addActionListener(this);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"cb_spplier Error",0);
			}
		}
	}	

	public void setSupplier()
	{
		try
		{
			b = p.addSupplier();
			for(int i=0;i<b.size();i++)
			{
				cb_supp.addItem(b.get(i));
			}	
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"Supplier Error",0);
		}
	}


	public void setBrand()
	{
		try
		{
			a = p.addBrand();
			for(int i=0;i<a.size();i++)
			{
				cb_brand.addItem(a.get(i));
			}	
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"error",0);
		}		
	}	

	public void getId()
	{
		try
		{
			int i= p.autoGenerate();
			t_id.setText(Integer.toString(i));
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Id error",0);
		}
	}

	public void addDept()
	{
		cb_dept.removeActionListener(this);
		cb_dept.removeAllItems();
		try
		{
			Statement stmt = p.con.createStatement();
			ResultSet rs = stmt.executeQuery("select dname from dept order by deptno");
			while(rs.next())
			{
				cb_dept.addItem(rs.getString(1));				
			}
			cb_dept.addActionListener(this);
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"Id error",0);
		}	
	}

	public void addSection()
	{
		cb_section.removeActionListener(this);
		cb_section.removeAllItems();
		try
		{
			Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("select sub_dname,sub_deptno from sub_sections where deptno in (select deptno from dept where dname = '" + cb_dept.getSelectedItem().toString() +"')");
			if(rs.next())
			{
				ld_deptno.setText(rs.getString(2));
			}
			rs.previous();
			while(rs.next())
			{
				cb_section.addItem(rs.getString(1));
			}
			cb_section.addActionListener(this);
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"Id error",0);
		}
	}

	class validate extends KeyAdapter
	{
		int total,phone;
		String pname;
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_qty)
				{
					total = Integer.parseInt(t_qty.getText());
				}								
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);
				t_qty.setText("");
			}
			try
			{
				if(e.getSource()==t_price)
				{
					phone = Integer.parseInt(t_price.getText());
				}								
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);
				t_price.setText("");
			}				
		}		
	}
}
