package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import BLogic.*;

class LoginFrame extends JInternalFrame implements ActionListener
{
	JLabel
		l_user = new JLabel("<html><b>Enter UserName :-"),
		l_pass = new JLabel("<html><b>Enter Password :-");

	JTextField
		t_user = new JTextField(10);

	JPasswordField
		pf = new JPasswordField(10);

	JButton
		b_log = new JButton("Login",new ImageIcon("Res/YourAccount_R.gif")),
		b_reset = new JButton("Reset",new ImageIcon("Res/Refresh24.gif"));

	JPanel
		fields = new JPanel(),
		upper = new JPanel(),
		buttons = new JPanel();

	dept d = new dept(desktop.con);
		
	int count =0;
		
	public LoginFrame()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dim.width-400)/2,(dim.height-200)/2,400,200);

		setTitle("Enter your Indentity");
		setLayout(new FlowLayout());
		setClosable(true);
		
		addControls();
		setVisible(true);
	}	

	public void addControls()
	{
		this.requestFocus(true);
		t_user.requestFocusInWindow();
		//---------Setting Fonts-----------
		t_user.setFont(new Font("Verdana",Font.BOLD,12));
		pf.setFont(new Font("Verdana",Font.BOLD,12));

		//------Adding Componenets------
		fields.add(l_user);
		fields.add(t_user);
		fields.add(l_pass);
		fields.add(pf);
		
		upper.add(fields);
		upper.setBorder(BorderFactory.createEmptyBorder());

		buttons.add(b_log);
		buttons.add(b_reset);
		
		//------Setting Layout---------
		fields.setLayout(new GridLayout(3,2,10,10));
		upper.setLayout(new FlowLayout(FlowLayout.CENTER,8,8));
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,8,8));

		//-------Adding Listeners---------
		b_log.addActionListener(this);
		b_reset.addActionListener(this);
		
		this.getContentPane().add(upper);
		this.getContentPane().add(buttons);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_reset)
		{
			pf.setText("");
			t_user.setText("");
			t_user.requestFocusInWindow();
		}
		if(e.getSource()==b_log)
		{
			String u = t_user.getText();
			char [] pass = pf.getPassword();
			String pa = new String(pass);
			try
			{
				if(t_user.getText().equals("") | pf.getText().equals(""))
				{
					JOptionPane.showMessageDialog(this,"Please Enter the Details","Data Missing",3);
					t_user.requestFocusInWindow();
				}
				else if(count < 3)
				{
					if(d.check(u,pa))
					{
						this.dispose();						
						desktop.m_report.setEnabled(true);
						desktop.m_tools.setEnabled(true);
					}
					else
					{
						JOptionPane.showMessageDialog(this,"Sorry, Access Denied","Login Error",0);
						count=count+1;
					}
				}
				else
				{	
					JOptionPane.showMessageDialog(this,"Application is Exiting Now!!!...","Error",2);
					System.exit(0);
				}	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"error",0);
			}						
		}
	}
}
