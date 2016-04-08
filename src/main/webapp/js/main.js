// remove before production
var DEBUG = false;

$( document ).ready(function() {
	me ={
		"id":0,
		"name":""		
	}
	marginFromBottom=0;
	 var allCards = [];
	var CardsYouCanChooseFrom =[];
	var chosenCards = [];
	var threePhotos = [];
	var gameId = null;
	
	
	function login(){
		displayView('login');
	}
	login();
	
	function preparation(){
	timeShow(30,'preparation');
	displayView('choose-photos');
	$.get( "webapi/cards", function( data ) {
		allCards = data;
		CardsYouCanChooseFrom = allCards;
		cards();
		//shuffle(allCards);
		
	});
	}
	


	
		function game(cards){
		
			out = "";
			for(var i=0; i<10; i++) 
				{
				out += '<div class="game-cards"><div style="background-image:url(data/cards/' + cards[i].image +')" class="card" data-id="' + cards[i].id +'"> </div> <input type="text"/> </div>';
				}
			$('#game-card-container').html(out);
			
			displayView('start');
			timeShow(60,'game');
		};
	
		function gameOver(){
			cardsToSend=[];
			$('.game-cards').each(function(){
				var inputCard = $('input',$(this)).val() || " ";
				if (inputCard.length > 0) inputCard = " ";
				var inputId = $('.card',$(this)).attr('data-id');
				cardsToSend.push({
					"id":inputId,
					"name":inputCard
				})
				
			});
			
			var gameResultHandle = setInterval(function() {
				$.ajax({
				    url: 'webapi/results/compare',
				    type: 'post',
				    data: JSON.stringify({
				    	cards: cardsToSend,
				    	userId: me.id,
				    	gameId: gameId,
				    }),
				    headers: {
				        "Content-Type": 'application/json',   //If your header name has spaces or any other char not appropriate
				        "Accept": 'application/json'  //for object property name, use quoted notation shown in second
				    },
				    dataType: 'json',
				    success: function (data) {
				    	if (data.error) {
				    		console.log(data.error);
				    	} else {
							summary(data);
					    	clearInterval(gameResultHandle);
				    	}
				    },
				    contentType: 'application/json; charset=utf-8',
				    dataType: 'json',
				});
			}, 1000);
			
		}
	
		function displayPics(arr,selector) {
				
			    var out = "";
			    var i;
			    for(i = 0; i<arr.length; i++) {
			    	
			    	out += '<div style="background-image:url(data/cards/' + arr[i].image +')" class="card" data-id="' + i +'"></div>';
			        
			    }
		    $(selector).html(out);
			}
	
	    function summary(gameResults){
	    	console.log(gameResults);
	    		displayView('summary');
	    			
	    			$('.result-me .result-points').html('Points: ' + gameResults.me.points ) ;
	    			if(gameResults.me.winner == true)
	    			{
	    				$('.result-me .winner').html('<span style="color:green">Winner</span>') ;
    				}else{ 
    					$('.result-me .winner').html('<span style="color:red">Loser</span>') ;
	    				}
	    			
	    			$('.result-me .player-name').html(gameResults.me.name ) ;
	    			for(var i = 0; i<gameResults.me.wrong.length; i++){
	    			$('.result-me').append('<div class="mistake"><div class="answer" style="background-image:url(data/cards/'  +  gameResults.me.wrong[i].image +'"></div> <p> ' +  gameResults.me.wrong[i].name  + '</p></div>');	
	    			
	    		};
	    			
		    		$('.result-you .result-points').html('Points: ' + gameResults.you.points ) ;
		    		if(gameResults.you.winner == true)
	    			{
	    				$('.result-you .winner').html('<span style="color:green">Winner</span>') ;
	    				}else{ 
	    					$('.result-you .winner').html('<span style="color:red">Loser</span>') ;
	    				}
	    			$('.result-you .player-name').html(gameResults.you.name ) ;
	    			for(var i = 0; i<gameResults.you.wrong.length; i++){
	    			$('.result-you').append('<div class="mistake"><div class="answer" style="background-image:url(data/cards/'  +  gameResults.you.wrong[i].image +'"></div> <p> ' +  gameResults.you.wrong[i].name  + '</p></div>');
    		};

	    		};
	    	
	    
	        
	
	function cards(){
	CardsYouCanChooseFrom = shuffle(CardsYouCanChooseFrom); 
	var arrayToDisplay=[]
	for(i=0;i<3;i++){
		arrayToDisplay.push(CardsYouCanChooseFrom[i]);
	}
	
	displayPics(arrayToDisplay,'#prepare-screen-card-holder');
	}
	function wait()
	{	
		var PrepareCardsToSend=[];
		for(i=0;i<chosenCards.length;i++){
			PrepareCardsToSend.push(chosenCards[i].id | 0)
		}
		
		timeHide();
		clearInterval(timer);
		displayView('cs-loader');
		
		$.ajax({
		    url: 'webapi/game/cards',
		    type: 'post',
		    data: JSON.stringify({
		    	cardIds: PrepareCardsToSend,
		    	userId: me.id,
		    	gameId: gameId,
		    }),
		    headers: {
		        "Content-Type": 'application/json',   //If your header name has spaces or any other char not appropriate
		        "Accept": 'application/json'  //for object property name, use quoted notation shown in second
		    },
		    dataType: 'json',
		    success: function (data) {
		    	if (data.error) {
		    		console.error(data.error);
		    	}
		    },
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json',
		});
		
		var waitCardsHandle = setInterval(function() {
			$.ajax({
			    url: 'webapi/game/cards',
			    type: 'get',
			    headers: {
			        "Content-Type": 'application/json',   //If your header name has spaces or any other char not appropriate
			        "Accept": 'application/json'  //for object property name, use quoted notation shown in second
			    },
			    data: { userId: me.id, gameId: gameId },
			    dataType: 'json',
			    success: function (data) {
			    	if (data.status === "success") {
		    			game(data.cards);
		    			clearInterval(waitCardsHandle);
		    		}
			    },
			    contentType: 'application/json; charset=utf-8',
			    dataType: 'json',
			});
		}, 1000);
		
	}
	
	
	
	function timeHide()
	{
		$(".time").hide();
		clearInterval(timer)
	}
	function timeShow(sec,task)
	{
		   timeLeft=sec; 
		   timer = setInterval(function()
		   {
		     if(timeLeft > 0){
		       $('#time-left').html(timeLeft);
		        timeLeft--;
		     }
		     else{
		    	 if(task=="preparation")
		        wait();
		    	 if(task=="game") gameOver();
		    	 clearInterval(timer);
		     }
		   },1000);		
		 $(".time").show();
	}
	
	function displayView(view){
		$(".login, .cs-loader, .choose-photos, .start, .summary").hide();
	    $("."+view).show();
	}
	
	$('body').on('click', 'button#ready-btn', function(){
		gameOver();
		clearInterval(timer);
	});

			function updateUserCount() {
				$.get("webapi/users/count", function(data) {
				if (!data.error) {
					var playerCount = data.value;
					$(".player-count").html(playerCount);
				}
				});
			}
				updateUserCount();
				setInterval(updateUserCount, 5000);	
	//---------------Event Listeners--------------------------
	$('#play').click(function(){
		me.name="John";
		me.name=$('#name').val();
		displayView('cs-loader');
		/*$.post("webapi/users/create", { "username": me.name }, function(data) {
			me.id = data.id;
		}, "json");*/
		
		$.ajax({
		    url: 'webapi/users/create',
		    type: 'post',
		    data: JSON.stringify({
		        "username": me.name,
		    }),
		    headers: {
		        "Content-Type": 'application/json',   //If your header name has spaces or any other char not appropriate
		        "Accept": 'application/json'  //for object property name, use quoted notation shown in second
		    },
		    dataType: 'json',
		    success: function (data) {
		    	me.id = data.id;
		    },
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json',
		});
		
		if (DEBUG) {
			setTimeout(function() {
				preparation();
			}, 1000);
		} else {
			var connectHandle = setInterval(function() {
				$.ajax({
				    url: 'webapi/users/connect',
				    type: 'post',
				    data: JSON.stringify({
						id: me.id,
					}),
				    headers: {
				        "Content-Type": 'application/json',
				        "Accept": 'application/json'
				    },
				    dataType: 'json',
				    success: function (data) {
				    	if (!data.error) {
							gameId = data.id;
							preparation();
					    	clearInterval(connectHandle);
						}
				    },
				    contentType: 'application/json; charset=utf-8',
				    dataType: 'json',
				});
			}, 1000);
		}
	})
	
	function updateSideCards(card) {
		marginFromBottom+=10;
		$('.scale').append('<div style="bottom:'+marginFromBottom+';background-image:url(data/cards/'+card.image+')"></div>');
	}
	
	$('body').on('click', '#prepare-screen-card-holder .card', function() {

		var id = $(this).attr("data-id");
		chosenCards.push(CardsYouCanChooseFrom[id]);
		updateSideCards(CardsYouCanChooseFrom[id])
		CardsYouCanChooseFrom.splice(id, 1);
		cards();
		// '<div style="background-image:url(data/cards/' + arr[i].image +')" class="card" data-id="' + i +'"></div>';
		if(chosenCards.length == 10){
			wait();
		}
	});
	
	var CHOOSE_CARDS_TIMEOUT = 30;
	
	$("#debug-button").click(function() {
		DEBUG = true;
	});
	
	
});



	function shuffle(sourceArray) {
	    for (var i = 0; i < sourceArray.length - 1; i++) {
	        var j = i + Math.floor(Math.random() * (sourceArray.length - i));

	        var temp = sourceArray[j];
	        sourceArray[j] = sourceArray[i];
	        sourceArray[i] = temp;
	    }
	    return sourceArray;
	}

	


	
	
	
