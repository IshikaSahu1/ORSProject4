
<%@page import="in.com.rays.ORSprojectt4.model.UserModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.com.rays.ORSprojectt4.servlet.UserListCtl"%>
<%@page import="in.com.rays.ORSprojectt4.utility.ServletUtility"%>
<%@page import="in.com.rays.ORSprojectt4.utility.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.com.rays.ORSprojectt4.servlet.ORSView"%>
<html>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<meta charset="ISO-8859-1">
<title>UserListView</title>
</head>
<body>
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.com.rays.ORSprojectt4.bean.UserBean"
		scope="request"></jsp:useBean>

	<center>
		<h1>User List</h1>
		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>
		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
			<%
				List loglist = (List) request.getAttribute("loglist");
			%>

			<table width="100%">
				<tr>
					<td align="center"><label>FirstName :</label> <input
						type="text" name="firstName" placeholder="Enter first name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						&emsp; <label>LoginId:</label><%=HTMLUtility.getList("login", String.valueOf(bean.getLogin()), loglist)%>&emsp;
						&emsp; <input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=UserListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" cellspacing="0" cellpadding="10">
				<tr>
					<th><input type="checkbox" name="select" id="select_all">Select
						All</th>
					<th>S. No.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Role</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Edit</th>
				</tr>


				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<UserBean> it = list.iterator();
					while (it.hasNext()) {
						bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>" <%if (bean.getRoleId() == 1) {%>
						disabled="disabled" <%}%>>
					<td><%=index++%>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getLogin()%></td>
					<%
						if (bean.getRoleId() == 1) {
					%><td>Admin</td>
					<%
						} else if (bean.getRoleId() == 2) {
					%><td>Student</td>
					<%
						} else if (bean.getRoleId() == 3) {
					%><td>College</td>
					<%
						} else if (bean.getRoleId() == 4) {
					%><td>Faculty</td>
					<%
						} else if (bean.getRoleId() == 5) {
					%><td>Kiosk</td>
					<%
						}
					%>
					<td><%=bean.getGender()%></td>
					<td><%=bean.getDob()%></td>
					<td><a href="UserCtl?id=<%=bean.getId()%>"
						<%if (bean.getRoleId() == 1) {%> onclick="return false" <%}%>>Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%><td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<%
						UserModel model = new UserModel();
						if (list.size() < pageSize || model.nextPK() - 1 == bean.getId()) {
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
		<br> <br> <br> <br> <br> <br> <br>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>