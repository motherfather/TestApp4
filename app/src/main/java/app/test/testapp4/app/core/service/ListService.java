package app.test.testapp4.app.core.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.List;

import app.test.testapp4.R;
import app.test.testapp4.android.network.JSONResult;
import app.test.testapp4.app.core.domain.AdminShopVo;
import app.test.testapp4.app.core.domain.SearchVo;
import app.test.testapp4.app.core.domain.ShopVo;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by BIT on 2017-01-16.
 */

public class ListService {
    static Context mMain;

    public static void init(Context main) { // MainActivity에서만 쓸 수 있는 findViewById를 쓰기 위해서 사용...
        mMain = main;
    }

    public List<ShopVo> list() {
        String url = "http://192.168.1.15:8088/modeal/shop/test"; // 접속한 주소
        HttpRequest httpRequest = HttpRequest.post(url); // 해당 주소로 접속 POST 방식
//        HttpRequest httpRequest = HttpRequest.get(url, true, "page", 3); // GET 방식

        httpRequest.contentType(HttpRequest.CONTENT_TYPE_JSON); // 전달 타입
        httpRequest.accept(HttpRequest.CONTENT_TYPE_JSON); // 받을 타입
        httpRequest.connectTimeout(3000); // 시간 경과시 보내기 끊기
        httpRequest.readTimeout(3000); // 시간 경과시 읽어오기 끊기

        Log.d("진짜", toJSON());
//        httpRequest.form(toJSON());

        int responseCode = httpRequest.code(); // 현재 연결상태에 따른 코드 불러오기 ex)접속성공시 200, 실패시 400, 404, 500 등...
        if (responseCode != HttpURLConnection.HTTP_OK) { // HTTP_OK 가 코드가 200
            throw new RuntimeException("Http Response : " + responseCode); // 연결 실패시 런타입익셉션
        }

        JSONResultList jsonResultList = fromJSON(httpRequest, JSONResultList.class); // JSONResultList타입의 JSON 데이터 httpReques를 객체로 변경

        List<ShopVo> list = jsonResultList.getData().getList(); // 서버에서 받은 데이터에서 리스트만 뽑아냄


        return list;
    }

    private class JSONResultList extends JSONResult<AdminShopVo> { // JSONResult<AdminShopVo>를 쓰기를 원하지만 fromJSON의 두번째 파라미터 Class<V> target에 제네릭타입의 클래스는 들어갈수 없으므로...
                                                                        // 이를 사용하기 위해서 같은 종류의 새로운 클래스 생성
//        내가 받아올 json의 모양대로 < > 안에 타입을 설정한다
//        ex) {"result":"success","message":null,"data":[{"id"="아이디", "password"="비밀번호"}, {"id"="아이디2", "password"="비밀번호2"}]}
//        UserVo가 id 와 password로 이루어졌다면 JSONResult< > 안은 List<UserVo> 라고 써야 한다!!!
    }

    // GSON 쓰기 위해서
    protected <V> V fromJSON(HttpRequest httpRequest, Class<V> target) { // httpRequest ← 서버에서 받아온 값, target ← 서버에서 받아온 값에서 필요한 값만 꺼내기 위해서... Class<V> ← 필요한 값의 형태?!
        V v = null;
        try {
            Gson gson = new GsonBuilder().create(); // GSON 생성

            Reader reader = httpRequest.bufferedReader(); // url에서 값 가져오기
            v = gson.fromJson(reader, target); // url에서 읽어온 json값 객체로 변경
            reader.close(); // reader 닫음

        } catch (Exception e) { // 어떤 Exception이라도 일어날 경우...
            throw new RuntimeException(e); // RuntimeException을 일으킨다
        }
        return v; // json에서 객체로 변경된 값 리턴
    }

    protected String toJSON() {
        String json = "";
        try {
            Gson gson = new GsonBuilder().create(); // GSON 생성

            int page = Integer.parseInt(((EditText) ((Activity) mMain).findViewById(R.id.editText)).getText().toString()); // MainActivity에서 EditText에 적힌 값(APP에서 내가 적은 값) 가져오기
            int filterCheck = Integer.parseInt(((EditText) ((Activity) mMain).findViewById(R.id.editText2)).getText().toString()); // MainActivity에서 EditText에 적힌 값(APP에서 내가 적은 값) 가져오기
            String keyword = ((EditText) ((Activity) mMain).findViewById(R.id.editText3)).getText().toString(); // MainActivity에서 EditText에 적힌 값(APP에서 내가 적은 값) 가져오기

            SearchVo searchVo = new SearchVo(); // MainActivity에서 EditText에 가져 온 값(APP에서 내가 적은 값)들을 Vo에 넣음
            searchVo.setPage(page);
            searchVo.setFilterCheck(filterCheck);
            searchVo.setKeyword(keyword);

            json = gson.toJson(searchVo); // 값이 들어간 Vo객체를 json으로 변환

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
