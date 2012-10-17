/*
 ** Copyright (C)  2012  Tomas Arnesson, Hampus Lilja, Mattias Herzog & Mathias Dosé
 ** Permission is granted to copy, distribute and/or modify this document
 ** under the terms of the GNU Free Documentation License, Version 1.3
 ** or any later version published by the Free Software Foundation;
 ** with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 ** A copy of the license is included in the section entitled "LICENSE.txt".
 */
/*
 ** This file is part of ShutApp.
 **
 ** ShutApp is free software: you can redistribute it and/or modify
 ** it under the terms of the GNU General Public License as published by
 ** the Free Software Foundation, either version 3 of the License, or
 ** (at your option) any later version.
 **
 ** ShutApp is distributed in the hope that it will be useful,
 ** but WITHOUT ANY WARRANTY; without even the implied warranty of
 ** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 ** GNU General Public License for more details.
 **
 ** You should have received a copy of the GNU General Public License
 ** along with ShutApp.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tomasar.shutapp_gcm_server;

import com.google.android.gcm.server.*;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

/*
 * This is added server package to ShutAPP Android application.
 * Copyright (C) 2012  Hampus Lilja, Tomas Arnesson, Mathias Dosé, Mathias Herzog
 */

@WebServlet(name = "GCM", urlPatterns = {"/GCM"})
public class GCM_Server extends HttpServlet implements Serializable {

    //This is the registered key for access via Google API, GCM.
    private static final String SENDER_ID = "AIzaSyAbgoOa_EvIBk81TxI8oTm8d_0he1aqzWU";
    private static final String SENDER_ID2_browser = "AIzaSyAGbXeI9lkNaqDp_oFove5dmUNqEsD5FOA";
    /*
     * !-!-! BEFORE DEPLOYING CHECK PATH BELOW !-!-!
     * Static path to server SQLite database below
    */
    private static String DBURL = "jdbc:sqlite:C:\\Users\\Hampus\\Desktop\\SugarSync\\Chalmers\\SoftwareEngineering\\GitHub\\AndroidProject\\server-package\\ShutAPP_GCM_SERVER\\src\\main\\webapp\\shutappdb.db";
    private static String DBUSER = "gcm";
    private static String DBPASS = "gcm";
    private static String DBDriver = "org.sqlite.JDBC"; 
    /*
     * MYSQL prepared statements below:
     */
     //When a new chatroom is created, put the details into DB
    private static String createNewChatRoomInDB = "INSERT INTO CHATROOMS " + "(NAME, MEMBERS, LAT, LONG, RADIUS, PASSWD) VALUES " + "(?,?,?,?,?,?)";
    private static String updateMemberListForChatRoomInDB = "UPDATE CHATROOMS SET MEMBERS=" + "?" + "  WHERE NAME=" + "?";
    private static String delUserFromSQLDB = "DELETE FROM USERS WHERE REGID" + "=" + "'" + "(?)" + "'";
    private static String getCurrentChatRoomUsers = "SELECT MEMBERS FROM CHATROOMS WHERE NAME="+"?";
    
    public GCM_Server() {
         
        super();
        SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);
        config.setSharedCache(true);
        config.setOpenMode(SQLiteOpenMode.READWRITE);
    }   
    
     /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * NOTE: Not implemented, currently only POST is being used.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //processRequest(request, response);
    }
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * HTTP GET Not currently being used.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * NOTE: Not implemented, currently only POST is being used.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * All code here handles the incoming http POST requests.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String typeOfAction = request.getParameter("action");
        //response.setContentType("text/html;charset=UTF-8");
         /*
         * This ACTION handles sending GCM message to specific chatroom.
         */ 
            if( typeOfAction.equals("chatRoomMsg")) {
        String chatRoom = request.getParameter("chatRoom");
        String userMessage = request.getParameter("Message");
        String userName = request.getParameter("user");
        sendMsgToChatRoom(buildMsg(chatRoom, userMessage, userName), convertToArrayListForGCM(chatRoom));
        Logger.getAnonymousLogger().log(Level.INFO, "Recevied HTTP POST to send msg to all in chatroom: " +chatRoom);
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
           } else if( typeOfAction.equals("createNewChatRoom")) {
        /*
         * This ACTION handles creation of a new chatroom.
         */ 
        String name = request.getParameter("NAME");
        String members = "";
        String lat = request.getParameter("LAT");
        String longitud = request.getParameter("LONG");
        String radius = request.getParameter("RADIUS");
        String passwd = request.getParameter("PASSWD");
        applyChangesToSQL(name, members, lat, longitud, radius, passwd, createNewChatRoomInDB);
        Logger.getAnonymousLogger().log(Level.INFO, "Recieved HTTP POST for creation of new chatroom.");
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
       }else if( typeOfAction.equals("leaveChatroom")) {
        /*
         * This ACTION handles the removal of a user from a chatroom.
         */  
        String chatRoom = request.getParameter("name");
        String regId = request.getParameter("regid");
        applyChangesToSQL(removeUserFromChatRoom(regId, chatRoom), chatRoom, updateMemberListForChatRoomInDB);
        Logger.getAnonymousLogger().log(Level.INFO, "Recieved HTTP POST for new user to leave chatroom: " + chatRoom);
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
        }else if( typeOfAction.equals("joinChatroom")) {
            
        /*
         * This ACTION handles joining a chatroom.
         */  
        String chatRoom = request.getParameter("name");
        String regId = request.getParameter("regid");
        Logger.getAnonymousLogger().log(Level.INFO, "Recieved HTTP POST for:" +regId +" to join chatroom: " + chatRoom);
        applyChangesToSQL(addUserToChatRoom(regId, chatRoom), chatRoom, updateMemberListForChatRoomInDB);
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
        }

}

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "ShutApp GCM Web Servlet";
    }
    
   
    
    /*
     * This class is used to build a complete message string in correct format, before being sent.
     */
    public Message buildMsg(String chatRoom, String userMessage, String userName){
        
        //Appending username to sent message, so chatlog shows who sent what.
        StringBuffer temp = new StringBuffer();
        temp.append(userName);
        temp.append(":");
        temp.append(userMessage);        
        // This is used for building message!
        // Could be either string or JSON Object!
        Message message;
        message = new Message.Builder()
               
.addData("chatroom", chatRoom)
.addData("message", temp.toString())
.build();
        
        return message;
        
    }
    
    /*
     * The following function is responsible for sending a built message to GCM server
     * inparameters are a formatted and built message, by com.google.android.gcm.server.message; class.
     * And an arraylist of regIds of devices to send to.
     */
    public void sendMsgToChatRoom(Message message, List<String> users){
    //Instance of com.android.gcm.server.Sender, that does the, transmission of a Message to GCM.
        Sender sender = new Sender(SENDER_ID);
        try {
            
            MulticastResult result = sender.send(message, users, 1);
            Logger.getAnonymousLogger().log(Level.INFO, "Built and sent MSG to {0}", users);
            if (result.getResults() != null) {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) {
                     
                }
            } else {
                int error = result.getFailure();
                System.out.println("Broadcast failure: " + error);
            }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 

    /* ---------------------------------------------------------------
     * ALL BELOW THIS LINE BELONGS TO SQL PART OF SERVER COMMUNICATION
     * This functions calls for the correct driver/path and connects to JDBC SQL Database.
     * ---------------------------------------------------------------
     */
    private static Connection connectSQLDB(){
             System.out.println("Connecting to SQLDB");
             Connection SQLconnection = null;
    try{
        Class.forName(DBDriver);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    try{
        SQLconnection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
        return SQLconnection;
    }
        catch (SQLException e){
        e.printStackTrace();
    }
    return SQLconnection;
    }
    
    /*
     * This class handles updates to SQL Database, which has two variables.
     * And uses predefined static SQL strings, with variables.
     */
    private void applyChangesToSQL(String var1, String var2, String sqlstatement){
        Connection SQLconnection = null;
        PreparedStatement ps_sql = null;
      try{
        SQLconnection = connectSQLDB();
        
        ps_sql = SQLconnection.prepareStatement(sqlstatement);
        ps_sql.setString(1, var1);
        ps_sql.setString(2, var2);
        ps_sql.executeUpdate();
        System.out.println("JAVA: Performed sql string " + sqlstatement + " with variables " + var1 +", " + var2);
      }
      catch(SQLException e){
          System.out.println(e.getMessage());
      }		
          catch (Exception e){
        e.printStackTrace();
    }
      finally {
            if (ps_sql != null) {
                         try{
                        	ps_sql.close();
                            }
                           catch(SQLException e){
                           System.out.println(e.getMessage());
            }	
                         try{
                            if (SQLconnection != null) {
				SQLconnection.close();
                            }
            }
                            catch(SQLException e){
                           System.out.println(e.getMessage());
                            }	
            }
        }
        }
        /*
     * This class handles updates to SQL Database, which has one variable.
     * And uses predefined static SQL strings, with variables.
     */
    private void applyChangesToSQL(String var1, String sqlstatement){
        Connection SQLconnection = null;
        PreparedStatement ps_sql = null;
      try{
        SQLconnection = connectSQLDB();
        
        ps_sql = SQLconnection.prepareStatement(sqlstatement);
        ps_sql.setString(1, var1);
        ps_sql.setQueryTimeout(2);
        ps_sql.executeUpdate();
        System.out.println("JAVA: Performed sql string " + sqlstatement + " with variables " + var1);
      }
      catch(SQLException e){
          System.out.println(e.getMessage());
      }		
          catch (Exception e){
        e.printStackTrace();
    }
      finally {
            if (ps_sql != null) {
                         try{
                        	ps_sql.close();
                            }
                           catch(SQLException e){
                           System.out.println(e.getMessage());
            }	
                         try{
                            if (SQLconnection != null) {
				SQLconnection.close();
                            }
            }
                            catch(SQLException e){
                           System.out.println(e.getMessage());
                            }	
            }
        }
}
    
    
    
    
     /*
     * This class handles updates to SQL Database, which has five variables.
     * And uses predefined static SQL strings, with variables.
     */
    private void applyChangesToSQL(String var1, String var2, String var3, String var4, String var5, String var6, String sqlstatement){
        Connection SQLconnection = null;
        PreparedStatement ps_sql = null;
      try{
        SQLconnection = connectSQLDB();
        
        ps_sql = SQLconnection.prepareStatement(sqlstatement);
        ps_sql.setString(1, var1);
        ps_sql.setString(2, var2);
        ps_sql.setString(3, var3);
        ps_sql.setString(4, var4);
        ps_sql.setString(5, var5);
        ps_sql.setString(6, var6);
        ps_sql.executeUpdate();
        System.out.println("JAVA: Performed sql string " + sqlstatement + " with variables " + var1 +" " + var2 +" " + var3 +" " +var4 +" " +var5);
      }
      catch(SQLException e){
          System.out.println(e.getMessage());
      }		
          catch (Exception e){
        e.printStackTrace();
    }
      finally {
            if (ps_sql != null) {
                         try{
                        	ps_sql.close();
                            }
                           catch(SQLException e){
                           System.out.println(e.getMessage());
            }	
                         try{
                            if (SQLconnection != null) {
				SQLconnection.close();
                            }
            }
                            catch(SQLException e){
                           System.out.println(e.getMessage());
                            }	
            }
        }
}
    
        /*
         * Collects a list of members for a specific chatroom, splits it to regIds and adds into an arraylist.
         * It then returns the arraylist. With a string for the name of the chatRoom as inparameter.
         * */
         
        private static List<String> convertToArrayListForGCM(String chatRoom){
        List<String> chatroom_members = new ArrayList<>();
        Connection SQLconnection = null;
        PreparedStatement ps_sql = null;
        ResultSet result;
        String members;
      try{
        SQLconnection = connectSQLDB();
        
        ps_sql = SQLconnection.prepareStatement(getCurrentChatRoomUsers);
        ps_sql.setString(1, chatRoom);
        result = ps_sql.executeQuery();
        System.out.println("JAVA: Performed sql string " + getCurrentChatRoomUsers+ "with variables " + chatRoom);
        while (result.next()) {               // Position the cursor                   
        members = result.getString(1);        // Retrieve the first column value
        chatroom_members = new ArrayList<String>(Arrays.asList(members.split(",")));
        
        result.close();
        
}
      }
      catch(SQLException e){
          System.out.println(e.getMessage());
      }		
          catch (Exception e){
        e.printStackTrace();
    }
      finally {
            if (ps_sql != null) {
                         try{
                        	ps_sql.close();
                            }
                           catch(SQLException e){
                           System.out.println(e.getMessage());
            }	
                         try{
                            if (SQLconnection != null) {
				SQLconnection.close();
                            }
            }
                            catch(SQLException e){
                           System.out.println(e.getMessage());
                            }	
            }
        }
      return chatroom_members;
        }
        /*
         * Checks current members in a chatRoom and adds a new one to the list.
         */
        
        private String addUserToChatRoom(String regId, String chatRoom){
        List<String> chatroom_members = new ArrayList<>();
        Connection SQLconnection = null;
        PreparedStatement ps_sql = null;
        ResultSet result;
        String members;
        String updated_memberlist = "";
      try{
        SQLconnection = connectSQLDB();
        
        ps_sql = SQLconnection.prepareStatement(getCurrentChatRoomUsers);
        ps_sql.setString(1, chatRoom);
        result = ps_sql.executeQuery();
        System.out.println("JAVA: Performed sql string " + getCurrentChatRoomUsers+ "with variables " + chatRoom);
        while (result.next()) {               // Position the cursor                   
        members = result.getString(1);        // Retrieve the first column value
        result.close();
        ps_sql.close();
        /* ok now we have our info, append and update with new*/
        StringBuffer temp = new StringBuffer();
        if( members.equals("")){
            temp.append(regId);
            updated_memberlist = temp.toString();
            applyChangesToSQL(updated_memberlist, chatRoom, updateMemberListForChatRoomInDB);
        }else{
        temp.append(members);
        temp.append(",");
        temp.append(regId);
        updated_memberlist = temp.toString();
        applyChangesToSQL(updated_memberlist, chatRoom, updateMemberListForChatRoomInDB);
        }
}
      }
      catch(SQLException e){
          System.out.println(e.getMessage());
      }		
          catch (Exception e){
        e.printStackTrace();
    }
      finally {
            if (ps_sql != null) {
                         try{
                        	ps_sql.close();
                            }
                           catch(SQLException e){
                           System.out.println(e.getMessage());
            }	
                         try{
                            if (SQLconnection != null) {
				SQLconnection.close();
                            }
            }
                            catch(SQLException e){
                           System.out.println(e.getMessage());
                            }	
            }

        }return updated_memberlist;
        }


        /*
         * Connects to database and removes a users device regId from column MEMBERS.
         * Input parameters are what regId and which chatRoom.
         */
        private String removeUserFromChatRoom(String regId, String chatRoom){
        List<String> chatroom_members = new ArrayList<>();
        Connection SQLconnection = null;
        PreparedStatement ps_sql = null;
        ResultSet result;
        String members;
        String updated_memberlist = "";
      try{
        SQLconnection = connectSQLDB();
        
        ps_sql = SQLconnection.prepareStatement(getCurrentChatRoomUsers);
        ps_sql.setString(1, chatRoom);
        result = ps_sql.executeQuery();
        System.out.println("JAVA: Performed sql string " + getCurrentChatRoomUsers+ "with variables " + chatRoom);
        while (result.next()) {               // Position the cursor                   
        members = result.getString(1);        // Retrieve the first column value
        result.close();
        ps_sql.close();
        /* ok now we have our info, append and update with new*/
          String[] temp;
          StringBuffer sb= new StringBuffer();;
          temp = members.split(",");
          for(int i = 0; i<temp.length; i++){
            if(!temp[i].equals(regId)){
            sb.append(temp[i]);
            if(i != temp.length-2){
            sb.append(",");
                }
            }
        }
        updated_memberlist = sb.toString();
        applyChangesToSQL(updated_memberlist, chatRoom, updateMemberListForChatRoomInDB);
       
}
      }
      catch(SQLException e){
          System.out.println(e.getMessage());
      }		
          catch (Exception e){
        e.printStackTrace();
    }
      finally {
            if (ps_sql != null) {
                         try{
                        	ps_sql.close();
                            }
                           catch(SQLException e){
                           System.out.println(e.getMessage());
            }	
                         try{
                            if (SQLconnection != null) {
				SQLconnection.close();
                            }
            }
                            catch(SQLException e){
                           System.out.println(e.getMessage());
                            }	
            }

        }return updated_memberlist;
        }
}