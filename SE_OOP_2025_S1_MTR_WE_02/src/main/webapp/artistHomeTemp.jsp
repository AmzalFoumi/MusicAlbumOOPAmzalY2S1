<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.amzal.musicmanager.model.PublishedMusic" %>
    <%@ page import="com.amzal.musicmanager.dao.MusicDAO" %>
    <%@ page import="java.util.ArrayList"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Artist Home</title>
	<style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #999;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        form {
            display: inline;
        }
    </style>
</head>
<body>
	<header>
		UserId: <%=session.getAttribute("userId") %>
		Username: <%=session.getAttribute("username") %>
		Usertype: <%=session.getAttribute("usertype") %>
	</header>
	
	<div>
    <%
        int artistId = 0;
        try {
            artistId = Integer.parseInt(session.getAttribute("userId").toString());
        } catch (Exception e) {
            out.println("<p>Error reading artist ID from session.</p>");
        }

        if (artistId > 0) {
            MusicDAO dao = MusicDAO.getMusicDAO();
            ArrayList<PublishedMusic> musicList = dao.getAllMusicListByArtist(artistId);

            if (!musicList.isEmpty()) {
     %>
                  <table>
                      <thead>
                          <tr>
                              <th>Title</th>
                              <th>Genre</th>
                              <th>Language</th>
                              <th>Album</th>
                              <th>Region</th>
                              <th>Price</th>
                              <th>Release Date</th>
                              <th>Published</th>
                              <th>Actions</th>
                          </tr>
                      </thead>
                      <tbody>
                      <%
                          for (PublishedMusic music : musicList) {
                      %>
                          <tr>
                              <td><%= music.getTitle() %></td>
                              <td><%= music.getGenre() %></td>
                              <td><%= music.getLanguage() %></td>
                              <td><%= music.getAlbumName() %></td>
                              <td><%= music.getRegion() %></td>
                              <td>$<%= music.getPrice() %></td>
                              <td><%= music.getReleaseDate() %></td>
                              <td><%= music.isPublished() ? "Yes" : "No" %></td>
                              <td>
                              	<%if (music.isPublished()) { %>
                                  <!-- Preview Button if music is published -->
                                  <form method="get" action="musicPreviewController">
                                      <input type="hidden" name="musicId" value="<%= music.getId() %>">
                                      <input type="hidden" name="title" value="<%= music.getTitle() %>">
                                      <input type="hidden" name="actionValue" value="publishedMusic">
                                      <input type="hidden" name="isPublished" value="true">
                                      <input type="submit" value="Preview">
                                  </form>

                                  <% }
                              		else if (!music.isPublished()) { %>
                                  <!-- Publish Button -->
                                  <form method="get" action="musicPreviewController">
                                      <input type="hidden" name="musicId" value="<%= music.getId() %>">
                                      <input type="hidden" name="title" value="<%= music.getTitle() %>">
                                      <input type="hidden" name="actionValue" value="publish">
                                      <input type="hidden" name="isPublished" value="false">
                                      <input type="submit" value="Publish">
                                  </form>
                                  
                                  <form method="get" action="musicPreviewController">
                                      <input type="hidden" name="musicId" value="<%= music.getId() %>">
                                      <input type="hidden" name="title" value="<%= music.getTitle() %>">
                                      <input type="hidden" name="actionValue" value="upload">
                                      <input type="hidden" name="isPublished" value="false">
                                      <input type="submit" value="Preview">
                                  </form>
                                  
                                  <% } %>
                              </td>
                          </tr>
                      <%
                          }
                      %>
                      </tbody>
                  </table>
                <%
                        } else {
                            out.println("<p>No music uploaded by this artist yet.</p>");
                        }
                    } else {
                        out.println("<p>No artist ID found in session.</p>");
                    }
                %>
</div>
	

</body>
</html>