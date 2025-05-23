<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Landing Page</title>
</head>
<body>
    <h2>Enter Session Information</h2>
    
    <!-- Display error message if set -->
    <%
        String errorMsg = (String) request.getAttribute("errorMsg");
        if (errorMsg != null) {
    %>
        <p style="color: red;"><%= errorMsg %></p>
    <%
        }
    %>
    
    <form method="post" action="setSession">
    
    	<label for="userId">User Id:</label>
        <input type="number" id="userId" name="userId" ><br><br>
    	
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" ><br><br>

        <label for="usertype">User Type:</label>
        <select id="usertype" name="usertype" >
            <option value="artist">artist</option>
            <option value="listener">listner</option>
        </select><br><br>

        <button type="submit">Proceed to Upload</button>
    </form>
    
    
</body>
</html>