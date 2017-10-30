package cn.ylapl.util.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Angle
 * Date: 2017/9/9
 * Time: 下午3:45
 */
class CustomerJsonSerializer {
    private ObjectMapper mapper = new ObjectMapper();
    private JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

    /**
     * @param clazz target type
     * @param include include fields
     * @param filter filter fields
     */
    private void filter(Class<?> clazz, String include, String filter) {
        if (clazz == null) {
            return;
        }
        if (!StringUtils.isEmpty(include)) {
            jacksonFilter.include(clazz, include.split(","));
        }
        if (!StringUtils.isEmpty(filter)) {
            jacksonFilter.filter(clazz, filter.split(","));
        }
        mapper.addMixIn(clazz, jacksonFilter.getClass());
    }

    String toJson(Object object) throws JsonProcessingException {
        mapper.setFilterProvider(jacksonFilter);
        return mapper.writeValueAsString(object);
    }
    void filter(JSON json) {
        this.filter(json.type(), json.include(), json.filter());
    }

    Object translate(Object object) throws JsonProcessingException {
        try {
            return mapper.readValue(toJson(object),object.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}