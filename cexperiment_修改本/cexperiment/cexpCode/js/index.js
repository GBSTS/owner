
function chgvalueshowhide(elemid,felemid,n){
	var spwd=document.getElementById(elemid);
	if(spwd.value.length >= n){
		document.getElementById(felemid).style.display="block";
	}else{
		document.getElementById(felemid).style.display="none";
	}
}