package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import ms.apiclient.model.ApiHeader;

public class ApiUtils {

    public static ApiHeader buildApiHeader(ApiHeader header) {
        ApiHeader h = new ApiHeader();
        h.setReqType("REQUEST");
        h.setApi(header.getApi());
        h.setApiKey(header.getApiKey());
        h.setChannel(header.getChannel());
        h.setContext(header.getContext());
        h.setLocation(header.getLocation());
        h.setPriority(header.getPriority());
        h.setRequestAPI("AUTHEN-API");
        h.setRequestNode(header.getRequestNode());
        return h;
    }

}
