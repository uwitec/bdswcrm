function goNext(obj,element){
	if (window.event.keyCode==13){
        for(var tmpa=0;tmpa<obj.length;tmpa++){
            if(obj[tmpa].name == element){
                id=tmpa;
                break;
            }
        }
        if(id+1<obj.length){
        	try{        		
        		obj[id+1].focus();
        		obj[id+1].select();
        	}catch(ex){
        		//goNext(obj[id+2],element);
        	}
        }
	}
}