package io.hamibot.dcgateway.utils.handler.send;
import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.adapter.HamibotAdapter;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.util.SocketData;
import org.json.JSONObject;


public class HamibotIdentify implements SendHandler {

    @Override
    public void createMessage(WebSocket webSocket, SocketData socketData) {

        HamibotAdapter hamibotAdapter = (HamibotAdapter) socketData.adapter;

        JSONObject payload = new JSONObject();
        payload.put("operation", 2);

        JSONObject content = new JSONObject();
        content.put("shard_count", socketData.shardingCount);
        content.put("shard_id", socketData.shardingId);

        payload.put("content", content);

        hamibotAdapter.send(payload);


    }

}
