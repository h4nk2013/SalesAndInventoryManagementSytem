package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class update_csr extends JPanel implements ActionListener
	{
		JLabel
			l_id = new JLabel("Sales Representative ID"),
			l_name = new JLabel("Name"),
			l_add = new JLabel("Address"),
			l_phone = new JLabel("Telephone"),
			l_email = new JLabel("Email Address"),
			l_dname	= new JLabel("Select Department");

		JTextField
			t_name = new JTextField(15),
			t_add = new JTextField(20),
			t_phone = new JTextField(15),
			t_email = new JTextField(15);

		JComboBox
			cb_dname = new JComboBox(),
			cb_cust_id = new JComboBox();

		JPanel
			fields = new JPanel(),			
			buttons = new JPanel();
		
		JButton
			b_update = new JButton("Update Record",new ImageIcon("Res/update.gif")),
			b_refresh = new JButton("Refresh",new ImageIcon("Res/Refresh24.gif"));
			

		ArrayList a;

		cust_sales_rep 
			csr = new cust_sales_rep(desktop.con);

		ResultSet rs;

		int id;
		String name,add,phone,email,dname;

		public update_csr()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
						
			fields.setLayout(new GridLayout(6,2,10,10));
			fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),"Update Details"));
			fields.add(l_id);
			fields.add(cb_cust_id);
			fields.add(l_name);
			fields.add(t_name);
			fields.add(l_add);
			fields.add(t_add);
			fields.add(l_phone);
			fields.add(t_phone);
			fields.add(l_email);
			fields.add(t_email);
			fields.add(l_dname);
			fields.add(cb_dname);			

			buttons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

			buttons.add(b_update);	
			buttons.add(b_refresh);	

		addDept();		
		addId();
		addDetails();
		
		add(fields);
		add(buttons);

		b_update.addActionListener(this);	
		b_refresh.addActionListener(this);	
		cb_cust_id.addActionListener(this);
		t_phone.addKeyListener(new validate());

		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==cb_cust_id)
		{
			try
			{
				PreparedStatement ps = csr.con.prepareStatement("select name,address,phone,email,dname from cust_sales_rep where csr_id=?");
				ps.setInt(1,Integer.parseInt(cb_cust_id.getSelectedItem().toString()));
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					t_name.setText(rs.getString(1).trim());
					t_add.setText(rs.getString(2).trim());
					t_phone.setText(rs.getString(3).trim());
					t_email.setText(rs.getString(4).trim());
					cb_dname.setSelectedItem(rs.getString(5).trim());
				}
				ps.close();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Cb ID error",0);
			}
		}
		if(e.getSource()==b_update)
		{
			if(t_name.getText().equals("") || t_add.getText().length()==0 || t_phone.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this,"Please Enter All Details","Data Missing",3);
			}
			else
			{
				try
				{
					id = Integer.parseInt(cb_cust_id.getSelectedItem().toString());
					name = t_name.getText();
					add = t_add.getText();
					phone = t_phone.getText();
					email = t_email.getText();
					dname = cb_dname.getSelectedItem().toString();

					csr.updateCsr(name,add,phone,email,dname,id);
					JOptionPane.showMessageDialog(this,"Record has been updated Successfully!!!...","Updating Record",1);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
				}
			}

		}
		if(e.getSource()==b_refresh)
		{
			cb_cust_id.removeActionListener(this);
			cb_cust_id.removeAllItems();				
			addId();
			addDetails();
			cb_cust_id.addActionListener(this);						
		}
	}

	public void addDept()
	{
		try
		{
			Statement stmt = csr.con.createStatement();
			ResultSet rs = stmt.executeQuery("select dname from dept order by deptno");
			while(rs.next())
			{
				cb_dname.addItem(rs.getString(1));				
			}				
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Dname error",0);
		}
	}

	public void addId()
	{
		try
		{
			Statement stmt = csr.con.createStatement();
			ResultSet rs = stmt.executeQuery("select csr_id from cust_sales_rep");
			while(rs.next())
			{
				cb_cust_id.addItem(rs.getString(1));				
			}				
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Dname error",0);
		}
	}

	public void addDetails()
	{
		try
		{
			Statement stmt = csr.con.createStatement();
			ResultSet rs = stmt.executeQuery("select csr_id,name,address,phone,email,dname from cust_sales_rep");
			if(rs.next())
			{
				//cb_cust_id.addItem(rs.getString(1));
				t_name.setText(rs.getString(2).trim());
				t_add.setText(rs.getString(3).trim());
				t_phone.setText(rs.getString(4).trim());
				t_email.setText(rs.getString(5).trim());
			}				
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Id error",0);
		}
	}



	class validate extends KeyAdapter implements KeyListener
	{
		int total;
		long phone;
		
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_phone)
				{
					phone = Long.parseLong(t_phone.getText());
				}								
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);												
				t_phone.requestFocusInWindow();
			}
		}
	}
}
	