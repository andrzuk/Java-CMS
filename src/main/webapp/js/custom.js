/*
 * Custom scripts for all purposes
 */

document.addEventListener('DOMContentLoaded', function() {
	
	var cons = document.getElementsByClassName("dc");
	var i;

	for (i = 0; i < cons.length; i++) {
		
		var img_width = cons[i].firstElementChild.clientWidth;
		var img_height = cons[i].firstElementChild.clientHeight;
		
		cont = document.createElement("div");
		cont.setAttribute("class", "loading");
		
		cont.style.left = (img_width / 2 - 16).toString() + "px";
		cont.style.top = (img_height / 2 - 16).toString() + "px";

		spin = document.createElement("img");
		spin.src = "gallery/ajax/loader.gif";
		spin.setAttribute("class", "spinner");
		
		cont.appendChild(spin);
	  
		cons[i].appendChild(cont);
	}
	
});

function showImage(obj) {
	
	obj.style.visibility = "visible";
	obj.nextElementSibling.firstElementChild.style.display = "none";
}
