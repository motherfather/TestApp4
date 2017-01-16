package app.test.testapp4.app.core.service;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import app.test.testapp4.android.network.JSONResult;
import app.test.testapp4.app.core.domain.AdminShopVo;
import app.test.testapp4.app.core.domain.ShopVo;

/**
 * Created by BIT on 2017-01-16.
 */

public class ListService {

    public List<ShopVo> list() {
        String url = "http://192.168.1.15:8088/modeal/shop/test"; // 접속한 주소
        HttpRequest httpRequest = HttpRequest.get(url); // 해당 주소로 접속 POST 방식
//        HttpRequest httpRequest = HttpRequest.get(url, true, "page", 3); // GET 방식

        httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON); // 전달 타입
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON); // 받을 타입
        httpRequest.connectTimeout(3000); // 시간 경과시 보내기 끊기
        httpRequest.readTimeout(3000); // 시간 경과시 읽어오기 끊기

        int responseCode = httpRequest.code(); // 현재 연결상태에 따른 코드 불러오기 ex)접속성공시 200, 실패시 400, 404, 500 등...
        if (responseCode != HttpURLConnection.HTTP_OK) { // HTTP_OK 가 코드가 200
            throw new RuntimeException("Http Response : " + responseCode); // 연결 실패시 런타입익셉션
        }

        JSONResultList jsonResultList = fromJSON(httpRequest, JSONResultList.class);

        List<ShopVo> list = (List<ShopVo>)jsonResultList.getData().getList();
        return list;
    }

    private class JSONResultList extends JSONResult<AdminShopVo> {
//        내가 받아올 json의 모양대로 < > 안에 타입을 설정한다
//        ex) {"result":"success","message":null,"data":[{"id"="아이디", "password"="비밀번호"}, {"id"="아이디2", "password"="비밀번호2"}]}
//        UserVo가 id 와 password로 이루어졌다면 JSONResult< > 안은 List<UserVo> 라고 써야 한다!!!
    }

    // GSON 쓰기 위해서
    protected <V> V fromJSON(HttpRequest httpRequest, Class<V> target) {
        V v = null;
        try {
            Gson gson = new GsonBuilder().create(); // GSON 생성

            Reader reader = httpRequest.bufferedReader(); // url에서 값 가져오기
            v = gson.fromJson(reader, target); // url에서 읽어온 json값 객체로 변경
            reader.close(); // reader 닫음

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return v;
    }
}
