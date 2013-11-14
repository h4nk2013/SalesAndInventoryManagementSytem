package BLogic;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class dept 
{
	private String dept_no;
	private String dept_name;
	private String mgr_name;
	private String user_name;
	private String pass_word;
	public Connection con;
	ResultSet rs;
	boolean adm;

	public dept(Connection con)
	{
		this.con = con;
	}
	
	//==========Login Methods============
	public boolean check(String u,String p) throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select * from dept");
		while(rs.next())
		{
			if(u.equals(rs.getString(4)) && p.equals(rs.getString(5)))
			{
				adm=true;
				return true;
			}
			else
			{
				adm = false;
			}				
		}			
		return false;				
	}

	public boolean validate(String u,String m,String p) throws Exception
	{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select * from dept");
		
		while(rs.next())
		{
			if(m.equals(rs.getString(3)) && u.equals(rs.getString(4)) && p.equals(rs.getString(5)))
			{
				adm=true;
				return true;
			}
			else
			{
				adm = false;
			}				
		}				
		return false;
	}

	//=================================================
	public void getDeptdata(String dept) throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);  
		rs= stmt.executeQuery("select deptno,mgr from dept where dname = '"+dept+"'");
		while(rs.next())
		{
			dept_no=rs.getString(1);
			mgr_name=rs.getString(2);
		}
		rs.close();
		stmt.close();
		
	}
	
	public void saveDetails(String dno,String dname,String mgr,String uname) throws Exception
	{
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs= stmt.executeQuery("Select * from Dept where deptno ='"+dno+"'");
		
		
			rs.next();
			rs.updateString(1,dno);
			rs.updateString(2,dname);
			rs.updateString(3,mgr);
			rs.updateString(4,uname);			
			
			rs.updateRow();
		
		rs.close();
		stmt.close();
	}
	//=================================================

	public void savePassDetails(String dno,String dname,String mgr,String uname,String pass) throws Exception
	{
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs= stmt.executeQuery("Select * from Dept where deptno ='"+dno+"'");
		
		rs.next();
		rs.updateString(1,dno);
		rs.updateString(2,dname);
		rs.updateString(3,mgr);
		rs.updateString(4,uname);
		rs.updateString(5,pass);	
		rs.updateRow();
		
		rs.close();
		stmt.close();
	}

	public String getDeptNo()
	{
		return dept_no;
	}

	public void setDeptNo(String dept_no)
	{
		this.dept_no = dept_no;
	}
	
	public String getDeptName()
	{
		return dept_name;
	}
	public void setDeptName(String dept_name)
	{
		this.dept_name = dept_name;
	}
	
	public String getMgrName()
	{
		return mgr_name;
	}
	public void setMgrName(String mgr_name)
	{
		this.mgr_name = mgr_name;
	}

	public String getUserName()
	{
		return user_name;
	}
	public void setUserName(String user_name)
	{
		this.user_name = user_name;
	}
	
	public String getPassWord()
	{
		return pass_word;
	}
	public void setPassWord(String pass_word)
	{
		this.pass_word = pass_word;
	}
}
