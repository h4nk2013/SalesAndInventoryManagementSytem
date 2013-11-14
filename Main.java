package Lifestyle.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.net.*;
import BLogic.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

class Main 
{
	public static void main(String[] args) 
	{
		setApplicationLook();
		new desktop();
	}
	
	public static void setApplicationLook() {
        UIManager.put("Button.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("ToggleButton.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("Label.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("ComboBox.font", new FontUIResource(new Font("Verdana", 0, 11)));
        UIManager.put("List.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("TitledBorder.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("Panel.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("RadioButton.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("Menu.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("TextField.font", new FontUIResource(new Font("Verdana", 0, 11)));
        UIManager.put("CheckBox.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("FormattedTextField.font", new FontUIResource(new Font("Verdana", 0, 11)));
        UIManager.put("TabbedPane.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("MenuItem.font", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("Table.font", new FontUIResource(new Font("Verdana", 0, 11)));
        UIManager.put("InternalFrame.titleFont", new FontUIResource(new Font("Verdana", 1, 11)));
        UIManager.put("Spinner.font", new FontUIResource(new Font("Verdana", 1, 11)));
    }
}



class desktop extends JFrame implements ActionListener,MouseListener
{
	//--------Controls of the form------
	static JDesktopPane 
			d = new JDesktopPane();
	JInternalFrame 
			i = new JInternalFrame();

	Dimension 
			dim = Toolkit.getDefaultToolkit().getScreenSize();

	int tab=0;

	//=========Setting MenuBar and MenuItems=======
	 JMenuBar bar = new JMenuBar();

	JMenu 
			m_file = new JMenu("File"),
			m_prod = new JMenu("Product Info"),
			m_sales = new JMenu("Sales");

	static JMenu m_report = new JMenu("Reports");
	static JMenu m_tools = new JMenu("Administrator Tools");
	
	JMenu m_help = new JMenu("Help");

	JMenuItem m_search = new JMenuItem("Search");

			

	JMenuItem
		//------MenuItems------------
			
			//---------File-------------
			iLogin = new JMenuItem("Login"),
			iChangeLook = new JMenuItem("Change Look"),
			iExit = new JMenuItem("Exit"),

			//-------Product Info------
			iAdd = new JMenuItem("Add Product"),
			iMod = new JMenuItem("Update Product"),
			iDel = new JMenuItem("Delete Product"),

			//iProd_name = new JMenuItem("By Product Name"),
			//iDept_name = new JMenuItem("By Department Name"),
			//iProd_id = new JMenuItem("By Product ID"),

			//---------Sales-----------
			iBill = new JMenuItem("Make Invoice",new ImageIcon("Res/ShoppingCart_N.gif")),

			//---------Report--------
			iSupplier_rep = new JMenuItem("Supplier's Reports",new ImageIcon("Res/bluepage.gif")),
			iSales_rep = new JMenuItem("Sales Report",new ImageIcon("Res/bluepage.gif")),
			iProd_rep = new JMenuItem("Product Report",new ImageIcon("Res/bluepage.gif")),
			iCsr_rep = new JMenuItem("Sales Representative's Report",new ImageIcon("Res/bluepage.gif")),
			iCust_rep = new JMenuItem("Customer Report",new ImageIcon("Res/bluepage.gif")),

			//--------Admin Tools---------
			iSupp = new JMenuItem("Supplier Details"),
			iDetails = new JMenuItem("Manager Accounts"),
			iCsrDetails = new JMenuItem("Sales Representative"),
			
			//---------Help----------
			iAbout = new JMenuItem("About Lifestyle",new ImageIcon("Res/HelpCenter.gif")),
			iOnline = new JMenuItem("Go Online",new ImageIcon("Res/web.gif")),

			
			
			pi_add = new JMenuItem("Add Product"),
			pi_mod = new JMenuItem("Update Product"),
			pi_del = new JMenuItem("Delete Product"),
			pi_dname = new JMenuItem("By Dept Name"),
			pi_pname = new JMenuItem("By Prod Name"),
			pi_pid = new JMenuItem("By Prod Id"),
			pi_bill = new JMenuItem("Make Invoice");

		//--------Instanstiating ToolBar----------
	JToolBar 
			t_bar = new JToolBar();		

	JPopupMenu 
			pop = new JPopupMenu();

	JButton 
		b_add = new JButton(new ImageIcon("Res/Feedicons v.2/comment_rss_add_32.png")),
		b_del = new JButton(new ImageIcon("Res/Feedicons v.2/comment_rss_add_delete_32.png")),
		b_search = new JButton(new ImageIcon("Res/UII_Icons/32x32/search.png")),
		b_bill = new JButton(new ImageIcon("Res/UII_Icons/32x32/shopping_cart_add.png")),
		b_exit = new JButton(new ImageIcon("Res/UII_Icons/32x32/exit.png"));
		
	//============Variables for SysTray==========

	TrayIcon icon;

	SystemTray st;

	MenuItem 
		smi_add = new MenuItem("Add Products"),
		smi_mod= new MenuItem("Update Products"),
		smi_exit = new MenuItem("Exit"),
		smi_del = new MenuItem("Delete Products"),
		smi_login = new MenuItem("Login"),
		smi_prod_name = new MenuItem("By Product Name"),
		smi_dept_name = new MenuItem("By Department Name"),
		smi_prod_id = new MenuItem("By Product ID");

	Menu 
		smi_search = new Menu("Search");

	PopupMenu 
		pm = new PopupMenu(); 

	//=========================================
		

	public boolean adm = true;
	static Connection con;  
	
	
	public desktop()
	{
		setTitle("Welcome to Lifestyle");
		setLayout(new BorderLayout());
		setSize(dim.width,dim.height-27);			

		addControls();		
		addSystemTray();

		setJMenuBar(bar);
		setVisible(true);
		setDefaultCloseOperation(3);
	}
	

	//============================================================

	public void addSystemTray()
	{
		try
		{
			st = SystemTray.getSystemTray();
			icon = new TrayIcon(new ImageIcon("Res/Feedicons v.2/RSS_16.png").getImage());
			st.add(icon);
			
			pm.add(smi_search);
			smi_search.add(smi_prod_name);
			smi_search.add(smi_dept_name);
			smi_search.add(smi_prod_id);
			pm.add(smi_add);
			pm.add(smi_mod);
			pm.add(smi_del);
			pm.add(smi_login);
			pm.addSeparator();
			pm.add(smi_exit);
			icon.setPopupMenu(pm);
					
			
			icon.setToolTip("Application Running");
			icon.displayMessage("LIFESTYLE", "Application is started !\nRight click here to Display !!", TrayIcon.MessageType.INFO);
			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Error" + ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	//============================================================

	public void addControls()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:LifeDs","sa","");			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
		}

		bar.add(m_file);
		bar.add(m_prod);
		bar.add(m_sales);
		bar.add(m_report);
		bar.add(m_tools);
		bar.add(m_help);

		m_file.add(iLogin);
		m_file.add(iChangeLook);
		m_file.addSeparator();
		m_file.add(iExit);

		m_prod.add(iAdd);
		m_prod.add(iMod);
		m_prod.add(iDel);
		m_prod.addSeparator();
		m_prod.add(m_search);

		//m_search.add(iProd_name);
		//m_search.add(iDept_name);
		//m_search.add(iProd_id);
		
		m_sales.add(iBill);

		m_report.add(iSupplier_rep);
		m_report.add(iProd_rep);
		m_report.add(iCsr_rep);
		m_report.add(iSales_rep);
		m_report.add(iCust_rep);

		m_tools.add(iSupp);
		m_tools.add(iDetails);
		m_tools.add(iCsrDetails);

		m_help.add(iAbout);
		m_help.addSeparator();
		m_help.add(iOnline);

		//--------Disabled Menus---------
		m_report.setEnabled(false);
		m_tools.setEnabled(false);

		//---------ToolBar------------
		t_bar.setRollover(false);
		t_bar.setFloatable(false);

		t_bar.addSeparator();
		t_bar.add(b_add);
		t_bar.addSeparator();
		t_bar.addSeparator();
		t_bar.add(b_del);
		t_bar.addSeparator();
		t_bar.addSeparator();
		t_bar.add(b_search);
		t_bar.addSeparator();
		t_bar.addSeparator();
		t_bar.add(b_bill);
		t_bar.addSeparator();
		t_bar.addSeparator();
		t_bar.add(b_exit);


		//--------PopupMenus----------
		JMenu 
			pm_search = new JMenu("Search");
		
		pm_search.add(pi_pname);
		pm_search.add(pi_dname);
		pm_search.add(pi_pid);

		JMenu 
			pm_prod = new JMenu("Product Info");
		pm_prod.add(pi_add);
		pm_prod.add(pi_mod);
		pm_prod.add(pi_del);

		pop.add(pm_prod);
		pop.add(pm_search); 
		pop.add(pi_bill);

		//=========Setting KeyStrokes==========

		m_file.setMnemonic(KeyEvent.VK_F);
		m_prod.setMnemonic(KeyEvent.VK_P);
		m_sales.setMnemonic(KeyEvent.VK_S);
		m_report.setMnemonic(KeyEvent.VK_R);
		m_tools.setMnemonic(KeyEvent.VK_A);
		m_help.setMnemonic(KeyEvent.VK_H);
		
		iAdd.setMnemonic(KeyEvent.VK_A);
		KeyStroke k_add = KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK);
		iAdd.setAccelerator(k_add);

		iMod.setMnemonic(KeyEvent.VK_U);
		KeyStroke k_mod = KeyStroke.getKeyStroke(KeyEvent.VK_U,Event.CTRL_MASK);
		iMod.setAccelerator(k_mod);

		iDel.setMnemonic(KeyEvent.VK_D);
		KeyStroke k_del = KeyStroke.getKeyStroke(KeyEvent.VK_D,Event.CTRL_MASK);
		iDel.setAccelerator(k_del);
		
		iBill.setMnemonic(KeyEvent.VK_B);
		KeyStroke k_bill = KeyStroke.getKeyStroke(KeyEvent.VK_B,Event.CTRL_MASK);
		iBill.setAccelerator(k_bill);				

		//-------Adding Listeners------
		iLogin.addActionListener(this);
		iChangeLook.addActionListener(this);
		iExit.addActionListener(this);
		iAdd.addActionListener(this);
		iMod.addActionListener(this);
		iDel.addActionListener(this);
		m_search.addActionListener(this);
		//iProd_name.addActionListener(this);
		//iDept_name.addActionListener(this);
		//iProd_id.addActionListener(this);
		iBill.addActionListener(this);
		iSupplier_rep.addActionListener(this);
		iSales_rep.addActionListener(this);
		iCsr_rep.addActionListener(this);
		iProd_rep.addActionListener(this);
		iCust_rep.addActionListener(this);
		iSupp.addActionListener(this);
		iDetails.addActionListener(this);
		iCsrDetails.addActionListener(this);
		iAbout.addActionListener(this);
		iOnline.addActionListener(this);

		//======ActionListener 4 PopupMenu========
		pi_add.addActionListener(this);
		pi_mod.addActionListener(this);
		pi_del.addActionListener(this);
		pi_dname.addActionListener(this);
		pi_pname.addActionListener(this);
		pi_pid.addActionListener(this);
		pi_bill.addActionListener(this);		

		//======ActionListener 4 SysTray========
		smi_add.addActionListener(this);
		smi_mod.addActionListener(this);
		smi_del.addActionListener(this);
		smi_prod_name.addActionListener(this);
		smi_dept_name.addActionListener(this);
		smi_prod_id.addActionListener(this);
		smi_login.addActionListener(this);
		smi_exit.addActionListener(this);	

		//============ActionListener 4 ToolBar=========
		b_add.addActionListener(this);
		b_del.addActionListener(this);		
		b_search.addActionListener(this);		
		b_bill.addActionListener(this);
		b_exit.addActionListener(this);

		//=========Adding ToolTips for ToolBar Buttons==========
		b_add.setToolTipText("Adding New Product");
		b_del.setToolTipText("Deleting Product");
		b_search.setToolTipText("Searching Products");
		b_bill.setToolTipText("Prepare Invoice");
		b_exit.setToolTipText("Exit");

		this.addMouseListener(this);

		add(d);		
		add(t_bar,BorderLayout.NORTH);		
	}

	public void mousePressed(MouseEvent e)
	{
		if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
		{
			pop.show(this,e.getX(),e.getY());
		}
	}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}

	public void actionPerformed(ActionEvent e)
	{
		//==========ToolBar Buttons=========
		if(e.getSource()==iChangeLook) {
			ChangeLookDialog cld = new ChangeLookDialog(this);
			cld.setVisible(true);
		}
		if(e.getSource()==b_add)
		{
			try
			{
				addProdFrame addProd = new addProdFrame();				
				d.add(addProd);				
				addProd.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}
		if(e.getSource()==b_del)
		{
			try
			{
				delProdFrame dp = new delProdFrame();				
				d.add(dp);				
				dp.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==b_search)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==b_bill)
		{
			try
			{
				makeBill b = new makeBill();				
				d.add(b);				
				b.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==b_exit)
		{
			System.exit(0);
		}

		//============Menus===============
		if(e.getActionCommand().equals("Exit"))
		{
			System.exit(0);
		}
		if(e.getSource()==iLogin)
		{
			try
			{
				LoginFrame l = new LoginFrame();				
				d.add(l);				
				l.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}		
		}
		if(e.getSource()==iAdd)
		{
			try
			{
				addProdFrame addProd = new addProdFrame();				
				d.add(addProd);				
				addProd.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}	
		}
		if(e.getSource()==iMod)
		{
			try
			{
				updateProdFrame mod = new updateProdFrame();				
				d.add(mod);				
				mod.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}	
		}
		if(e.getSource()==iDel)
		{
			try
			{
				delProdFrame dp = new delProdFrame();				
				d.add(dp);				
				dp.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}
		if(e.getSource()==m_search)
		{
			try
			{
				searchFrame s = new searchFrame();
				d.add(s);
				s.setSelected(true);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", 0);
			}
		}
		/*if(e.getSource()==iProd_name)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==iDept_name)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}

		if(e.getSource()==iProd_id)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}*/

		if(e.getSource()==iBill)
		{
			try
			{
				makeBill b = new makeBill();				
				d.add(b);				
				b.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}

		if(e.getSource()==iSupp)
		{
			try
			{
				supplierDetails sd = new supplierDetails();				
				d.add(sd);				
				sd.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}				
		}
		if(e.getSource()==iDetails)
		{
			try
			{
				updateDetails mgr = new updateDetails();				
				d.add(mgr);				
				mgr.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}
		if(e.getSource()==iCsrDetails)
		{
			try
			{
				custRepDetails cust = new custRepDetails();				
				d.add(cust);				
				cust.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}
		if(e.getSource()==iSales_rep)
		{
			try
			{
				salesReport sales = new salesReport();				
				d.add(sales);				
				sales.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}
		if(e.getSource()==iSupplier_rep)
		{
			try
			{
				supplierReport supp = new supplierReport();				
				d.add(supp);				
				supp.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}		
		}
		if(e.getSource()==iProd_rep)
		{
			try
			{
				prodReport prod = new prodReport();				
				d.add(prod);				
				prod.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}		
		}
		if(e.getSource()==iCsr_rep)
		{
			try
			{
				custSalesRepFrame csr = new custSalesRepFrame();				
				d.add(csr);				
				csr.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}					
		}
		if(e.getSource()==iCust_rep)
		{
			try
			{
				customerReport cust = new customerReport();				
				d.add(cust);				
				cust.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}					
		}
		if(e.getSource()==iAbout)
		{
			try
			{
				aboutLifestyle life = new aboutLifestyle();				
				d.add(life);				
				life.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}					
		}
		if(e.getSource()==iOnline)
        {
             Desktop dsk = Desktop.getDesktop();
			 try
			 {
				dsk.browse(new URI("http://www.lifestylestores.com"));	
			 }
			 catch (Exception ex)
			 {
				 JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",1);
			 }             
        }

		//============Popup Menu Items Event===========
		if(e.getSource()==pi_add)
		{
			try
			{
				addProdFrame addProd = new addProdFrame();				
				d.add(addProd);				
				addProd.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}	
		}
		if(e.getSource()==pi_mod)
		{
			try
			{
				updateProdFrame mod = new updateProdFrame();				
				d.add(mod);				
				mod.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}			
		}

		if(e.getSource()==pi_del)
		{
			try
			{
				delProdFrame del = new delProdFrame();				
				d.add(del);				
				del.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==pi_bill)
		{
			try
			{
				makeBill b = new makeBill();				
				d.add(b);				
				b.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==pi_pname)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==pi_dname)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==pi_pid)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}

		//==============System Tray Events==============
		if(e.getSource()==smi_add)
		{
			try
			{
				addProdFrame addProd = new addProdFrame();				
				d.add(addProd);				
				addProd.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}	
		}
		if(e.getSource()==smi_mod)
		{
			try
			{
				updateProdFrame mod = new updateProdFrame();				
				d.add(mod);				
				mod.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}	
		}
		if(e.getSource()==smi_del)
		{
			try
			{
				delProdFrame del = new delProdFrame();				
				d.add(del);				
				del.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==smi_prod_name)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		if(e.getSource()==smi_dept_name)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}
		
		if(e.getSource()==smi_prod_id)
		{
			try
			{
				searchFrame s = new searchFrame();				
				d.add(s);				
				s.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}
		}

		if(e.getSource()==smi_login)
		{
			try
			{
				LoginFrame l = new LoginFrame();				
				d.add(l);				
				l.setSelected(true);   
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",0);
			}	
		}
		if(e.getSource()==smi_exit)
		{
			System.exit(0);
		}
	}
}

