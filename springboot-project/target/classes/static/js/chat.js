function enterPressed(e) {
    if (e.keycode === 13 && !e.shiftKey) {
        e.preventDefault();
        document.getElementById("send").click();
    }
}

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/chat'
});

window.onload = function () {
    var objDiv = document.getElementById("scrollDiv");
    objDiv.scrollTop = objDiv.scrollHeight;
    connect();
};

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', (messageOutput) => {
        showMessageOutput(JSON.parse(messageOutput.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function connect() {
    stompClient.activate();
}

function sendMessage() {
    var from = document.getElementById('user').value;
    var text = document.getElementById('message').value;
    var id = document.getElementById('id').value;

    if (stompClient.connected) {
        stompClient.publish({
            destination: "/app/messages",
            body: JSON.stringify({ 'user': from, 'message': text, 'id': id })
        });
    } else {
        connect();
    }
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('messagesBody');
    var from = document.getElementById('user').value;
    if (from == messageOutput.user) {
        var htmlString = '<div class="row">' +
            '<div class="d-flex justify-content-end">' +
            '<div class="col-sm-auto col-lg-6"></div>' +
            '<div class="col-sm-auto col-lg-6">' +
            '<div class="card mt-lg-5 mb-5 w-lg-75 mt-sm-auto mb-sm-auto w-sm-auto bg-info shadow">' +
            '<div class="card-body">' +
            '<p class="card-text text-left">' + messageOutput.message + '</p>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    }
    else {
        var htmlString = '<div class="row">' +
            '<div class="d-flex justify-content-start">' +
            '<div class="col-sm-auto col-lg-6">' +
            '<div class="card mt-lg-5 mb-5 w-lg-75 mt-sm-auto mb-sm-auto w-sm-auto shadow">' +
            '<div class="card-body">' +
            '<p class="card-text text-left">' + messageOutput.user + ': ' + messageOutput.message + '</p>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="col-sm-auto col-lg-6"></div>' +
            '</div>' +
            '</div>';
    }

    const existingContent = response.innerHTML;
    response.innerHTML = existingContent + htmlString;
    document.getElementById('message').value = '';
    var objDiv = document.getElementById("scrollDiv");
    objDiv.scrollTop = objDiv.scrollHeight;
}

function enterPressed(e) {
    if (e.keycode === 13 && !e.shiftKey) {
        e.preventDefault();
        document.getElementById("send").click();
    }
}

$(function () {
    $("#send").click(() => sendMessage());
    $("#message").keydown((event) => {
        if (event.keyCode == 13) {
            sendMessage();
        }
    });
});