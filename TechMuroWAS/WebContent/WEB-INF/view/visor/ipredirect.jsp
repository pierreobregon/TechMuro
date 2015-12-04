<!doctype html>
<html>
	<head>
    <meta charset="utf-8">
	</head>

	<body>
	<input type="hidden" id=list />
	
<script type="text/javascript" src="../js-visor/jquery.min.js" ></script>
<script>

// NOTE: window.RTCPeerConnection is "not a constructor" in FF22/23
var RTCPeerConnection = /*window.RTCPeerConnection ||*/ window.webkitRTCPeerConnection || window.mozRTCPeerConnection;

if (RTCPeerConnection) (function () {
    var rtc = new RTCPeerConnection({iceServers:[]});
    if (window.mozRTCPeerConnection) {      // FF needs a channel/stream to proceed
        rtc.createDataChannel('', {reliable:false});
    };
    
    rtc.onicecandidate = function (evt) {
        if (evt.candidate) grepSDP(evt.candidate.candidate);
    };
    rtc.createOffer(function (offerDesc) {
        grepSDP(offerDesc.sdp);
        rtc.setLocalDescription(offerDesc);
    }, function (e) { console.warn("offer failed", e); });
    
    
    var addrs = Object.create(null);
    addrs["0.0.0.0"] = false;
    function updateDisplay(newAddr) {
        if (newAddr in addrs) return;
        else addrs[newAddr] = true;
        var displayAddrs = Object.keys(addrs).filter(function (k) { return addrs[k]; });
        document.getElementById('list').textContent = displayAddrs.join(" - ") || "n/a";
      
     //  var ip= displayAddrs.join(" - ") || "n/a";

	var ip;
for (i = 0; i < displayAddrs.length; i++) { 
     var temp =  displayAddrs[i];  
     var res = temp.substring(0,3);
     if(res == '118'){
   		ip = temp;
   		break; 
     }
	
}


      var url  = '${urlVisor}';
   
    $.get("../oficina_visor/"+ip+".htm",function(data){
		
		var codOficina = $.trim(data);
		location.replace(url + codOficina + ".htm");
	 
    });
    
    }
    
    function grepSDP(sdp) {
        var hosts = [];
        sdp.split('\r\n').forEach(function (line) {
            if (~line.indexOf("a=candidate")) {
                var parts = line.split(' '),
                    addr = parts[4],
                    type = parts[7];
                if (type === 'host') updateDisplay(addr);
            } else if (~line.indexOf("c=")) {
                var parts = line.split(' '),
                    addr = parts[2];
                updateDisplay(addr);
            }
        });
    }
})(); else {
    document.getElementById('list').innerHTML = "No se puede obtener IP";
}


</script>

</body></html>