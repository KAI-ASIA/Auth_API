package com.kaiasia.app.service.Auth_api.utils;

import com.kaiasia.app.service.Auth_api.config.ApiProperties;

import ms.apiclient.model.ApiBody;
import ms.apiclient.model.ApiHeader;
import ms.apiclient.model.ApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ConvertApiHelper {
    private static final Logger log = LoggerFactory.getLogger(ConvertApiHelper.class);


    /**
     * Chuyển đổi yêu cầu API bằng cách cập nhật header và sao chép body.
     *
     * @param req Yêu cầu API gốc cần chuyển đổi.
     * @param LOCATION Vị trí mà quá trình chuyển đổi diễn ra (dùng cho log).
     * @return Một đối tượng ApiRequest mới với header và body đã được cập nhật.
     */
    public ApiRequest convertApi(ApiRequest req , ApiProperties apiProperties, String LOCATION) {
        if (req == null) {
            return null;
        }


        ApiRequest newApiReq = new ApiRequest();
        ApiHeader header = new ApiHeader();
        header.setReqType("REQUEST");
        header.setApi(apiProperties.getApiName());
        header.setApiKey(apiProperties.getApiKey());
        header.setChannel("API");
        header.setLocation("PC/IOS");
        header.setRequestAPI("FE API");
        header.setRequestNode("node 01");
        header.setPriority(1);

        ApiBody body = new ApiBody();
        LinkedHashMap<String, String> enquiry = (LinkedHashMap<String, String>)req.getBody().get("enquiry");
        enquiry.replace("authenType", apiProperties.getAuthenType());
        body.put("enquiry",enquiry);
        body.put("command","GET_ENQUIRY");

        newApiReq.setHeader(header);
        newApiReq.setBody(body);



        return newApiReq;







    }
}
