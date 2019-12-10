/*
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */

package io.lettuce.test;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterreplica.MasterReplica;
import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;

/**
 * @author yangpengyi
 * @Title TestReadFrom
 * @Description
 * @date 2019/11/28 11:18
 */
public class TestReadFrom {
    public static void main(String[] args) {


        RedisURI redisURI = RedisURI.create("redis-sentinel://172.20.1.27:25240,172.20.1.28:25240/1#queue-server");
        RedisClient redisClient = RedisClient.create();
        redisURI.setPassword("Hybris1234");
        String defaultUrls ="172.20.1.27:5240,172.20.1.28:5240";
        StatefulRedisMasterReplicaConnection<String, String> connection = MasterReplica.connect(redisClient, new StringCodec(),redisURI,defaultUrls);
//        StatefulRedisMasterReplicaConnection<String, String> connection = MasterReplica.connect(redisClient, new StringCodec(), redisURI);
        connection.setReadFrom(ReadFrom.REPLICA_PREFERRED);
        RedisCommands<String, String> commands = connection.sync();
        for(int i =1; i<=10; i++){
            System.out.println("commands: "+commands.get("test"));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
