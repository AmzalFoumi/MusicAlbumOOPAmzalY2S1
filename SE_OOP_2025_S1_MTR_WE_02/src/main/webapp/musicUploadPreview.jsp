
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.amzal.musicmanager.model.Music" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Music Preview</title>
</head>
<body>

	<header>
		UserId: <%=session.getAttribute("userId") %>
		Username: <%=session.getAttribute("username") %>
		Usertype: <%=session.getAttribute("usertype") %>
	</header>

	<%
	    // Get the Music object from the request
	    Music music = (Music) request.getAttribute("music");
	
	    // If music is available (loaded from DB)
	    if (music != null) {
	%>
    <h2>Preview of your Music</h2>

    <!-- Display music details -->
    <p><strong>Title:</strong> <%= music.getTitle() %></p>
    <p><strong>Genre:</strong> <%= music.getGenre() %></p>
    <p><strong>Language:</strong> <%= music.getLanguage() %></p>
    
    <p>Audio path: <%= request.getContextPath() + "/" + music.getFilePath() %></p>
    
    <!-- Audio player -->
    <audio controls>
        <source src="<%= request.getContextPath() + "/" + music.getFilePath() %>" type="audio/mp4">
        <source src="<%= request.getContextPath() + "/" + music.getFilePath() %>" type="audio/mpeg">
        <source src="<%= request.getContextPath() + "/" + music.getFilePath() %>" type="audio/wav">
    </audio>

    <!-- Button to go back to dashboard -->
    <form action="test.jsp" method="get" style="display:inline;">
        <button type="submit">Back to Dashboard</button>
    </form>

    <!-- Button to proceed to publish form, passing musicId -->
    <form action="musicPreviewController" method="get" style="display:inline;">
        <input type="hidden" name="musicId" value="<%= music.getId() %>">
        <input type="hidden" name="actionValue" value="publish">
        <input type="hidden" name="isPublished" value="false">
        <button type="submit">Publish Now</button>
    </form>
    
    <form action="musicPreviewController" method="get" style="display:inline;">
        <input type="hidden" name="musicId" value="<%= music.getId() %>">
        <input type="hidden" name="actionValue" value="delete">
        <input type="hidden" name="isPublished" value="false">
        <button type="submit">Delete Music</button>
    </form>
	
	<%
	    } else {
	%>
	    <!-- Error message if no music found -->
	    <p style="color:red;">Music details not found.</p>
	<%
	    }
	%>

</body>
</html>
