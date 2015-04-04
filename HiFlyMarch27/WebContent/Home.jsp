<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<html>
<head>
<title>Travelo a Travel Category Flat Bootstarp Responsive Web
	Template| Home :: w3layouts</title>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Travelo  Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--webfont-->
<link
	href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Raleway:400,800,300,100,500,700,600,900'
	rel='stylesheet' type='text/css'>
<!--animated-css-->
<link href="css/animate.css" rel="stylesheet" type="text/css"
	media="all">
<script src="js/wow.min.js"></script>
<script>
 new WOW().init();
</script>
<!--/animated-css-->
<!--script-->
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
<!--/script-->
<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},900);
				});
			});
</script>
</head>
<body>
	<!---->
	<div class="banner-bg">
		<video autoplay="autoplay" muted="muted" poster="wall_icons/my.jpg"
			width="100%" loop>
			<source src="srix.mp4" type="video/mp4" type="video/mp4">
		</video>
		<div class="banner">
			<div class="header">
				<div class="logo wow fadeInLeft" data-wow-delay="0.5s">
					<a href="index.html"><img src="images/logo2.png" alt="" /></a>
				</div>
				<div class="top-menu">
					<span class="menu"></span>
					<ul class="cl-effect-1">
						<li><a class="scroll" href="#home">HOME</a></li>
						<li><a class="scroll" href="#brief">BRIEF</a></li>
						<li><a class="scroll" href="#features">FEATURES</a></li>
						<li><a class="scroll" href="#screenshots">TOURS</a></li>
						<li><a class="scroll" href="#testimonial">TESTIMONIAL</a></li>
						<li><a class="scroll" href="#contact">CONTACT</a></li>
					</ul>
				</div>
				<div class="clearfix"></div>
				<!-- script-for-menu -->
				<script>
			$( "span.menu" ).click(function() {
			$( "ul.cl-effect-1" ).slideToggle( 300, function() {
			// Animation complete.
			});
			});
		</script>
				<!-- script-for-menu -->
			</div>
			<div class="banner-text wow fadeInUp" data-wow-delay="0.5s">
				<h1>
					Plan and <span>Book</span> Your Perfect Trip
				</h1>
			</div>
			<div class="booking-form">
				<!---strat-date-piker---->
				<link rel="stylesheet" href="css/jquery-ui.css" />
				<script src="js/jquery-ui.js"></script>
				<script>
									  $(function() {
										$( "#datepicker,#datepicker1" ).datepicker();
									  });
							  </script>
				<!---/End-date-piker---->
				<link type="text/css" rel="stylesheet" href="css/JFGrid.css" />
				<link type="text/css" rel="stylesheet" href="css/JFFormStyle-1.css" />
				<script type="text/javascript" src="js/JFCore.js"></script>
				<script type="text/javascript" src="js/JFForms.js"></script>
				
				<div class="online_reservation">
					<div class="b_room">
						<div class="booking_room">
							<div class="reservation">
								<ul>
									<li class="span1_of_1 left">
										<h5>From</h5>
										<div class="book_date">
											<form>
												<input type="text" placeholder="Type Depature City"
													required="required">
											</form>
										</div>
									</li>
									<li class="span1_of_1 left">
										<h5>To</h5>
										<div class="book_date">
											<form>
												<input type="text" placeholder="Type Destination City"
													required="required"
													pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?">
											</form>
										</div>
									</li>
									<li class="span1_of_1 left">
										<h5>Arrival</h5>
										<div class="book_date">
											<form>
												<input class="date" id="datepicker" type="text"
													value="2/04/2015" onfocus="this.value = '';"
													onblur="if (this.value == '') {this.value = '2/04/2015';}"
													required=>
											</form>
										</div>
									</li>
									<li class="span1_of_1 left">
										<h5>Depature</h5>
										<div class="book_date">
											<form>
												<input class="date" id="datepicker1" type="text"
													value="22/08/2015" onfocus="this.value = '';"
													onblur="if (this.value == '') {this.value = '22/08/2015';}"
													required=>
											</form>
										</div>
									</li>
									<li class="span1_of_1">
										<h5>Class</h5> <!----------start section_room----------->
										<div class="section_room">
											<select id="country" onchange="change_country(this.value)"
												class="frm-field required">
												<option value="null">Economy</option>
												<option value="null">Business</option>
												<option value="AX">First Class</option>
												<option value="AX">Premium Economy</option>
											</select>
										</div>
									</li>
									<li class="span1_of_3">
										<div class="date_btn">
											<form>
												<input type="submit" value="Book Now" />
											</form>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<!---->
			</div>
			<div class="clearfix"></div>
			<div class="online-form">
				<a class="play-icon popup-with-zoom-anim" href="#small-dialog2">Booking
					Here</a>
			</div>
			<div id="small-dialog2" class="mfp-hide">

				<div class="clearfix"></div>
			</div>

		</div>
	</div>
	
	
	<script type="text/javascript">
		$(document).ready(function() {
				/*
				var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
				};
				*/
		$().UItoTop({ easingType: 'easeOutQuart' });
});
		
		
</script>
	<a href="#to-top" id="toTop" style="display: block;"> <span
		id="toTopHover" style="opacity: 1;"> </span></a>
	<!---->
</body>
</html>