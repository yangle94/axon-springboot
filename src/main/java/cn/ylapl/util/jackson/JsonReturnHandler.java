package cn.ylapl.util.jackson;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Angle
 * Date: 2017/9/9
 * Time: 下午3:47
 * @author Angle
 */
@Component
public class JsonReturnHandler implements HandlerMethodReturnValueHandler {

    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_.]*");

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        // 如果有我们自定义的 JSON 注解 就用我们这个Handler 来处理
        return returnType.getMethodAnnotation(JSON.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);

        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String value = request.getParameter("cb");
        Annotation[] annos = returnType.getMethodAnnotations();
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();
        Arrays.asList(annos).forEach(a -> { // 解析注解，设置过滤条件
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                jsonSerializer.filter(json);
            } else if (a instanceof JSONS) {
                // 使用多重注解时，实际返回的是 @Repeatable(JSONS.class) 内指定的 @JSONS 注解
                JSONS jsons = (JSONS) a;
                Arrays.asList(jsons.value()).forEach(jsonSerializer::filter);
            }
        });
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (!StringUtils.isEmpty(value) && this.isValidJsonpQueryParam(value)) {
            MappingJacksonValue container = new MappingJacksonValue(jsonSerializer.translate(returnValue));
            Map<String, String> parameterMap = new HashMap<>(4);
            parameterMap.put("charset", "utf-8");
            ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
            outputMessage.getHeaders().setContentType(
                    new MediaType("application","javascript", parameterMap));
            container.setJsonpFunction(value);
            MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
            messageConverter.write(container,returnType.getGenericParameterType(),
                    new MediaType("application","json", parameterMap),outputMessage);
            return;
        }

        String json = jsonSerializer.toJson(returnValue);
        response.getWriter().write(json);
    }

    private boolean isValidJsonpQueryParam(String value) {
        return CALLBACK_PARAM_PATTERN.matcher(value).matches();
    }
}