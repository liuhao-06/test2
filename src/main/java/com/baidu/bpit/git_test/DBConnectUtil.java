package com.baidu.bpit.git_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * @author    liuhao
 * @function  
 */
public class DBConnectUtil {
	
	private String url;
	private String name;
	private String pwd;
	private String clazz;
	
	private Statement stmt;
	private PreparedStatement pst;
	private Connection conn;
	
	public DBConnectUtil(){
		Properties properties = new Properties();
		//InputStream is = DBConnectUtil.class.getResourceAsStream("/com/baidu/bpit/git_test/jdbc.properties");
		/*File folder = new File("");
		System.out.println(folder.getAbsolutePath());
		File file = new File(folder.getAbsoluteFile()+File.separator+"conf.properties");*/
		InputStream is = null;
		try {
			//is = new FileInputStream(file);
		    is = DBConnectUtil.class.getResourceAsStream("/com/baidu/bpit/git_test/jdbc.properties");
			properties.load(is);
			init(properties.getProperty("jdbc.url"),properties.getProperty("jdbc.username"),
					properties.getProperty("jdbc.password"),properties.getProperty("jdbc.driver"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public DBConnectUtil(Properties properties){
		init(properties.getProperty("jdbc.url"),properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"),properties.getProperty("jdbc.driver"));
	}
	
	public DBConnectUtil(String url,String name,String pwd,String clazz){
		init(url,name,pwd,clazz);
	}
	
	private void init(String url,String name,String pwd,String clazz){
		this.url = url;
		this.name = name;
		this.pwd = pwd;
		this.clazz = clazz;
		try {
			Class.forName(clazz);
			conn= DriverManager.getConnection(url,name,pwd);
			stmt=conn.createStatement();
			//System.out.println("url="+url+" name="+name+" pwd="+pwd+" clazz="+clazz);
			//System.out.println("conn="+conn+" stmt="+stmt);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet query(String sql) throws SQLException{
		return stmt.executeQuery(sql);
	}
	
	public int update(String sql) throws SQLException{
		return stmt.executeUpdate(sql);
	}
	
	public int[] batchUpdate(String[] sql) throws SQLException{
		for(int i=0;i<sql.length;i++){
			stmt.addBatch(sql[i]);
		}
		return stmt.executeBatch();
	}
	
	public int updateWithBlob(String sql,Blob[] blobs) throws SQLException{
		 pst  = conn.prepareStatement(sql);
		 for(int i=0;i<blobs.length;i++){
			 pst.setBlob(i+1, blobs[i]);
		 }
		 return pst.executeUpdate();
	}
	
	public int updateWithBlob(String sql,InputStream[] is) throws SQLException{
		pst  = conn.prepareStatement(sql);
		for(int i=0;i<is.length;i++){
			pst.setBlob(i+1, is[i]);
		}
		return pst.executeUpdate();
	}
	
	public int updateWithBytes(String sql,List<byte[]> li) throws SQLException{
		pst  = conn.prepareStatement(sql);
		for(int i=0;i<li.size();i++){
			pst.setBytes(i+1, li.get(i));
		}
		return pst.executeUpdate();
	}
	
	public void close(){
		try {
			if(stmt!=null){
					stmt.close();
			}
			if(pst!=null){
				pst.close();
			}
			if(conn!=null){
					conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		DBConnectUtil db = null;
		try {
			db = new DBConnectUtil();

			String[] sql2 = new String[2];
			for(int i=0;i<sql2.length;i++){
				sql2[i] = "update zzz set name='name"+(i+"a")+"' where code='code"+i+"'";
			}
			int[] r = db.batchUpdate(sql2);
			for(int i=0;i<r.length;i++){
				System.out.println(i+":"+r[i]);
			}
			
			///
			String sql = "select * from zzz";
			ResultSet rs = db.query(sql);
			while(rs.next()){
				String BankCode = rs.getString(1);
				String BankName = rs.getString(2);
				System.out.println(BankCode + "," + BankName);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("cost:"+(end-start));
		
	}

}
