<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Message</title>
</head>
<body>
<h1>Deleted The Ad Successfully</h1>
<h5>The user who created the ad is as follows</h5>
<label>Created By:</label><p>${user.name}</p>
<h5>Please Go Back to the Member Dashboard homepage </h5>
<!-- <button type="button" name="back" onclick="history.back()">back</button> -->
<a href="#" onclick="location.href = document.referrer; return false;">Back</a>
</body>
</html>