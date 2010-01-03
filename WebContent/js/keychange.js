function goNext(obj,element){
	if (window.event.keyCode==13){
        for(var tmpa=0;tmpa<obj.length;tmpa++){
            if(obj[tmpa].name == element){
                id=tmpa;
                break;
            }
        }
        if(id+1<obj.length){
        	obj[id+1].focus();

        	try{
        		obj[id+1].select();
        	}catch(ex){}
        }
	}
}