package com.kaiasia.app.service.Auth_api.utils;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiHeader;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.config.ApiProperties;
import com.sun.tools.javac.util.DefinedBy;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertApiHelper {
    private static final Logger log = LoggerFactory.getLogger(ConvertApiHelper.class);
    @Autowired
    private ApiConfig apiConfig;

    public ApiRequest covertApi(ApiRequest req , String apiName, String LOCATION){
        ApiRequest apiRequest = new ApiRequest();
        ApiHeader apiHeader = req.getHeader();
        try {
            ApiProperties apiProperties = apiConfig.getApi(apiName);
            apiHeader.setApi(apiProperties.getApiName());
            apiHeader.setApiKey(apiProperties.getApiKey());


           apiRequest.setHeader(apiHeader);


           return apiRequest;
        }catch (Exception e){
            log.error(LOCATION + e );
            return null;
        }

    }
}
