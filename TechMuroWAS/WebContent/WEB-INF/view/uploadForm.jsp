<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/jquery.form.js"></script>
<title>Being Java Guys | Hello World</title>

<script type="text/javascript">

$(document).ready(function() {
	
					$('#uploadForm').ajaxForm("", function(data) {
		                $('#result').html(data);
		                
		                alert(data);
		            });
		            });

</script>
</head>
<body>
		<form:form id="uploadForm" action="fileUpload.htm" method="POST" enctype="multipart/form-data"
		modelAttribute="uploadedFile">
                <input type="hidden" name="MAX_FILE_SIZE" value="100000" >
                File: <input type="file" name="file">
                
                <input type="submit" value="Submit 1" name="uploadSubmitter1">
            </form:form>
			
			<div id="result"></div>
	
</body>
</html>
