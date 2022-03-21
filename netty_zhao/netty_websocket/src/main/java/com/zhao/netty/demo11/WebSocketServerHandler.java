package com.zhao.netty.demo11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import com.zhao.netty.demo11.dto.Response;
import com.zhao.netty.demo11.entity.Client;
import com.zhao.netty.demo11.service.MessageService;
import com.zhao.netty.demo11.service.RequestService;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * (1) 第一次握手由HTTP协议承载，所以是一个HTTP消息，根据消息头中是否包含"Upgrade"字段来判断是否是websocket。
 * (2) 通过校验后，构造WebSocketServerHandshaker，通过它构造握手响应信息返回给客户端，同时将WebSocket相关的编码和解码类动态添加到ChannelPipeline中。
 *
 * 下面分析链路建立之后的操作：
 * (1) 客户端通过文本框提交请求给服务端，Handler收到之后已经解码之后的WebSocketFrame消息。
 * (2) 如果是关闭按链路的指令就关闭链路
 * (3) 如果是维持链路的ping消息就返回Pong消息。
 * (4) 否则就返回应答消息
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    // websocket 服务的 uri
    private static final String WEBSOCKET_PATH = "/websocket";

    // 一个 ChannelGroup 代表一个直播频道 <userId, <event, client>>
    private static Map<Long, Map<String, ChannelGroup>> channelGroupMap = new ConcurrentHashMap<>();

    // 本次请求的 code
    private static final String HTTP_REQUEST_USERID = "id";
    private static final String HTTP_REQUEST_EQUIPMENTCODE = "token";
    private static final String HTTP_REQUEST_EVENT = "event";

    private Client client;

    // 握手
    private WebSocketServerHandshaker handshaker;

    // 第一次握手由HTTP协议承载，所以是一个HTTP消息，根据消息头中是否包含"Upgrade"字段来判断是否是websocket。
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) {
        // 接收处理消息
        if (msg instanceof FullHttpRequest) {
            // 处理HTTP请求
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            // 处理WebSocket
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 处理Http请求
     * @param ctx
     * @param req
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        if ("/favicon.ico".equals(req.uri()) || ("/".equals(req.uri()))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();

//        if (parameters.size() == 0 || !parameters.containsKey(HTTP_REQUEST_EQUIPMENTCODE)) {
//            System.err.printf(HTTP_REQUEST_EQUIPMENTCODE + "参数不可缺省");
//            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
//            return;
//        }

        String clientInfo = new String(Base64.decodeBase64(parameters.get("clientInfo").get(0)));
        JSONObject jsonObject = new JSONObject(clientInfo);


        // 客户端注册
        client = RequestService.clientRegister(String.valueOf(jsonObject.getInt(HTTP_REQUEST_USERID)), jsonObject.getString(HTTP_REQUEST_EVENT), jsonObject.getString(HTTP_REQUEST_EQUIPMENTCODE));
        if (client.getEvent().equals("")) {
            System.err.printf("操作事件不可缺省");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }

        Map<String, ChannelGroup> eventMap;
        // 房间列表中如果不存在则为该频道,则新增一个频道 ChannelGroup
        if (!channelGroupMap.containsKey(client.getId())) {
            System.out.println("用户:" + client.getId() + ":登录");
            eventMap = new ConcurrentHashMap<>();
        } else {
            eventMap = channelGroupMap.get(client.getId());
        }

        if (!eventMap.containsKey(client.getEvent())) {
            if ("live".equals(client.getEvent())){
                System.out.println(client.getId()+ ":进入直播间");
            }

            eventMap.put(client.getEvent(), new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
        // 确定有房间号,才将客户端加入到频道中
        eventMap.get(client.getEvent()).add(ctx.channel());
        channelGroupMap.put(client.getId(), eventMap);

        System.out.println("当前在线人数:" + channelGroupMap.get(client.getId()).size());

        // Handshake  构造握手响应返回，
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            ChannelFuture channelFuture = handshaker.handshake(ctx.channel(), req);

            // 握手成功之后,业务逻辑
            if (channelFuture.isSuccess()) {
                if (client.getId() == 0) {
                    System.out.println(ctx.channel() + " 游客");
                    return;
                }

            }
        }
    }

    // 广播
    private void broadcast(ChannelHandlerContext ctx, WebSocketFrame frame) {

        if (client.getId() == 0) {
            Response response = new Response(1001, "没登录不能聊天哦");
            String msg = new JSONObject(response).toString();
            ctx.channel().write(new TextWebSocketFrame(msg));
            return;
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println(" 收到 " + ctx.channel() + request);

        if (!request.equals("heartbeat")) {
            Response response = MessageService.sendMessage(client, request);
            String msg = new JSONObject(response).toString();
            if (channelGroupMap.containsKey(client.getId()) && channelGroupMap.get(client.getId()).containsKey(client.getEvent())) {
                ChannelGroup channelGroup = channelGroupMap.get(client.getId()).get(client.getEvent());
                channelGroup.forEach(c -> {
                    c.writeAndFlush(new TextWebSocketFrame(String.format("msg: {}, num: 1", msg)));
                    c.writeAndFlush(new TextWebSocketFrame(String.format("msg: {}, num: 2", msg)));
                    c.writeAndFlush(new TextWebSocketFrame(String.format("msg: {}, num: 3", msg)));
                    c.writeAndFlush(new TextWebSocketFrame(String.format("msg: {}, num: 4", msg)));
                });
            }
        }
    }

    /**
     * 处理WebSocket框架
     * @param ctx
     * @param frame
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        broadcast(ctx, frame);
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaderUtil.setContentLength(res, res.content().readableBytes());
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaderUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    //  发生异常时，关闭连接（channel），随后将channel从ChannelGroup中移除
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    // 当客户端连接服务端之后(打开连接)
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
//        System.out.println(incoming.remoteAddress() + "进入直播间");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (client != null && channelGroupMap.containsKey(client.getId())) {
            Map<String, ChannelGroup> eventMap = channelGroupMap.get(client.getId());
            eventMap.get(client.getEvent()).remove(ctx.channel());
            if(!eventMap.containsKey(client.getEvent()) || eventMap.get(client.getEvent()).size() == 0) {
                eventMap.remove(client.getEvent());
                if ("live".equals(client.getEvent())) {
                    System.out.println("用户" + client.getId() + "退出直播间");
                }
            }

            if (eventMap == null || eventMap.size() == 0) {
                channelGroupMap.remove(client.getId());
                System.out.println("用户" + client.getId() + "注销");
            }
            System.out.println("当前在线人数:" + channelGroupMap.size());
        }
    }

    private static String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HOST) + WEBSOCKET_PATH;
        return "ws://" + location;
    }
}
