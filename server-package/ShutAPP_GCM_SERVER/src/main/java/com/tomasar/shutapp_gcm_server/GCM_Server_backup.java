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

@WebServlet(name = "GCM2", urlPatterns = {"/GCM2"})
//@WebServlet("GCM_Server")
public class GCM_Server_backup extends HttpServlet {

    private static final String SENDER_ID = "AIzaSyAbgoOa_EvIBk81TxI8oTm8d_0he1aqzWU";
    private static final String SENDER_ID2_browser = "AIzaSyAGbXeI9lkNaqDp_oFove5dmUNqEsD5FOA";
    private static final String TOMAS = "APA91bEqKVrHg19T-I5MKgp707wMhcgAdmzOfr0IPAsTyk5O0citkgjl4rHdYcz7axKbA9ODwDFPb4I2ISwwdZntheJcLdoeHJCkESs7F145PKBmU92bALDPg_ClwS82pO3UnLVGCvmAyWl15B9Qpk9jjQd-Fwa_5Q";
    private static final String DOSE = "APA91bHCMst1WyecOE-T6IyrLlwa2E-9mwRYVE3T1KHrdcFNMBlehvxmHHPWNGPL0alH02cfOxuj1Qvk4rc03k3Zd0Khp33d4dREYUSXUGQ_nO1Vdkhg03Qg4j4jsojmSk89Ym4AlsCMuglHHU47TuX4SbJNYSONFg";     
    private static final String ROOM1 = "chatroom001";
    private static final String ROOM2 = "chatroom002";
    //MYSQL part
    private static String DBURL = "jdbc:derby://localhost:1527/SHUTAPPDB";
    private static String DBUSER = "gcm";
    private static String DBPASS = "gcm";
    private static String DBDriver = "org.apache.derby.jdbc.ClientDriver";
    //List of current users
    private List<String> users = new ArrayList<String>();
    //String singleuser = TOMAS;
    //List of current chatrooms
    private List<String> chatrooms = new ArrayList<String>();

    
    
    public GCM_Server_backup() {
         
        super();
        //Adding hardcoded targets
        users.add(DOSE);
        users.add(TOMAS);
        List<String> chatrooms = new ArrayList<String>();
        chatrooms.add(ROOM1);
        chatrooms.add(ROOM2);
    }    
     /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
        
        sendMsgToChatRoom(chatRoom, userMessage, userName);
        Logger.getAnonymousLogger().log(Level.INFO, "msg_sent to all in chatroom");
        //response.sendRedirect("index.jspx");
        
           } else if( typeOfAction.equals("addUserToDB")) {
        // We'll collect the "chatRoom" and "Message" values from our JSP page
        String DBNICK = request.getParameter("DBNICK");
        String DBREGID = request.getParameter("DBREGID");
        
        registerUserToDB(DBNICK, DBREGID);
        Logger.getAnonymousLogger().log(Level.INFO, "got POST for new user to DB");
        response.sendRedirect("index.jspx");
        
                        }
             else if( typeOfAction.equals("remUserFromDB")) {
        // We'll collect the "chatRoom" and "Message" values from our JSP page
        String DBREGID = request.getParameter("DBREGID");
        
        removeUserFromDB(DBREGID);
        Logger.getAnonymousLogger().log(Level.INFO, "got POST for new user to DB");
        response.sendRedirect("index.jspx");
             }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    public Message constructMsg(String chatRoom, String userMessage, String userName){
                // Instance of com.android.gcm.server.Sender, that does the
        // transmission of a Message to the Google Cloud Messaging service.
        Sender sender = new Sender(SENDER_ID);
        
        StringBuffer temp = new StringBuffer();
        temp.append(userName);
        temp.append(":");
        temp.append(userMessage);        
        // This is used for building message!
        // Could be String or JSON Object!
        Message message = new Message.Builder()

        // If multiple messages are sent using the same .collapseKey()
        // the android target device, if it was offline during earlier message
        // transmissions, will only receive the latest message for that key when
        // it goes back on-line.
        .collapseKey(chatRoom)
        .timeToLive(30)
        .delayWhileIdle(true)
        .addData("message", temp.toString())
        .build();
        
        return message;
        
    }
    
    
    public void sendMsgToChatRoom(String chatRoom, String userMessage, String userName){
        // Instance of com.android.gcm.server.Sender, that does the
        // transmission of a Message to the Google Cloud Messaging service.
        Sender sender = new Sender(SENDER_ID);
        
        StringBuffer temp = new StringBuffer();
        temp.append(userName);
        temp.append(":");
        temp.append(userMessage);        
        // This is used for building message!
        // Could be String or JSON Object!
        Message message = new Message.Builder()

        // If multiple messages are sent using the same .collapseKey()
        // the android target device, if it was offline during earlier message
        // transmissions, will only receive the latest message for that key when
        // it goes back on-line.
        .collapseKey(chatRoom)
        .timeToLive(30)
        .delayWhileIdle(true)
        .addData("message", temp.toString())
        .build();

        // Oki message built, try to send it
        try {
            MulticastResult result = sender.send(message, users, 1);
            Logger.getAnonymousLogger().log(Level.INFO, "msg_sent to " + users);
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
    
    public void registerUserToDB(String DBNICK, String DBREGID){
             System.out.println("connecting to db");
             Connection con = null;
    try{
        Class.forName(DBDriver);
        con = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
    try{
        Statement st = con.createStatement();
        int val = st.executeUpdate("INSERT INTO USERS (REGID, NICK) VALUES ("+"'" +DBREGID +"', "+"'" +DBNICK +"')");

    System.out.println("Deleted user from " +DBREGID);
    }
    catch (SQLException s){
        System.out.println("SQL statement is not executed!");
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }
}
    
    public void removeUserFromDB(String DBREGID){
             System.out.println("connecting to db");
             Connection con = null;
    try{
        Class.forName(DBDriver);
        con = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
    try{
        Statement st = con.createStatement();
              int val = st.executeUpdate("DELETE FROM USERS WHERE REGID = "+"'" +DBREGID +"'");
    System.out.println("Inserted " +DBUSER);
    }
    catch (SQLException s){
        System.out.println("SQL statement is not executed!");
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }
}
    

} 
