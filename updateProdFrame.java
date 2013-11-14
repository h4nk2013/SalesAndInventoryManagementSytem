package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class updateProdFrame extends JInternalFrame implements ActionListener
{
	JLabel
		l_pid = new JLabel("Product ID"),
		l_pname = new JLabel("Product Name"),
		l_supp = new JLabel("Product Supplier"),
		l_bname = new JLabel("Brand Name"),
		l_qty = new JLabel("Quantity"),
		l_price = new JLabel("Price"),
		l_tot_price = new JLabel("Total Price"),
		l_sub_dept = new JLabel("SubDepartment no."),
		l_sub_dname = new JLabel("SubDepartment Name"),

		ld_pid = new JLabel(""),
		ld_deptno = new JLabel(""),
		ld_dname = new JLabel("");

	JTextField
		t_qty = new JTextField(15),
		t_price = new JTextField(15),
		t_tot_price =new JTextField(15);

	JComboBox
		cb_pname = new JComboBox(),
		cb_supp = new JComboBox(),
		cb_brand = new JComboBox();

	JButton
		b_mod = new JButton("Update Product",new ImageIcon("Res/update.gif")),
		b_cancel=new JButton("Cancel",new ImageIcon("Res/cloapp.gif"));

	JPanel
		fields = new JPanel(),
		upper = new JPanel(),
		buttons = new JPanel();
	
	int id,price,tot,qty;
	String name,sname,brand,deptno,dname;

	ArrayList a;

	Product p = new Product(desktop.con);

	public updateProdFrame()
	{
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setLayout(new FlowLayout());
		setTitle("Update Products");
		addControls();

		setProdName();
		setSupplier();
		setBrand();

		setVisible(true);
		this.pack();
	}
	public void addControls()
	{
		//----------Setting Font------------
		l_pid.setFont(new Font("Verdana",Font.BOLD,12));
		t_qty.setFont(new Font("Verdana",Font.BOLD,12));
		t_price.setFont(new Font("Verdana",Font.BOLD,12));
		t_tot_price.setFont(new Font("Verdana",Font.BOLD,12));

		//----------Setting Layout----------
		fields.setLayout(new GridLayout(9,2,15,15));
		
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

		upper.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
		upper.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Select A Product"));

		//----------Adding Components--------

		fields.add(l_pid);
		fields.add(ld_pid);
		fields.add(l_pname);
		fields.add(cb_pname);
		fields.add(l_supp);
		fields.add(cb_supp);
		fields.add(l_bname);
		fields.add(cb_brand);
		fields.add(l_qty);
		fields.add(t_qty);
		fields.add(l_price);
		fields.add(t_price);
		fields.add(l_tot_price);
		fields.add(t_tot_price);
		fields.add(l_sub_dept);
		fields.add(ld_deptno);
		fields.add(l_sub_dname);
		fields.add(ld_dname);

		buttons.add(b_mod);
		buttons.add(b_cancel);
		
		upper.add(fields);

		//--------Adding ActionListeners--------

		cb_pname.addActionListener(this);
		cb_supp.addActionListener(this);
		cb_brand.addActionListener(this);
		b_mod.addActionListener(this);
		b_cancel.addActionListener(this);

		//---------Adding FocusListener---------
		t_qty.addKeyListener(new validate());
		t_price.addKeyListener(new validate());

		t_tot_price.setEditable(false);
		cb_pname.requestFocusInWindow();

		this.getContentPane().add(upper);
		this.getContentPane().add(buttons);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_cancel)
		{
			this.dispose();
		}
		if(e.getSource()==cb_pname)
		{
			cb_supp.removeActionListener(this);
			cb_supp.removeAllItems();
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);				
				ResultSet rs= stmt.executeQuery("select prod_id,prod_supp,prod_brand,prod_qty,prod_price,total_price,sub_deptno,sub_dname from prod where prod_name= '"+ cb_pname.getSelectedItem().toString()+ "'");					
				if(rs.next())
				{
					ld_pid.setText(Integer.toString(rs.getInt(1)));
					t_qty.setText(Integer.toString(rs.getInt(4)));
					t_price.setText(String.valueOf(rs.getInt(5)));
					t_tot_price.setText(String.valueOf(rs.getInt(6)));
					ld_deptno.setText(rs.getString(7));
					ld_dname.setText(rs.getString(8));
				}	
				rs.previous();
				while(rs.next())
				{
					cb_supp.addItem(rs.getString(2));
					cb_brand.addItem(rs.getString(3));
				}
				
				stmt.close();
				cb_brand.addActionListener(this);
				cb_supp.addActionListener(this);
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
				ResultSet rs= stmt.executeQuery("select prod_id,prod_brand,prod_qty,prod_price,total_price,sub_deptno,sub_dname from prod where prod_supp= ' " + cb_supp.getSelectedItem().toString() + "'");
				
				if(rs.next())
				{
					ld_pid.setText(Integer.toString(rs.getInt(1)));
					t_qty.setText(Integer.toString(rs.getInt(3)));
					t_price.setText(String.valueOf(rs.getInt(4)));
					t_tot_price.setText(String.valueOf(rs.getInt(5)));
					ld_deptno.setText(rs.getString(6));
					ld_dname.setText(rs.getString(7));
				}
				rs.previous();
				while(rs.next())
				{
					cb_brand.addItem(rs.getString(2));
				}
				stmt.close();
				cb_brand.addActionListener(this);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"cd_supp Error",0);
			}
		}

		if (e.getSource()==cb_brand)
		{
			cb_supp.removeActionListener(this);
			cb_supp.removeAllItems();
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs= stmt.executeQuery("select prod_id,prod_supp,prod_qty,prod_price,total_price,sub_deptno,sub_dname from prod where prod_name= '"+cb_pname.getSelectedItem().toString()+ "' and prod_brand= '" +cb_brand.getSelectedItem().toString()+ "'");
				if(rs.next())
				{
					ld_pid.setText(Integer.toString(rs.getInt(1)));					
					t_qty.setText(Integer.toString(rs.getInt(3)));
					t_price.setText(String.valueOf(rs.getInt(4)));
					t_tot_price.setText(String.valueOf(rs.getInt(5)));
					ld_deptno.setText(rs.getString(6));
					ld_dname.setText(rs.getString(7));
				}
				rs.previous();
				while(rs.next())
				{
					cb_supp.addItem(rs.getString(2));
				}
				stmt.close();
				cb_supp.addActionListener(this);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"cd_brand Error",0);
			}
		}
		if(e.getSource()==b_mod)
		{
			try
			{
				if(t_qty.getText().length()==0 | t_price.getText().length()==0)
				{
					JOptionPane.showMessageDialog(this,"Please Enter All the Details","Error",3);
				}
				else if(Integer.parseInt(t_qty.getText()) < 0 | Integer.parseInt(t_price.getText()) < 0)
				{
					JOptionPane.showMessageDialog(this,"Please Check the Details","Error",2);
				}
				else
				{
					id = Integer.parseInt(ld_pid.getText());
					name = cb_pname.getSelectedItem().toString();
					sname = cb_supp.getSelectedItem().toString();
					brand = cb_brand.getSelectedItem().toString();
					qty =Integer.parseInt(t_qty.getText());
					price = Integer.parseInt(t_price.getText());
					tot = Integer.parseInt(t_tot_price.getText());

					p.updateProduct(name,sname,brand,qty,price,tot,id);
					JOptionPane.showMessageDialog(this,"Product Has been Updated","Success",1);
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"b_mod Error",0);
			}
		}
	}

	public void setProdName()
	{
		try
		{
			a =	p.getPName();
			for(int i=0;i<a.size();i++)
			{
				cb_pname.addItem(a.get(i));
			}	
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"error",0);
		}
	}
	
	public void setSupplier()
	{
		try
		{
			a = p.getSupplier();
			for(int i=0;i<a.size();i++)
			{
				cb_supp.addItem(a.get(i));
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Set supplier error",0);
		}
	}

	public void setBrand()
	{
		try
		{
			a = p.getBrand();
			for(int i=0;i<a.size();i++)
			{
				cb_brand.addItem(a.get(i));
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Set brand error",0);
		}
	}
	
	class validate extends KeyAdapter
	{
		int total,q;
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_qty)
				{					
					total = Integer.parseInt(t_qty.getText()) * Integer.parseInt(t_price.getText());
					t_tot_price.setText(Integer.toString(total));
				}
				if(e.getSource()==t_price)
				{
					total = Integer.parseInt(t_qty.getText()) * Integer.parseInt(t_price.getText());
					t_tot_price.setText(Integer.toString(total));
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);
			}
		}
	}  
}
