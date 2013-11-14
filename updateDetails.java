package Lifestyle.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import BLogic.*;

public class updateDetails extends JInternalFrame implements ActionListener,ItemListener
{
	JLabel
		l_deptno = new JLabel("Department no"),
		l_dept = new JLabel("Department name"),
		l_mgr = new JLabel("Manager Name"),
		l_username = new JLabel("Username"),
		l_pass = new JLabel("Password"),
		l_new_pass = new JLabel("New Password"),
		l_confirm = new JLabel("Confirm Password");

	JTextField
		t_mgr = new JTextField(10),
		t_username= new JTextField(10),
		t_deptno = new JTextField(10);
		
		
	JPasswordField
		t_pass = new JPasswordField(10),
		t_confirm = new JPasswordField(10);		

	JButton
		b_reset = new JButton("Reset",new ImageIcon("Res/refresh24.gif")),
		b_cancel = new JButton("Cancel",new ImageIcon("Res/cloapp.gif")),
		b_enter = new JButton("Enter",new ImageIcon("Res/GArrow.gif")),
		b_mod = new JButton("Update Details"),
		b_update = new JButton("Update Details");


	JComboBox
		cb_dept = new JComboBox();

	JPanel
		fields = new JPanel(),
		buttons = new JPanel(),
		upper = new JPanel();

	JCheckBox
		chk_pass = new JCheckBox("Change Password");
	
	dept 
		d = new dept(desktop.con);

	String deptno,deptname,username,mgr,pass;

	int count=0;

	ArrayList a;

	boolean c = false;
		
	public updateDetails()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dim.width-592)/2,(dim.height-420)/2,592,420);

		setLayout(new FlowLayout(FlowLayout.CENTER, 25,25));
		setTitle("Manager Accounts");
		setIconifiable(true);
		setClosable(true);
	
		addDept();
		addControls();
		setDeptdata();
		setVisible(true);
	}
	public void addControls()
	{
		//----------Setting Layout----------
		fields.setLayout(new GridLayout(7,2,12,12));		
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

		fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder())));
		
		fields.add(l_deptno);
		fields.add(t_deptno);
		fields.add(l_dept);
		fields.add(cb_dept);
		fields.add(l_mgr);
		fields.add(t_mgr);
		fields.add(l_username);
		fields.add(t_username);		
		fields.add(l_pass);
		fields.add(t_pass);
		fields.add(l_confirm);
		fields.add(t_confirm);

		buttons.add(chk_pass);
		buttons.add(b_enter);
		buttons.add(b_mod);
		buttons.add(b_update);
		buttons.add(b_reset);
		buttons.add(b_cancel);

		//---------Setting Font-----------

		t_deptno.setFont(new Font("Verdana",Font.BOLD,12));
		t_username.setFont(new Font("Verdana",Font.BOLD,12));
		
		//---------Setting Disabled----------------
		t_deptno.setEditable(false);
		cb_dept.requestFocusInWindow();
		
		l_confirm.setEnabled(false);
		t_confirm.setEnabled(false);
		chk_pass.setEnabled(false);
		b_update.setVisible(false);
		b_mod.setVisible(false);

		//------Adding Listeners-----------
		b_reset.addActionListener(this);
		b_cancel.addActionListener(this);
		b_enter.addActionListener(this);
		b_update.addActionListener(this);
		b_mod.addActionListener(this);

		chk_pass.addItemListener(this);
		cb_dept.addActionListener(this);		

		this.getContentPane().add(fields);
		this.getContentPane().add(buttons);
	}

	public void addDept()
	{
		cb_dept.addItem("BABY SHOP");
		cb_dept.addItem("SHOE MART");
		cb_dept.addItem("SPLASH");
		cb_dept.addItem("HOME CENTER");
		cb_dept.addItem("LIFESTYLE");
	}

	public void setDeptdata()
	{
		try
		{
			d.getDeptdata(cb_dept.getSelectedItem().toString());
			
			t_deptno.setText(d.getDeptNo()); 
			
			t_mgr.setText(d.getMgrName());
			
		}
		
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"setDeptdataError",0);
		}	
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_cancel)
		{
			this.dispose();
		}
		if(e.getSource()==b_reset)
		{
			t_username.setText("");
			t_mgr.setText("");
			t_pass.setText("");	
			t_confirm.setText("");
		}
		
		if(e.getSource()==b_enter)
		{
			if(t_username.getText().equals("") || t_mgr.getText().equals("") )
			{
				JOptionPane.showMessageDialog(this,"Enter all the fields carefully.","Manager Accounts",3);
			}
			else
			{
				try
				{
					String u = t_username.getText();
					char [] pass = t_pass.getPassword();
					String pa = new String(pass);
					deptno=t_deptno.getText();
					deptname= cb_dept.getSelectedItem().toString();
					mgr= t_mgr.getText();
						
					username= t_username.getText();
					
					if(d.validate(u,mgr,pa))
					{
						JOptionPane.showMessageDialog(this,"Welcome "+ mgr +" !!","Manager Accounts",1);
						
						t_pass.setText("");
						l_pass.setEnabled(false);
						t_pass.setEnabled(false);
						b_mod.setVisible(true);
						b_enter.setVisible(false);
						chk_pass.setEnabled(true);
						cb_dept.setVisible(false);
						l_dept.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(this,"You cannot access "+ mgr +"'s Account","Manager Accounts",3);
					}						
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Verify details Error",0);		
				}					
				
			}
			
		}

		if(e.getSource()==b_mod)
		{
			if(t_username.getText().equals("") || t_mgr.getText().equals("") ||t_pass.getText().equals("") && chk_pass.isSelected())
			{
				JOptionPane.showMessageDialog(this,"Enter all the fields carefully.","Manager Accounts",3);
			}
			else
			{
				try
				{
					deptno=t_deptno.getText();
					deptname= cb_dept.getSelectedItem().toString();
					mgr= t_mgr.getText();
					username= t_username.getText();
	
					d.saveDetails(deptno,deptname,mgr,username);
					JOptionPane.showMessageDialog(this,"Details have been Saved Successfully !","Manager Accounts",1);		
					
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Saving details Error",0);		
				}
			}
		}

		if(e.getSource()==cb_dept)
		{
			try
			{
				d.getDeptdata(cb_dept.getSelectedItem().toString());
				t_pass.setText("");
				t_username.setText("");
				t_deptno.setText(d.getDeptNo()); 
				t_confirm.setText("");
				t_mgr.setText(d.getMgrName());
				
			}
		
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getMessage(),"setDeptdataError",0);
			}	
		}

		if(e.getSource()==b_update)
		{
			if(t_pass.getText().equals("") || t_confirm.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Enter passwords in both the fields.","Manager Accounts",2);
			}
			else if(t_pass.getText().equals(t_confirm.getText()))
			{
				try
				{
					deptno=t_deptno.getText();
					deptname= cb_dept.getSelectedItem().toString();
					mgr= t_mgr.getText();
					char [] pass = t_pass.getPassword();
					String pa = new String(pass);
					username= t_username.getText();
					d.savePassDetails(deptno,deptname,mgr,username,pa);
					JOptionPane.showMessageDialog(this,"Password and other details have been Changed Successfully","Manager Accounts",1);	
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Password Error",0);
				}				
			}
			else
			{
				JOptionPane.showMessageDialog(this,"The Password you Entered do not match.\nPlease reEnter the new passwords in both fields.","Manager Accounts",1);
			}
		}
	}
	public void itemStateChanged(ItemEvent i)
	{
		if(chk_pass.isSelected())
		{
			l_confirm.setEnabled(true);
			t_confirm.setEnabled(true);

			l_pass.setEnabled(true);
			t_pass.setEnabled(true);

			b_mod.setVisible(false);
			b_update.setVisible(true);
		}
		else 
		{
			l_confirm.setEnabled(false);
			t_confirm.setText("");
			t_confirm.setEnabled(false);
			
			l_pass.setEnabled(false);
			t_pass.setText("");
			t_pass.setEnabled(false);

			b_mod.setVisible(true);
			b_update.setVisible(false);
		}
	}	
}
