function Point(i,j,data){
    var i = i;
    var j = j;
    var data = data;

    this.getI = function(){
        return i;
    }

    this.getJ = function(){
        return j;
    }

    this.getData = function(){
        return data;
    }
}

function Environment(width, height){
    //properties
    var GRID_WIDTH = 20;
    var width = width;
    var height = height;

    var env = this;
    var myCanvas = document.querySelector('canvas');
    var ctx = myCanvas.getContext('2d');
    ctx.canvas.width = width*GRID_WIDTH;
    ctx.canvas.height = height*GRID_WIDTH;

    //public methods
    this.refresh = function(points){
        env.clear();
        points.map(
            function(point){
                env.drawRect(point.getI(), point.getJ(), point.getData());
            });
    }

    this.drawRect = function(i,j, properties){

        var iPos = i*GRID_WIDTH;
        var jPos = j*GRID_WIDTH;

        var color = findColorByAgentType(properties.agentType);
        ctx.fillStyle = color;
        ctx.fillRect(iPos,jPos , GRID_WIDTH, GRID_WIDTH);

        ctx.font = "12px Arial";
        ctx.fillStyle = "black";
        ctx.fillText(properties.name,iPos+GRID_WIDTH/4,jPos+GRID_WIDTH/1.5);
    }

    function findColorByAgentType(agentType){
        if(agentType == 'healthy'){
            return 'rgb(0,200,0)';
        }else if(agentType == 'therapist'){
            return 'rgb(0,0,200)';
        }else if(agentType == 'infected'){
            return 'rgb(200,0,0)';
        }else{//cured
            return 'rgb(200,200,200)';
        }
    }

    this.clear = function(){
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    }

    //private methods
    function readFromServer(){
        var i=Math.floor(Math.random()*height);
        var j=Math.floor(Math.random()*width);
        return [new Point(i,j)];
    }

}

 var REFRESH_FRECUENCY = 500;
 var env = null;





var stompClient = null;

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function configure(configuration) {
    stompClient.send("/app/configure", {}, JSON.stringify(configuration));
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect(configuration) {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        configure(configuration);
        env = new Environment(configuration.environment.width,configuration.environment.height);
        setInterval(function(){sendName()},REFRESH_FRECUENCY);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showGreeting(message) {
    var points = eval(message).map(function(p){return new Point(p.i, p.j, p.data);})
    env.refresh(points);
    //$("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

    //iniciar configuracion
    $("#myModal").modal('show');

});


