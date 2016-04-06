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
	


	
	function game(){
		$.get( "webapi/cards", function( result ) {
			
			out = "";
			for(var i=0; i<10; i++) 
				{
				out += '<div class="game-cards"><div style="background-image:url(data/cards/' + result[i].image +')" class="card" data-id="' + result[i].id +'"> </div> <input type="text"/> </div>';
				}
			$('#game-card-container').html(out);
			displayView('start');
			timeShow(3,'game');
		});

		};
	
		function gameOver(){
			dataToSend=[];
			timeHide();
			$('.game-cards').each(function(){
				var inputCard = $('input',$(this)).val();
				var inputId = $('.card',$(this)).attr('data-id');
				dataToSend.push({
					"id":inputId,
					"name":inputCard
				})
				
			})
			console.log(dataToSend)
			
		}
	
		function displayPics(arr,selector) {
				
			    var out = "";
			    var i;
			    for(i = 0; i<arr.length; i++) {
			    	
			    	out += '<div style="background-image:url(data/cards/' + arr[i].image +')" class="card" data-id="' + i +'"></div>';
			        
			    }
		    $(selector).html(out);
			}
	
	    function summary(){
	    	
	    		displayView('summary');
	    	
	    }	
	        
	
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
		timeHide();
		clearInterval(timer);
		displayView('cs-loader');
		setTimeout(function(){ 
			game();
		}, 3000);
		
		
		
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
		     if(timeLeft !=0){
		       $('#time-left').html(timeLeft);
		        timeLeft--;
		     }
		     else{
		    	 if(task=="preparation")
		        wait();
		    	 if(task=="game") gameOver();
		     }
		   },1000);		
		 $(".time").show();
	}
	
	function displayView(view){
		$(".login, .cs-loader, .choose-photos, .start, .summary").hide();
	    $("."+view).show();
	}
	//---------------Event Listeners--------------------------
	$('#play').click(function(){
		me.name=$('#name').val();
		displayView('cs-loader');
		$.post("webapi/users/create", { username: me.name }, function(data) {
			me.id = data.id;
			
		}); 
		
		setTimeout(function(){ preparation() }, 1000)
	})
	
	function updateSideCards(card) {
		marginFromBottom+=10;
		$('.scale').append('<div style="bottom:'+marginFromBottom+';background-image:url(data/cards/'+card.image+')"></div>');
	}
	
	$('body').on('click', '#prepare-screen-card-holder .card', function() {
	
		if(chosenCards.length == 9){
			wait();
		}else{
			var id = $(this).attr("data-id");
			console.log(id);
			chosenCards.push(CardsYouCanChooseFrom[id]);
			updateSideCards(CardsYouCanChooseFrom[id])
			console.log('');
			console.log(CardsYouCanChooseFrom);
			CardsYouCanChooseFrom.splice(id, 1);
			console.log(chosenCards);
			cards();
			// '<div style="background-image:url(data/cards/' + arr[i].image +')" class="card" data-id="' + i +'"></div>';
		}
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

	


	
	
	
