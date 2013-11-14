package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

public class add_csr extends JPanel implements ActionListener
{
	JLabel
		l_cust_id = new JLabel("Cust Representative Id"),
		l_cust_name = new JLabel("CSR Name"),
		l_cust_add = new JLabel("CSR Address"),
		l_cust_phone = new JLabel("TelePhone"),
		l_cust_email = new JLabel("Email Address"),
		l_cust_dname = new JLabel("Select Department");

	JTextField
		t_cust_id = new JTextField(15),
		t_cust_name = new JTextField(15),
		t_cust_add = new JTextField(15),
		t_cust_phone = new JTextField(15),
		t_cust_email = new JTextField(15);

	JPanel
		fields = new JPanel(),			
		buttons = new JPanel();
		
	JComboBox
		cb_dname = new JComboBox();

	JButton
		b_add = new JButton("Add Record",new ImageIcon("Res/Add24.gif")),
		b_reset = new JButton("Reset",new ImageIcon("Res/ctxsample_hide.gif"));
		
	ArrayList a;

	cust_sales_rep 
		csr = new cust_sales_rep(desktop.con);

	ResultSet rs;

	int id;
	String name,add,phone,email,dname;

	public add_csr()
	{
		t_cust_id.setBackground(Color.BLACK);
		t_cust_id.setForeground(Color.WHITE);
		t_cust_id.setEnabled(false);
		t_cust_id.setFont(new Font("Verdana",Font.BOLD,12));
		
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
					
		fields.setLayout(new GridLayout(6,2,10,10));
		fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),"Add New Details"));
		fields.add(l_cust_id);
		fields.add(t_cust_id);
		fields.add(l_cust_name);
		fields.add(t_cust_name);
		fields.add(l_cust_add);
		fields.add(t_cust_add);
		fields.add(l_cust_phone);
		fields.add(t_cust_phone);
		fields.add(l_cust_email);
		fields.add(t_cust_email);
		fields.add(l_cust_dname);
		fields.add(cb_dname);			

		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

		buttons.add(b_add);
		buttons.add(b_reset);	

		addDept();
		getId();
		add(fields);
		add(buttons);

		b_add.addActionListener(this);
		b_reset.addActionListener(this);

		t_cust_phone.addKeyListener(new validate());
		
		setVisible(true);
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

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_reset)
		{
			t_cust_name.setText("");
			t_cust_add.setText("");
			t_cust_phone.setText("");
			t_cust_email.setText("");				
			getId();
		}
		if(e.getSource()==b_add)
		{
			try
			{
				if(t_cust_name.getText().equals("") || t_cust_add.getText().length()==0 || t_cust_phone.getText().length()==0)
				{
					JOptionPane.showMessageDialog(this,"Please Enter All Details","Data Missing",3);
				}			
				else
				{
					id = Integer.parseInt(t_cust_id.getText());
					name = t_cust_name.getText();
					add = t_cust_add.getText();
					phone = t_cust_phone.getText();
					email = t_cust_email.getText();
					dname = cb_dname.getSelectedItem().toString();
					try
					{
						csr.newCsr(id,name,add,phone,email,dname);
						JOptionPane.showMessageDialog(this,"New CSR Has been Added Successfully!!!...","Add CSR",1);
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(this,"Please Check the Details","Adding CSR Error",0);
					}
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,"Please Enter Valid Details","Add Record error",0);
			}
		}
	}	
		
	public void getId()
	{
		int i;
		try
		{
			Statement stmt = csr.con.createStatement();
			rs = stmt.executeQuery("select max(csr_id) from cust_sales_rep");
			while(rs.next())
			{
				i=rs.getInt(1)+1;
				t_cust_id.setText(Integer.toString(i));
			}
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Dname error",0);
		}
	}
	class validate extends KeyAdapter implements KeyListener
	{
		long phone;
		String name;
		
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_cust_phone)
				{
					phone = Long.parseLong(t_cust_phone.getText());								
				}				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);
				t_cust_phone.setText("");
			}
		}
	}
}

