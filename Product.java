package BLogic;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Product 
{
	private int pid;
	private String prod_name;
	private String prod_supp;
	private String prod_brand;
	private int prod_qty;
	private int prod_price;
	private int tot_price;
	private String dept_no;
	private String dept_name;
	public Connection con;
	ResultSet rs;
	int i;
	boolean adm;
	
	public Product(Connection con)
	{
		this.con = con;
	}	

	public ArrayList addBrand() throws Exception
	{
		ArrayList arr = new ArrayList();
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select bname from supplier");
		while (rs.next())
		{
			arr.add(rs.getString(1));
		}
		stmt.close();
		return arr;
	}

	public ArrayList addSupplier() throws Exception
	{
		ArrayList arr = new ArrayList();
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select distinct sname from supplier");
		while (rs.next())
		{
			arr.add(rs.getString(1));
		}
		stmt.close();
		return arr;
	}
	
	public int autoGenerate() throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select max(prod_id) from prod");
		while(rs.next())
		{
			i = rs.getInt(1)+1;			
		}
		stmt.close();
		return i;
	}

	public void newProduct(int id,String pname,String sname, String bname,int qty,int price,int total,java.sql.Date d,String deptno,String dname) throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select prod_id,prod_name,prod_supp,prod_brand,prod_qty,prod_price,total_price,pdate,sub_deptno ,sub_dname from prod where sub_deptno='"+deptno+"'");
		rs.next();
		rs.updateInt(1,id);
		rs.updateString(2,pname);
		rs.updateString(3,sname);
		rs.updateString(4,bname);
		rs.updateInt(5,qty);
		rs.updateInt(6,price);
		rs.updateInt(7,total);
		rs.updateDate(8,d);
		rs.updateString(9,deptno);
		rs.updateString(10,dname);
		rs.insertRow();
		stmt.close();
	}	

	//==========Update Product Methods=========
	
	public ArrayList getPName() throws Exception
	{
		ArrayList ar = new ArrayList();
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select distinct prod_name from prod");
		while (rs.next())
		{
			ar.add(rs.getString(1));
		}
		stmt.close();
		return ar;
	}

	public ArrayList getSupplier() throws Exception
	{
		ArrayList ar = new ArrayList();
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select distinct prod_supp from prod");
		while(rs.next())
		{
			ar.add(rs.getString(1));
		}
		stmt.close();
		return ar;
	}

	public ArrayList getBrand() throws Exception
	{
		ArrayList ar = new ArrayList();
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select distinct prod_brand from prod");
		while(rs.next())
		{
			ar.add(rs.getString(1));
		}
		stmt.close();
		return ar;
	}
	
	public void updateProduct(String p,String s,String b,int q,int pr,int total,int id) throws Exception
	{
		PreparedStatement ps = con.prepareStatement("update prod set prod_supp=?, prod_qty=?,prod_price=?,total_price=? from prod where prod_id=? and prod_brand=? and prod_supp=?");
		ps.setString(1,s);
		ps.setInt(2,q);
		ps.setInt(3,pr);
		ps.setInt(4,total);
		ps.setInt(5,id);
		ps.setString(6,b);
		ps.setString(7,s);
		
		int rs = ps.executeUpdate();
		ps.close();
	}	
	
	//==========Search Methods==============
	
	public Vector searchProductName(Vector d,int length,String n) throws Exception
	{
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);		
			rs = stmt.executeQuery("select * from prod where prod_name like '"+ n + "%' order by prod_id" );
			while(rs.next())
			{
				String arr [] = new String[length];
				arr[0] = Integer.toString(rs.getInt(1));	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = Integer.toString(rs.getInt(5));
				arr[5] = Integer.toString(rs.getInt(6));
				arr[6] = Integer.toString(rs.getInt(7));
				arr[7] = rs.getDate(8).toString();
				arr[8] = rs.getString(9);
				arr[9] = rs.getString(10);
				d.addElement(arr);				
			}
			stmt.close();			
			return d;					
	}

	public Vector searchDeptName(Vector d,int length,String n) throws Exception
	{
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);			
			rs = stmt.executeQuery("select * from prod where sub_deptno in (select sub_deptno  from sub_sections where deptno in (select deptno from dept where dname = '"+ n + "')) order by prod_id");
			while(rs.next())
			{
				String arr [] = new String[length];
				arr[0] = Integer.toString(rs.getInt(1));	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = Integer.toString(rs.getInt(5));
				arr[5] = Integer.toString(rs.getInt(6));
				arr[6] = Integer.toString(rs.getInt(7));
				arr[7] = rs.getDate(8).toString();
				arr[8] = rs.getString(9);
				arr[9] = rs.getString(10);
				d.addElement(arr);				
			}			
			stmt.close();
			return d;					
	}

	public Vector searchProdId(Vector d,int l,String id) throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select * from prod where prod_id like '" + id + "%' order by prod_id");
		while(rs.next())
		{
				String arr [] = new String[l];
				arr[0] = Integer.toString(rs.getInt(1));	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = Integer.toString(rs.getInt(5));
				arr[5] = Integer.toString(rs.getInt(6));
				arr[6] = Integer.toString(rs.getInt(7));
				arr[7] = rs.getDate(8).toString();
				arr[8] = rs.getString(9);
				arr[9] = rs.getString(10);
				d.addElement(arr);
		}
		stmt.close();
		return d;
	}
	
	public Vector searchSupplierName(Vector d,int l,String sname) throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select * from supplier where sname like '" + sname + "%' order by sid");
		while(rs.next())
		{
				String arr [] = new String[l];
				arr[0] = Integer.toString(rs.getInt(1));	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);
				arr[5] = rs.getString(6);
				arr[6] = rs.getString(7);
				d.addElement(arr);
		}
		stmt.close();
		return d;
	}	

	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}

	public String getProdName()
	{
		return prod_name;
	}

	public void setProdName(String prod_name)
	{
		this.prod_name = prod_name;
	}

	public String getProdSupp()
	{
		return prod_supp;
	}

	public void setProdSupp(String prod_supp)
	{
		this.prod_supp = prod_supp;
	}

	public String getProdBrand()
	{
		return prod_brand;
	}

	public void setProdBrand(String prod_brand)
	{
		this.prod_brand = prod_brand;
	}

	public int getProdQty()
	{
		return prod_qty;
	}

	public void setProdQty(int prod_qty)
	{
		this.prod_qty = prod_qty;
	}

	public int getProdPrice()
	{
		return prod_price;
	}

	public void setProdPrice(int prod_price)
	{
		this.prod_price = prod_price;
	}

	public int getTotPrice()
	{
		return tot_price;
	}

	public void setTotPrice(int tot_price)
	{
		this.tot_price = tot_price;
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
}
