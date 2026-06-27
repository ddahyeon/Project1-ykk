package ykk.ykk_backend.common;

import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;

import java.util.Objects;

public class ExternalAPI {
    public static Float GetDollorPrice() {
        WebClient webClient = WebClient.create();

        String url = "https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=calculator&pkid=141&q=%ED%99%98%EC%9C%A8&where=m&u1=keb&u6=standardUnit&u7=0&u3=USD&u4=KRW&u8=down&u2=1";


        String value = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> {
                    for(JsonNode node: jsonNode.get("country")){
                        if ("원".equals(node.path("currencyUnit").asString())) {
                            return node.path("value").asString();
                        }
                    }
                    return null;
                }).block();
        return Float.parseFloat(Objects.requireNonNull(value).replace(",",""));
    }
}
