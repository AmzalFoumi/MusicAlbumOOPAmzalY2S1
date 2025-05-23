<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<header>
		UserId: <%=session.getAttribute("userId") %>
		Username: <%=session.getAttribute("username") %>
		Usertype: <%=session.getAttribute("usertype") %>
	</header>
	
	<h2>Upload Music Feature</h2>


    <h2>Step 1: Music Details Upload</h2>

    <form action="musicUploadController" method="post" enctype="multipart/form-data">
        <label for="title">Music Title:</label><br>
        <input type="text" id="title" name="title"><br><br>

        <label for="genre">Genre:</label><br>
        <input type="text" id="genre" name="genre"><br><br>

        <label for="language">Language:</label><br>
        <input type="text" id="language" name="language"><br><br>

 		<label for="audioFile">Audio File:</label><br>
        <input type="file" id="audioFile" name="audioFile" accept=".mp3,.wav,.aac,.m4a"><br><br> 
        
        <input type="hidden" name="actionValue" value="upload">

        <input type="submit" value="submit">
    </form>
    
    <% 
    	String error = (String) request.getAttribute("errorMsg");
   		if (error != null) { 	
   	%>
   	<p style="color:red;"><%= error %></p>
	<% } %>
    

</body>
</html>