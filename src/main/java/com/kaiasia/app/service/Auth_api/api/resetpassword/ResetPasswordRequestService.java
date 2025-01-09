package com.kaiasia.app.service.Auth_api.api.resetpassword;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.core.job.Enquiry;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import ms.apiclient.t24util.T24UtilClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@KaiService
public class ResetPasswordRequestService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GetErrorUtils apiErrorUtils;

    @Autowired
    private T24UtilClient t24UtilClient;

    @KaiMethod
    public ApiError validate(ApiRequest req) throws Exception {
        String LOCATION = "";

        return null;
    }
}
