package DBModels;

import java.util.*;
import Models.*;
import config.DBConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBModel {

    public UserDBModel() {
    }

    public static void main(String [] args){
        Vector<BuildingType> types = new Vector<>();
        int ID;
        String name;
        
        try{
            Connection conn = DBConfig.getConnection();
        
            PreparedStatement prepStmt = conn.prepareStatement("select * from BuildingTypes");

            ResultSet result = prepStmt.executeQuery();
            while(result.next()){ 
                ID = result.getInt("ID");
                name = result.getString("Name");

                types.add(new BuildingType(ID, name));
            }
            System.out.println(types.get(0).getName());
            result.close();
            prepStmt.close();
            conn.close();
        }catch (InstantiationException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    public boolean authenticateUser(String name,String password)
    {
        boolean valid=false;
        try {
            Statement stmt;
            try (Connection conn = DBConfig.getConnection()) {
                stmt = conn.createStatement();
                ResultSet result=stmt.executeQuery("SELECT * FROM users WHERE Username = '"+name+"'"+" AND Password = '"+password+"'"+";");
                if(result.next())
                {
                    valid=true;
                }
                conn.close();
            }
            stmt.close();  
            
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valid;

    }
    public boolean validateUserName(String name) throws InstantiationException, ClassNotFoundException, IllegalAccessException, SQLException
    {
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery("SELECT * FROM users where Username = '"+name+"'"+";");
        
        if(result.next())
        {
            stmt.close();
            conn.close();
            return false;
        }
        stmt.close();
        conn.close();
        return true;
        
    }
    public boolean addNewUser(User user) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        int numrows=stmt.executeUpdate("INSERT INTO users (Username,Password,Phone) values( '"+user.getUsername()+"' , '"+user.getPassword()+"' , '"+user.getPhone()+"' );");
        conn.close();
        return numrows>0;
    }
    public boolean savePhoneNumber(String name, String phoneNumber) throws InstantiationException, SQLException, IllegalAccessException, IllegalAccessException, IllegalAccessException, IllegalAccessException, ClassNotFoundException {
        
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        int result=stmt.executeUpdate("UPDATE users SET Phone = '"+phoneNumber+"' WHERE Username = '"+name+"'"+";");
        conn.close();
        stmt.close();
        return result>0;
    }
    public boolean deletePhoneNumber(String name)throws InstantiationException, SQLException, IllegalAccessException, IllegalAccessException, IllegalAccessException, IllegalAccessException, ClassNotFoundException {
       Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery("SELECT * FROM users where Username = '"+name+"'"+";");
        String phone="";
        int row=0;
        if(result.next())
        {
            phone=result.getString("Phone");
            System.out.println(phone);
        }
        if(!(phone==""))
        {
           row=stmt.executeUpdate("UPDATE users SET Phone = "+NULL+" WHERE Username = '"+name+"'"+";");
        }
        conn.close();
        stmt.close();
        return row>0;
    }
    public boolean updatePassword(String name, String password) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        int result=stmt.executeUpdate("UPDATE users SET Password = '"+password+"' WHERE Username = '"+name+"'"+";");
        conn.close();
        stmt.close();
        return result>0;
    }
    public boolean savePicture(File f,String name) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {
        Connection conn=DBConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET Picture = (?) WHERE Username = '"+name+"'"+";");
        FileInputStream fis = new FileInputStream(f);
        pstmt.setBlob(1, (InputStream) fis, (int) (f.length()));
        int s = pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        return s>0;
    }

 
    public boolean deletePicture(String name) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException 
    {
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        int result=stmt.executeUpdate("UPDATE users SET Picture = "+NULL+" WHERE Username = '"+name+"'"+";");
        conn.close();
        stmt.close();
        return result>0;
    }
    public User getUser(String name) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException 
    {
        User user=new User();
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery("SELECT * FROM users where Username = '"+name+"'"+";");
        if(result.next())
        {
            user.setUsername(name);
            user.setID(result.getInt("ID"));
            user.setPhone(result.getString("Phone"));
            user.setPicture(result.getBlob("Picture"));
        }
        return user;

    }

    public boolean addInterest(int size,int statusID,int typeID,int userID) {
        
        try {
            Connection conn = DBConfig.getConnection();
            PreparedStatement prepStmt = conn.prepareStatement("insert into Interests values(null,?,?,?,?);");
            
            prepStmt.setInt(1, size);
            prepStmt.setInt(2, statusID);
            prepStmt.setInt(3, typeID);
            prepStmt.setInt(4, userID);
            
            int affectedRows = prepStmt.executeUpdate();
            prepStmt.close();
            conn.close();
            
            if(affectedRows > 0)
                return true;
            
        } catch (InstantiationException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public Vector<BuildingStatus> fetchBuildingStatuses(){
        Vector<BuildingStatus> statuses = new Vector<>();
        int ID;
        String name;
        
        try{
            Connection conn = DBConfig.getConnection();
            PreparedStatement prepStmt = conn.prepareStatement("select * from BuildingStatuses");

            ResultSet result = prepStmt.executeQuery();
            while(result.next()){ 
                ID = result.getInt("ID");
                name = result.getString("Name");

                statuses.add(new BuildingStatus(ID, name));
            }

            result.close();
            prepStmt.close();
            conn.close();
        
        } catch (InstantiationException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return statuses;
    }
    
    public Vector<BuildingType> fetchBuildingTypes(){
        Vector<BuildingType> types = new Vector<>();
        int ID;
        String name;
        
        try{
            Connection conn = DBConfig.getConnection();
        
            PreparedStatement prepStmt = conn.prepareStatement("select * from BuildingTypes");

            ResultSet result = prepStmt.executeQuery();
            while(result.next()){ 
                ID = result.getInt("ID");
                name = result.getString("Name");

                types.add(new BuildingType(ID, name));
            }

            result.close();
            prepStmt.close();
            conn.close();
        }catch (InstantiationException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return types;
    }

    public Vector<Notification> getUserNotifications(int userID) {
        Vector<Notification> allNotifications = null;
        try {
            Connection conn = DBConfig.getConnection();
            PreparedStatement prepStmt = conn.prepareStatement("select Notifications.ID,Notifications.Text,Notifications.Time,Notifications.Link"
                                        + " from Notifications,UserNotifications"
                                        +" where UserNotifications.UserID = ? and isRead = 0"
                                        + " and UserNotifications.NotificationID = Notifications.ID");
            prepStmt.setInt(1, userID);
            ResultSet resultSet = prepStmt.executeQuery();
            allNotifications = new Vector<>();
            
            while(resultSet.next()){
                Notification notification = new Notification();
                notification.setID(resultSet.getInt("ID"));
                notification.setText(resultSet.getString("Text"));
                notification.setTime(resultSet.getDate("Time"));// ensure that it is correct
                notification.setLink(resultSet.getString("Link"));
                
                allNotifications.add(notification);
            }
            
            prepStmt.close();
            conn.close();
            
        } catch (InstantiationException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return allNotifications;
    }

    public void addNotificationToUser(int advertiserID, Notification notification) {
    }

    
    public String getPhone(int advertiserID) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Connection conn=DBConfig.getConnection();
        Statement stmt=conn.createStatement();
        ResultSet result=stmt.executeQuery("SELECT * FROM users WHERE ID = '"+advertiserID+"'"+";");
        stmt.close();
        conn.close();
        return result.getString("Phone");
    }

}