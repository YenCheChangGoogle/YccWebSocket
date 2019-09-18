<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <title>WebSocket with SockJS</title>
</head>
<body>
<h1>SockJS互動範例</h1>
<ul id="ul">
</ul>
<script type="text/javascript" src="/YccWebSocket/resources/js/sockjs-1.0.0.min.js"></script>
<script>

    // SockJS與原生的WebSocket的方法基本是一致的, 所以只需要將 new WebSocket(url) 换成 new SockJS(url) 就可以了    
    var url = "/YccWebSocket/echo";    
    var sock = new SockJS(url);
    //var url = "ws://localhost:8080/YccWebSocket/echo2"; //如果有SSL加密後, ws就是wss
    //var sock = new WebSocket(url); //WebSocket 是HTML5開始提供的一種瀏覽器與伺服器間進行全雙工通訊的網路技術, WebSocket通訊協定於2011年被IETF定為標準RFC6455, WebSocketAPI被W3C定為標準
   
    //啟動連線的時後
    sock.onopen = function (event) {
        console.log("啟動連線");
        sayHey();
    };

    //收到伺服器回應時候
    sock.onmessage = function (event) {
    	console.log("接收到的訊息型態是"+sock.binaryType+", 接收到是blob數據內容長度是"+event.data.length+", "+event.data);
    	
    	//sock.binaryType = "arraybuffer"; //接收到是文字內容
    	//sock.binaryType = "blob"; //接收到是blob數據
        
        var li = document.createElement("li");
        li.innerText = event.data;
        document.getElementById("ul").appendChild(li);
        setTimeout(sayHey, 2000);
    };
    
    //關閉連線的時候
    sock.onclose = function (event) {
        console.log("關閉連線");
    };
    
    //傳送發生錯誤
    sock.onerror = function (event) {
    	console.log("傳送發生錯誤");
    }

    //送出訊息
    function sayHey() {
    	
    	//送二進位的範例
    	//var file = document.querySelector('input[type="file"]').files[0];
    	//sock.send(file);
    	
    	//送 canvas ImageData as ArrayBuffer的範例
    	//var img = canvas_context.getImageData(0, 0, 400, 320);
    	//var binary = new Uint8Array(img.data.length);
    	//for (var i = 0; i < img.data.length; i++) { binary[i] = img.data[i]; }
    	//sock.send(binary.buffer);
    	
    	//送文字的範例
        console.log("送出訊息 [Hello! YenCheChang!]");
        sock.send("Hello! YenCheChang!");
    };
</script>
</body>
</html>
