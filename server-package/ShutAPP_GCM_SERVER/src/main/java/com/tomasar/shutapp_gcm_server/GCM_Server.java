package com.tomasar.shutapp_gcm_server;

import com.google.android.gcm.server.*;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
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

/*
 * This is added server package to ShutAPP Android application.
 * Copyright (C) 2012  Hampus Lilja, Tomas Arnesson, Mathias Dosé, Mathias Herzog
 * This program uses GNU General Public License, please read supplied LICENCE.TXT for more info.
 */

@WebServlet(name = "GCM", urlPatterns = {"/GCM"})
//@WebServlet("GCM_Server")
public class GCM_Server extends HttpServlet {

    //This is the registered key for access via Google API, GCM.
    private static final String SENDER_ID = "AIzaSyAbgoOa_EvIBk81TxI8oTm8d_0he1aqzWU";
    private static final String SENDER_ID2_browser = "AIzaSyAGbXeI9lkNaqDp_oFove5dmUNqEsD5FOA";
    
    //These are static configurations, for testing purposes.
    private static final String TOMAS = "APA91bEqKVrHg19T-I5MKgp707wMhcgAdmzOfr0IPAsTyk5O0citkgjl4rHdYcz7axKbA9ODwDFPb4I2ISwwdZntheJcLdoeHJCkESs7F145PKBmU92bALDPg_ClwS82pO3UnLVGCvmAyWl15B9Qpk9jjQd-Fwa_5Q";
    private static final String DOSE = "APA91bHCMst1WyecOE-T6IyrLlwa2E-9mwRYVE3T1KHrdcFNMBlehvxmHHPWNGPL0alH02cfOxuj1Qvk4rc03k3Zd0Khp33d4dREYUSXUGQ_nO1Vdkhg03Qg4j4jsojmSk89Ym4AlsCMuglHHU47TuX4SbJNYSONFg";     
    private static final String ROOM1 = "chatroom001";
    private static final String ROOM2 = "chatroom002";
    /*
     * Static SQL part/strings below this line.
     */
    //MYSQL part containing vital connect information.
    //private static String DBURL = "jdbc:derby://localhost:1527/SHUTAPPDB";
    private static String DBURL = "jdbc:sqlite:C:\\Tomas\\Software Engineering\\ShutAPP_GCM_SERVER\\target\\ShutAPP_GCM_SERVER-1.0-SNAPSHOT\\shutappdb";
    private static String DBUSER = "gcm";
    private static String DBPASS = "gcm";
    //private static String DBDriver = "org.apache.derby.jdbc.ClientDriver";
    private static String DBDriver = "org.sqlite.JDBC";
    //MYSQL prepared statements
    //When a new users is registered, put hes/hers chose nickname in DB
    private static String newUserToSQLDB = "INSERT INTO USERS" + "(REGID, NICK) VALUES" + "(?,?)";
    //When a new chatroom is created, put the details into DB
    private static String createNewChatRoomInDB = "INSERT INTO CHATROOMS " + "(NAME, MEMBERS, LAT, LONG, RADIUS) VALUES " + "(?,?,?,?,?)";
    private static String addMemberToChatRoomInDB = "UPDATE CHATROOMS SET MEMBERS=" + "?" + "  WHERE NAME=" + "?";
    //When a user uninstall application, remove the REGID from GCM.USERS
    private static String delUserFromSQLDB = "DELETE FROM USERS WHERE REGID" + "=" + "(?)";
    //Checks which users are members or chatroom
    private static String getCurrentChatRoomUsers = "SELECT MEMBERS FROM CHATROOMS WHERE NAME="+"?";
    
    
    //List of current users
    private List<String> usertest = new ArrayList<String>();
    //String singleuser = TOMAS;
    //List of current chatrooms
    //private List<String> chatrooms = new ArrayList<String>();

    
    
    public GCM_Server() {
         
        super();
        
        usertest.add(DOSE);
        usertest.add(TOMAS);
        

    }    
     /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     * 
     * Not implemented, currently only POST is being used.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
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
        //processRequest(request, response);
        String typeOfAction = request.getParameter("action");
        //response.setContentType("text/html;charset=UTF-8");
        //response.setContentType("application/json");
            if( typeOfAction.equals("chatRoomMsg")) {
        String chatRoom = request.getParameter("chatRoom");
        String userMessage = request.getParameter("Message");
        String userName = request.getParameter("user");
        sendMsgToChatRoom(buildMsg(chatRoom, userMessage, userName), getMembersFromChatRoom(chatRoom));
        Logger.getAnonymousLogger().log(Level.INFO, "Recevied HTTP POST to send msg to all in chatroom: " +chatRoom);
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
           } else if( typeOfAction.equals("createNewChatRoom")) {
        /*
         * This ACTION handles creation of a new chatroom.
         */ 
        String name = request.getParameter("NAME");
        String members = request.getParameter("MEMBERS");
        String lat = request.getParameter("LAT");
        String longitud = request.getParameter("LONG");
        String radius = request.getParameter("RADIUS");
        //applyChangesToSQL("roomtest", usertest, lat, longitud, radius, createNewChatRoomInDB);
        applyChangesToSQL(name, members, lat, longitud, radius, createNewChatRoomInDB);
        Logger.getAnonymousLogger().log(Level.INFO, "Recieved HTTP POST for creation of new chatroom.");
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
       }else if( typeOfAction.equals("remUserChatRoom")) {
        /*
         * This ACTION handles the removal of a user from a chatroom.
         */  
        String chatRoom = request.getParameter("CHATROOM");
        String regId = request.getParameter("REGID");
        //removeUserFromChatRoom(DBREGID, delUserFromSQLDB);
        Logger.getAnonymousLogger().log(Level.INFO, "Recieved HTTP POST for new user to join chatroom: " + chatRoom);
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
        }else if( typeOfAction.equals("joinChatRoom")) {
        /*
         * This ACTION handles adding a new member to a chatroom(join).
         */  
        String chatRoom = request.getParameter("CHATROOM");
        String regId = request.getParameter("REGID");
        applyChangesToSQL(addUserToChatRoom(regId, chatRoom), chatRoom, addMemberToChatRoomInDB);
        
        Logger.getAnonymousLogger().log(Level.INFO, "Recieved HTTP POST for new user to join chatroom: " + chatRoom);
        //line below is only for test environment.
        response.sendRedirect("index.jspx");
        }

}

    /**
     * Returns a short description of the servlet.
     *
     */
    @Override
    public String getServletInfo() {
        return "ShutApp GCM Web Servlet";
    }
    
   
    
    /*
     * This class is used to build a complete message string in correct format, before being sent.
     */
    public Message buildMsg(String chatRoom, String userMessage, String userName){
        
        //Appending username to sent message, to chatlog shows who sent what.
        StringBuffer temp = new StringBuffer();
        temp.append(userName);
        temp.append(":");
        temp.append(userMessage);        
        // This is used for building message!
        // Could be String or JSON Object!
        Message message;
        message = new Message.Builder()

// If multiple messages are sent using the same .collapseKey()
// the android target device, if it was offline during earlier message
// transmissions, will only receive the latest message for that key when
// it goes back on-line.
//.collapseKey(chatRoom)
//.timeToLive(30)
//.delayWhileIdle(true)
.addData("chatroom", chatRoom)
.addData("message", temp.toString())
.build();
        
        return message;
        
    }
    
    /*
     * The following function is responsible for sending a built message to GCM server
     * imparmeters is a formatted and built message, by com.google.android.gcm.server.message; class
     */
    public void sendMsgToChatRoom(Message message, List<String> users){
                        // Instance of com.android.gcm.server.Sender, that does the
                // transmission of a Message to the Google Cloud Messaging service.
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
    private void applyChangesToSQL(String var1, String var2, String var3, String var4, String var5, String sqlstatement){
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
        private static List<String> getMembersFromChatRoom(String chatRoom){
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
        chatroom_members.add(members);
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
        private static String addUserToChatRoom(String regId, String chatRoom){
            List<String> chatroom_members = getMembersFromChatRoom(chatRoom);
            chatroom_members.add(regId);
            String updated_regid_list = "crap";
            
            return updated_regid_list;
        }
        
}

