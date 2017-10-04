function Point(i,j){
    var i = i;
    var j = j;

    this.getI = function(){
        return i;
    }

    this.getJ = function(){
        return j;
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
        //var points = readFromServer();
        env.clear();
        points.map(
            function(point){
                env.drawRect(point.getI(), point.getJ());
            });
    }

    this.drawRect = function(i,j){
        ctx.fillStyle = 'rgb(0,200,0)'; // sets the color to fill in the rectangle with
        ctx.fillRect(i*GRID_WIDTH,j*GRID_WIDTH , GRID_WIDTH, GRID_WIDTH);
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

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        env = new Environment(40,40);
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
    var points = eval(message).map(function(p){return new Point(p.i,p.j);})
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
});



