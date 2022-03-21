(function () {
    let websocket;
    let portList = [];

    var initSocket = function () {
        var that = this;
        console.log("创建websocket");
        if (websocket == null) {
            websocket = new WebSocket("ws://localhost:8083/websocket?clientInfo=e2lkOjEsZXZlbnQ6J2xpdmUnLHRva2VuOic0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExMzQ1NmJmYid9");
        } else {

        }

        websocket.onopen = function(event) {
            portList.map(item=>{
                item.postMessage(event.data);
            });
        };
        websocket.onmessage = function (events) {
            console.log("收到websocket消息", events.data);
            portList.map(item=>{
                // item!=port&&item.postMessage(workerResult);  /**不发给自己 */
                item.postMessage(events.data);
            });
        };
        websocket.onclose = function (event) {
            console.log("关闭websocket!");
        };
        websocket.onerror = function (event) {
            console.log("websocket异常!");
        };
    };


    onconnect = function (e) {
        // 通过 e.ports 拿到 port
        var port = e.ports[0];
        portList.push(port);

        // port.onmessage 监听父线程的消息
        port.onmessage = function (event) {
            console.log("=========>" + event.data.method);

            switch (event.data.method) {
                case "open":
                    console.log("开启websocket");
                    initSocket();
                    break;
                case "send":
                    websocket.send(event.data.data);
                    portList.map(item=>{
                        // item!=port&&item.postMessage(workerResult);  /**不发给自己 */
                        item.postMessage("发送消息成功！");                 /**发给自己 */
                    });
                    break;
                case "terminate":
                    break;
            }
        }
    }
})();
