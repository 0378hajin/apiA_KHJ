<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "java.sql.*" %>
<%
//접속 정보 등록

String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
String user = "system";
String password = "1234";
//해당 패키지에 있는 클래스를 가져온다.
Class.forName("oracle.jdbc.driver.OracleDriver");
//접속 정보를 활용해서 연결 객체를 만든다.
Connection conn = DriverManager.getConnection(url, user, password);
%>