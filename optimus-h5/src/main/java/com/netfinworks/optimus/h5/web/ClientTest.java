package com.netfinworks.optimus.h5.web;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.netfinworks.invest.facade.InvestQueryFacade;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by weichunhe on 2016/7/27.
 */
public class ClientTest {
    public static void main(String[] args) {
        main2();
    }

    public static void main2() {
        JsonRpcHttpClient client = null;
        try {
            client = new JsonRpcHttpClient(new URL("http://10.5.16.5:9002/optimus-h5/jsonrpc/com/netfinworks/optimus/rpctest/RpcTest"));
            System.out.println(client.invoke("hello", new Object[]{"wwww"}, String.class));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void main3() {
        JsonRpcHttpClient client = null;
        try {
            client = new JsonRpcHttpClient(new URL("http://10.5.16.6:9004/com/netfinworks/invest/facade/InvestQueryFacade"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InvestQueryFacade service = ProxyUtil.createClientProxy(client.getClass().getClassLoader(), InvestQueryFacade.class, client);
        System.out.println(service.findBySubjectNo("20160421161117S50001"));
    }
}
