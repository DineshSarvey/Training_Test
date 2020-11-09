<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New User Registration</title>
</head>
<body>
	<!-- read user name and password from user to create account
	     and send to usercontrollers registeruser method
	-->
    
<jsp:include page="header.jsp"/>
<title>User Registration Form</title>
	<h2>Enter Details for Registration</h2>
	<hr/>


        <form action="user?action=registernewuser" method="post">
            <div>
			        <label for="loanName">Select Loan Name:</label>
                                             <select name="LoanName" id="LoanName">
                                             <option value="Select Option">Select</option>
                                                <option value="LoanType">Mortagage Loan</option>
                                             </select>
                                              <br><br>
			</div>		
			<div>
					<div><label for="applno">Loan Application Number</label> </div>
					<div><input type="text" id="applno" name="applno" value="<%=request.getAttribute("applno") %>"  readonly> </div>
			</div>
            <div>
				<div><label for="amtrequest">Amount Requested</label> </div>
				<div><input type="text" id="amtrequest" name="amtrequest"> </div>
			</div>
             <div>
					<div><label for="doa">Loan Application Date</label> </div>
					<div><input type="date" id="doa" name="doa" readonly> </div>
			 </div>
              <div>
					<label for="bstructure">Select Business Structure:</label>
                                             <select name="bstructure" id="bstructure">
                                             <option value="Select Option">Select</option>
                                                <option value="Individual">Individual</option>
                                                <option value="Organization">Organization</option>
                                             </select>
                 	
			</div>

			<div>
					<label for="bindicator">Select Business Indicator:</label>
                                             <select name="bindicator" id="bindicator">
                                             <option value="Select Option">Select</option>
                                                <option value="Salaried">Salaried</option>
                                                <option value="Business">Business</option>
                                             </select>
                                              <br><br>
					
			</div>
			<div>
					<label for="tPayer">Tax Payer:</label>
                                             <select name="taxpayer" id="taxpayer">
                                             <option value="Select Option">Select</option>
                                                <option value="Yes">Y</option>
                                                <option value="No">N</option>
                                             </select>
                                              <br><br>
					
			</div>
				<div>
					<div><label for="address">Address</label> </div>
					<div><input type="text" id="address" name="address"> </div>
				</div>
				<div>
					<div><label for="email">emailId</label> </div>
					<div><input type="email" id="email" name="email"> </div>
				</div>
				<div>
					<div><label for="mobile">Mobile number</label> </div>
					<div><input type="text" id="mobile" name="mobile"> </div>
				</div>
				<div>
					<div><input type="submit" value="SignUp" onclick="success()"> </div>
				</div>


		<div class="form-group">
                	<a href="index.jsp">Existing User</a>
                </div>

<jsp:include page="footer.jsp"/>
<script>
document.getElementById('doa').value = new Date().toISOString().substring(0, 10);
	function success() {
	  alert("Application submitted successfully!");
	}
</script>
</body>
</html>