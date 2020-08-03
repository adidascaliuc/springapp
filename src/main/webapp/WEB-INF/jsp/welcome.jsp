<!DOCTYPE html >
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="sat, 01 Dec 2001 00:00:00 GMT">
<title>First Spring App</title>
<link href="/css/main.css" rel="stylesheet">
</head>
<body>
	<div role="navigation">
		<div class="topnav">
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="/" class="navbar-brand">Home</a></li>
					<li><a href="/table-accounts">All Users</a></li>
					<li><a href="/table-subscriptions">Subscription</a></li>
					<li><a href="/table-extra_charges">Extra Charges</a></li>

				</ul>
			</div>
		</div>
	</div>

	<p class="ptext">This is my first Spring WEB app.</p]
	>

	<c:choose>

		<c:when test="${mode=='ALL_CLIENTS' }">
			<div class="container text-center" id="tasksDiv">
				<h3>All Clients</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Address</th>
								<th>Phone</th>
								<th>Balance</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="client" items="${clients}">
								<tr>
									<td>${client.getID() }</td>
									<td>${client.getName() }</td>
									<td>${client.getAddress() }</td>
									<td>${client.getPhone() }</td>
									<td>${client.getBalance() }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>

		<c:when test="${mode=='ALL_SUBSCRIPTIONS' }">
			<div class="container text-center" id="tasksDiv">
				<h3>All Subscriptions</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Id</th>
								<th>Client Name</th>
								<th>Minutes Included</th>
								<th>Monthly Cost</th>
								<th>Network Minutes Included</th>
								<th>Network SMS Included</th>
								<th>SMS Included</th>
								<th>Traffic Included</th>
								<th>Subscription Type</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="sub" items="${subscriptions}">
								<tr>
									<td>${sub.getID() }</td>
									<td>${sub.getClientName() }</td>
									<td>${sub.getMinutesIncluded() }</td>
									<td>${sub.getMonthlyCost() }</td>
									<td>${sub.getNetworkMinutesIncluded() }</td>
									<td>${sub.getNetworkSMSIncluded() }</td>
									<td>${sub.getSMSIncluded() }</td>
									<td>${sub.getTrafficIncluded() }</td>
									<td><select name="subType" id="subType">
											<c:choose>
												<c:when test="${sub.getSubscriptionType()=='Standard'}">
													<option value="standard">Standard</option>
													<option value="premium">Premium</option>
												</c:when>
												<c:otherwise>
													<option value="premium">Premium</option>
													<option value="standard">Standard</option>
												</c:otherwise>
											</c:choose>

									</select></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>

		<c:when test="${mode=='ALL_EXTRACHARGESES' }">
			<div class="container text-center" id="tasksDiv">
				<h3>All Extra Chargeses</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Id</th>
								<th>Client Name</th>
								<th>Call</th>
								<th>SMS</th>
								<th>Network Call</th>
								<th>Network SMS</th>
								<th>Internet Traffic</th>
								<th>Extra Charges Type</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="extra" items="${extra_chargeses}">
								<tr>
									<td>${extra.getID() }</td>
									<td>${extra.getClientName() }</td>
									<td>${extra.getCall() }</td>
									<td>${extra.getSMS() }</td>
									<td>${extra.getNetworkCall() }</td>
									<td>${extra.getNetworkSMS() }</td>
									<td>${extra.getInternetTraffic() }</td>
									<td><select name="subType" id="subType">
											<c:choose>
												<c:when test="${extra.getExtra_ChargesType()=='Standard'}">
													<option value="standard">Standard</option>
													<option value="premium">Premium</option>
												</c:when>
												<c:otherwise>
													<option value="premium">Premium</option>
													<option value="standard">Standard</option>
												</c:otherwise>
											</c:choose>

									</select></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
	</c:choose>
</body>
</html>