package com.kaiasia.app.service.Auth_api.utils;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiHeader;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.config.ApiProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertApiHelper {
    private static final Logger log = LoggerFactory.getLogger(ConvertApiHelper.class);

    @Autowired
    private ApiConfig apiConfig;
    /**
     * Chuyển đổi yêu cầu API bằng cách cập nhật header và sao chép body.
     *
     * @param req Yêu cầu API gốc cần chuyển đổi.
     * @param apiName Tên của API để lấy cấu hình.
     * @param LOCATION Vị trí mà quá trình chuyển đổi diễn ra (dùng cho log).
     * @return Một đối tượng ApiRequest mới với header và body đã được cập nhật.
     */
    public ApiRequest convertApi(ApiRequest req, String apiName, String LOCATION) {
        if (req == null) {
            return null;
        }


        // Tạo một đối tượng API request mới
        ApiRequest newApiReq = new ApiRequest();

        // Copy and update the header
        ApiHeader newHeader = req.getHeader();

        // Lấy cấu hình API và cập nhật header
        try{
            ApiProperties apiProperties = apiConfig.getApi(apiName);
            if (apiProperties != null) {
                log.info("found api {}",apiName);
                newHeader.setApi(apiProperties.getApiName());
                newHeader.setApiKey(apiProperties.getApiKey());
            } else {
                log.warn("No configuration found for apiName: {}", apiName);
                return null;
            }

            // Đặt header đã cập nhật vào yêu cầu API mới
            newApiReq.setHeader(newHeader);

            // Sao chép body từ yêu cầu gốc
            newApiReq.setBody(req.getBody());

            log.info("a = " + JsonAndObjectUtils.objectToJson(newApiReq));
            return newApiReq;
        }catch (Exception e) {
            log.error( apiName +" "+ LOCATION+":"+ e.getMessage());
            log.debug("Stack trace:", e);

            return null;
        }




    }
}
