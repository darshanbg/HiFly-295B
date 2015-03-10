<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>HiFly - Flight Recommendation</title>

<%@ page import="com.airline.dto.FlightDisplay"%>
<%@ page import="java.util.ArrayList"%>

<!-- Bootstrap core CSS -->
<link href="../../dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="cover.css" rel="stylesheet">


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("#moreOptionsDiv").hide();
		$("#advanceOptionDiv").hide();
		$("#moreOptionCheckBox").prop( "checked", false);
		$("#advanceOptionCheckBox").prop( "checked", false);
		
		$("#moreOptionCheckBox").click(function() {
			$("#moreOptionsDiv").animate({
				height : 'toggle'
			});
		});

		$("#advanceOptionCheckBox").click(function() {
			$("#advanceOptionDiv").animate({
				height : 'toggle'
			});
		});
	});
</script>

<style type="text/css">
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
	overflow: auto;
	height: 400px;
}

#customers td,#customers th {
	font-size: 1em;
	border: 1px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

#customers th {
	font-size: 1.1em;
	text-align: left;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #A7C942;
	color: #ffffff;
}

#customers tr.alt td {
	color: #000000;
	background-color: #EAF2D3;
}

</style>
</head>

<body>

	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<div class="masthead clearfix">
					<div class="inner">
						<h2 class="masthead-brand">HiFly.com</h2>
						<ul class="nav masthead-nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="#">Features</a></li>
							<li><a href="#">Contact</a></li>
						</ul>
					</div>
				</div>

				<div class="inner cover">
					<%
						ArrayList<FlightDisplay> result = (ArrayList<FlightDisplay>) request
								.getAttribute("displayList");
					%>

				<form name="myForm" action="IternaryServlet" method="POST">
					<p>
						<input placeholder="Source" required autofocus style="text-transform:uppercase"> <input
							placeholder="Destination" required style="text-transform:uppercase" > <input
							type="date" name="date" required >
					</p>

					<p>
						Adult Passengers:<input type="number" name="adultCount" min="1"
							max="5">
						<button id="hide">Search</button>
					</p>

					<p>
						<input id="moreOptionCheckBox" type="checkbox" name="vehicle"
							value="Bike"> More Options
					</p>

					<div id="moreOptionsDiv" class="inner cover">
						<table id="customers">
							<tr>
								<td>Infant (Lap):</td>
								<td><input type="number" name="infantInLapCount" min="1"
									max="5"></td>
							</tr>

							<tr>
								<td>Infant(set):</td>
								<td><input type="number" name="infantInSeatCount" min="1"
									max="5"></td>
							</tr>

							<tr>
								<td>Senior:</td>
								<td><input type="number" name="seniorCount" min="1" max="5">
								</td>
							</tr>

							<tr>
								<td>Children:</td>
								<td><input type="number" name="childCount" min="1" max="5">
								</td>
							</tr>

						</table>
					</div>
					
				</form>
					

					<p>
						<input id="advanceOptionCheckBox" type="checkbox" name="advance">
						Advance Options
					</p>

					<div id="advanceOptionDiv">
						<table id="customers">
							<tr>
								<td>Max Price</td>
								<td><input placeholder="Ex: 2000" required autofocus>
								</td>
							</tr>

							<tr>
								<td>Earliest Departure Time</td>
								<td><input placeholder="HH:MM" required autofocus>
								</td>
							</tr>

							<tr>
								<td>Latest Departure Time</td>
								<td><input placeholder="HH:MM" required autofocus>
								</td>
							</tr>
							
							<tr>
								<td>Permitted Carriers</td>
								<td><input placeholder="XX, YY, .." required autofocus>
								</td>
							</tr>

							<tr>
								<td>Max Connection Time (In Min)</td>
								<td><input placeholder="240 min" required autofocus>
								</td>
							</tr>
							
							<tr>
								<td>Prohibited Carriers</td>
								<td><input placeholder="XX, YY, .." required autofocus>
								</td>
							</tr>

							<tr>
								<td>Preferred Cabin</td>
								<td><select>
										<option value="None">None</option>
										<option value="Coach">Coach</option>
										<option value="Premium Coach">Premium Coach</option>
										<option value="Business">Business</option>
										<option value="First Class">First Class</option>
								</select></td>
							</tr>
							
							<tr>
								<td>Refundable? <input type="checkbox" id="refundableCheckBox" name="refundable"></td>
								<td>Non Stop? <input type="checkbox" name="nonStop"></td>
							</tr>
							

						</table>

					</div>
					<br> <br>


					<%
						if (result != null) {
							%>
						
					<br> <br>
					<div id="customers">
						<table>
							<tr>
								<th>Flight Number</th>
								<th>Arrival Time</th>
								<th>Departure Time</th>
							</tr>	
					<%		
							for (FlightDisplay display : result) {
					%>
							<tr>
								<td><%=display.getAircraft()%></td>
								<td><%=display.getArrivalTime()%></td>
								<td><%=display.getDepartureTime()%></td>
							</tr>

					<%
						}
					}
					%>
											</table>
					</div>
					

				</div>

				<div class="mastfoot">
					<div class="inner">
						<p align="center">
							HiFly - Flight Recommendation System 
						</p>
					</div>
				</div>

			</div>

		</div>

	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="../../dist/js/bootstrap.min.js"></script>
	<script src="../../assets/js/docs.min.js"></script>
</body>
</html>
