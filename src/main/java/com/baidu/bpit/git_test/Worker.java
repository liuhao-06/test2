package com.baidu.bpit.git_test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Worker {
    
    public static String[] getServerConfig(){
        Properties properties = new Properties();
        InputStream is = null;
        String[] configs = new String[5];
        try {
            //is = new FileInputStream(file);
            is = DBConnectUtil.class.getResourceAsStream("/com/baidu/bpit/git_test/jdbc.properties");
            properties.load(is);
            configs[0]=properties.getProperty("jdbc.username");
            configs[1]=properties.getProperty("jdbc.password");
            configs[2]=properties.getProperty("jdbc.driver");
            configs[3]=properties.getProperty("jdbc.url");
            configs[4]=properties.getProperty("server.id");
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
        return configs;
    }

    public static boolean login(String name,String pwd,String ip){
        DBConnectUtil db = null;
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        String server_id = getServerConfig()[4];
        String host_ip = getHostIp();
        try{         
            // 获取IP地址            
            
            db = new DBConnectUtil();
            String sql = "select * from users where username='"+name+"' and password='"+pwd+"'";
            ResultSet rs = db.query(sql);
            boolean loginOk = false;
            if(rs!=null&&rs.next()){                
                loginOk = true;
            }
            if(loginOk){
                sql = "insert into logs (log_type,log_info,log_time,log_ip,host_ip,server_id) values"
                        + " ('login','"+name+" login sucess!','"+time+"','"+ip+"','"+host_ip+"','"+server_id+"')";
            }else{
                sql = "insert into logs (log_type,log_info,log_time,log_ip,host_ip,server_id) values "
                        + "('login','"+name+" login fail!','"+time+"','"+ip+"','"+host_ip+"','"+server_id+"')";
            }
            db.update(sql);
            return loginOk;
        }catch(Exception e){
            e.printStackTrace();
            String sql = "insert into logs (log_type,log_info,log_time,log_ip,host_ip,server_id) values"
                    + " ('login','login exception:"+e.getMessage()+"','"+time+"','"+ip+"','"+host_ip+"','"+server_id+"')";
            try {
                db.update(sql);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }finally{
            if(db!=null)    db.close();
        }
        return false;
    }
    
    public static List<Map> getUserList(){
        List<Map> li = new ArrayList<Map>();
        DBConnectUtil db = null;
        try{
            db = new DBConnectUtil();
            String sql = "select * from users";
            ResultSet rs = db.query(sql);
            if(rs!=null){
                while(rs.next()){
                    Map map = new HashMap();
                    map.put("id", rs.getString("id"));
                    map.put("username", rs.getString("username"));
                    map.put("password", rs.getString("password"));
                    String type = rs.getString("usertype");
                    if(type.equals("1")){
                        map.put("usertype", "管理员");
                    }else{
                        map.put("usertype", "普通用户");
                    }
                    map.put("modify_time", rs.getString("modify_time"));
                    li.add(map);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(db!=null)    db.close();
        }
        return li;
    }

    /** 
     * 多IP处理，可以得到最终ip 
     * @return 
     */  
    public static String getHostIp(){
            String localip = null;// 本地IP，如果没有配置外网IP则返回它  
            String netip = null;// 外网IP  
            try {  
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface  
                        .getNetworkInterfaces();  
                InetAddress ip = null;  
                boolean finded = false;// 是否找到外网IP  
                while (netInterfaces.hasMoreElements() && !finded) {  
                    NetworkInterface ni = netInterfaces.nextElement();  
                    Enumeration<InetAddress> address = ni.getInetAddresses();  
                    while (address.hasMoreElements()) {  
                        ip = address.nextElement();  
                      /*System.out.println(ni.getName() + ";" + ip.getHostAddress()  
                             + ";ip.isSiteLocalAddress()="  
                              + ip.isSiteLocalAddress()  
                              + ";ip.isLoopbackAddress()="  
                              + ip.isLoopbackAddress());  */
                        if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()  
                                && ip.getHostAddress().indexOf(":") == -1 ) {// 外网IP  
                            netip = ip.getHostAddress();  
                            finded = true;  
                            //System.out.println("netip="+netip);
                            break;  
                        } else if (ip.isSiteLocalAddress()  
                                && !ip.isLoopbackAddress()  
                                && ip.getHostAddress().indexOf(":") == -1 && ni.getName().indexOf("eth")==-1) {// 内网IP  
                            localip = ip.getHostAddress(); 
                            //System.out.println("localip="+localip);
                        }  
                    }  
                }  
            } catch (SocketException e) {  
                e.printStackTrace();  
            }  
            if (netip != null && !"".equals(netip)) {  
                return netip;  
            } else {  
                return localip;  
            }  
        }  
    
}
