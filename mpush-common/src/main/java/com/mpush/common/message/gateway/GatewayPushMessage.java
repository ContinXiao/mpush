/*
 * (C) Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *   ohun@live.cn (夜色)
 */

package com.mpush.common.message.gateway;

import com.mpush.api.connection.Connection;
import com.mpush.api.protocol.Packet;
import com.mpush.common.message.ByteBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;

import static com.mpush.api.protocol.Command.GATEWAY_PUSH;

/**
 * Created by ohun on 2015/12/30.
 *
 * @author ohun@live.cn
 */
public class GatewayPushMessage extends ByteBufMessage {
    public String userId;
    public int clientType;
    public byte[] content;

    public GatewayPushMessage(String userId, int clientType, byte[] content, Connection connection) {
        super(new Packet(GATEWAY_PUSH, genSessionId()), connection);
        this.userId = userId;
        this.clientType = clientType;
        this.content = content;
    }

    public GatewayPushMessage(Packet message, Connection connection) {
        super(message, connection);
    }

    @Override
    public void decode(ByteBuf body) {
        userId = decodeString(body);
        clientType = decodeInt(body);
        content = decodeBytes(body);
    }

    @Override
    public void encode(ByteBuf body) {
        encodeString(body, userId);
        encodeInt(body, clientType);
        encodeBytes(body, content);
    }

    @Override
    public void send() {
        super.sendRaw();
    }

    @Override
    public void send(ChannelFutureListener listener) {
        super.sendRaw(listener);
    }

    @Override
    public String toString() {
        return "GatewayPushMessage{" +
                "userId='" + userId + '\'' +
                "clientType='" + clientType + '\'' +
                ", content='" + content.length + '\'' +
                '}';
    }
}
