function getSelfTop(){
	var h= document.documentElement.clientHeight;
	var height = (h-400)/2;
	var w= document.documentElement.clientWidth;
	var width=(w-600)/2;
	$("#mask_last").css("margin-left",width);
	$("#mask_last").css("top",height);
	$("#edit_last").css("margin-left",width);
	$("#edit_last").css("top",height);
};

