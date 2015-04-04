function getAirlineData(){
	var source = $("#source").val();
	var destination = $("#destination").val();
	var date = $("#date").val();
	var adultCount = $("#adultCount").val();
	var req = {
			reqType : "airportPair",
			source: source,
			destination: destination,
	};

	$.ajax({
		url:"IternaryServlet",
		data: req,
		type: "POST",
		success : function(results){
			var codes = JSON.parse(results);
			var sCodes = codes.sourceCodes;
			var dCodes = codes.destinationCodes;

			for(var i=0;i< sCodes.length ;i++){
				for(var k=0;k < dCodes.length;k++){
					console.log("Before==> "+sCodes[i]+","+dCodes[k]);
					req = {
							reqType : "airportData",
							source: sCodes[i],
							destination: dCodes[k],
							date: date,
							adultCount: adultCount,
					};
					$.ajax({
						url:"IternaryServlet",
						data: req,
						type: "GET",
						success: function(flightData){
							var flightArray = JSON.parse(flightData); 	
							var trHTML = '';

							$.each(flightArray, function (i, item) {
								var count =0;
								var tFlightLen = item.transitFlights.length;
								console.log(item.transitFlights);
								$.each(item.transitFlights, function (i, transitFlight){
									trHTML +='<tr>';
									if (count ==0)
										trHTML +='<td rowspan="'+tFlightLen +'">' + item.saleTotal +'</td>';
									trHTML += '<td id="carrier">' + transitFlight.carrier + '</td><td>' + transitFlight.aircraft +
									'</td><td>' + transitFlight.arrivalTime + '</td><td>' + transitFlight.departureTime + '</td><td>'+
									transitFlight.origin + '</td><td>' + transitFlight.destination + '</td><td>' + transitFlight.flightNumber+'</td>';
									trHTML += '<td>'+ transitFlight.duration + '</td>';
									trHTML += '<td>'+ '<button onclick="makeBooking(this)">Book</button>'+ '</td>';
									count++;
									trHTML +='</tr>';


								});
								
							});
							$("#airline_table").append(trHTML);
						}
					});

				}
			}
//			console.log(results["sourceCodes"]);
		}

	});

	return false;
}

