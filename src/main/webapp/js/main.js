$( document ).ready(function() {
	me ={
		"id":0,
		"name":""		
	}
	 var allCards = [];
	var CardsYouCanChooseFrom =[];
	var chosenCards = [];
	var threePhotos = [];
	
	
	function login(){
		displayView('login');
		timeHide();
	}
	login();
	
	function preparation(){
	timeShow();
	displayView('choose-photos');
	$.get( "webapi/cards", function( data ) {
		allCards = data;
		CardsYouCanChooseFrom = allCards;
		cards();
		//shuffle(allCards);
		
		
	});
	}
	
	function StartTimer(sec){
		   var timeLeft=sec; 
		   timer = setInterval(function()
		   {
		     if(timeLeft !=0){
		       $('#time-left').html(timeLeft);
		        timeLeft--;
		     }
		     else{
		        console.log("gameOver");
		     }
		   },1000);
		};
	
	function game(){
		displayView('start');
		timeShow();
		console.log('---');
		console.log(arrayToDisplay);
		displayPics(arrayToDisplay,'#game');
	}
	function displayPics(arr,selector) {
		
	    var out = "";
	    var i;
	    for(i = 0; i<arr.length; i++) {
	    	
	    	out += '<div style="background-image:url(data/cards/' + arr[i].image +')" class="card" data-id="' + i +'"></div>';
	        
	    }
	    $(selector).html(out);
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
		timeHide()
		displayView('cs-loader');
		setTimeout(function(){ 
			game();
		}, 3000);
		
		
		
	}
	function timeHide()
	{
		$(".time").hide();
	}
	function timeShow()
	{
		 $(".time").show();
	}
	
	function displayView(view){
		$(".login, .cs-loader, .choose-photos, .start, .summary").hide();
	    $("."+view).show();
	}
	//---------------Event Listener--------------------------
	$('#play').click(function(){
		me.name=$('#name').val();
		displayView('cs-loader');
		$.post("webapi/users/create", { username: me.name }, function(data) {
			me.id = data.id;
			
		}); 
		
		setTimeout(function(){ preparation() }, 1000)
	})
	$('body').on('click', '.card', function() {
	
		if(chosenCards.length == 9){
			wait();
		}else{
			var id = $(this).attr("data-id");
			console.log(id);
			chosenCards.push(CardsYouCanChooseFrom[id]);
			console.log('');
			console.log(CardsYouCanChooseFrom);
			CardsYouCanChooseFrom.splice(id, 1);
			console.log(chosenCards);
			cards()
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

	


	
	
	
