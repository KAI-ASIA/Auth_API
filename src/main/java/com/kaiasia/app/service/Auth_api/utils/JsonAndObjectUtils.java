package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonAndObjectUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Chuyển đổi đối tượng thành chuỗi JSON.
     *
     * @param object Đối tượng cần chuyển đổi.
     * @return Chuỗi JSON tương ứng với đối tượng.
     * @throws JsonProcessingException Nếu có lỗi trong quá trình chuyển đổi.
     */
    public  String objectToJson(Object object) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Lỗi khi chuyển đối tượng thành JSON: {}", object, e);
            throw e; // Ném lại ngoại lệ để các lớp sử dụng biết và xử lý
        }
    }

    /**
     * Chuyển đổi chuỗi JSON thành đối tượng.
     *
     * @param json Chuỗi JSON cần chuyển đổi.
     * @param clazz Lớp mục tiêu mà bạn muốn chuyển đổi JSON thành.
     * @param <T> Kiểu dữ liệu của lớp mục tiêu.
     * @return Đối tượng của kiểu lớp mục tiêu.
     * @throws JsonProcessingException Nếu có lỗi trong quá trình chuyển đổi.
     */
    public  <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Lỗi khi chuyển JSON thành đối tượng: {}", json, e);
            throw e;
        }
    }

    /**
     * Chuyển đổi giữa các kiểu đối tượng (mapping giữa các kiểu dữ liệu khác nhau).
     *
     * @param object Đối tượng nguồn.
     * @param clazz Lớp mục tiêu mà bạn muốn chuyển đổi đối tượng thành.
     * @param <T> Kiểu dữ liệu của lớp mục tiêu.
     * @return Đối tượng của kiểu dữ liệu mục tiêu.
     */
    public  <T> T mapObjectToObject(Object object, Class<T> clazz) {
        try {
            return objectMapper.convertValue(object, clazz);
        } catch (IllegalArgumentException e) {
            log.error("Lỗi khi chuyển đổi giữa các kiểu đối tượng: {}", object, e);
            throw new RuntimeException("Lỗi khi chuyển đổi giữa các kiểu đối tượng", e);
        }
    }

    /**
     * Kiểm tra xem chuỗi JSON có hợp lệ hay không.
     *
     * @param json Chuỗi JSON cần kiểm tra.
     * @return True nếu chuỗi JSON hợp lệ, ngược lại false.
     */
    public  boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            log.warn("Chuỗi JSON không hợp lệ: {}", json, e);
            return false;
        }
    }

}
