package app.test.testapp4.app.ui.list;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.List;

import app.test.testapp4.R;
import app.test.testapp4.android.network.SafeAsyncTask;
import app.test.testapp4.app.core.domain.ShopVo;
import app.test.testapp4.app.core.service.ListService;

/**
 * Created by BIT on 2017-01-16.
 */

public class ListAsyncTask {
    private ListService listService = new ListService();
    static Context mMain;

    public static void init(Context main) {
        mMain = main;
    }

    public void start() {
        new ListAsync().execute(); // ListAsync 작동 (실제로 접속을 시킨다는 것)

    }

    /**
    * 서버로 부터 JSON형식의 LIST를 가져오는 Ajax 비동기 통신을 하는 AsyncTask 클래스
    */
    private class ListAsync extends SafeAsyncTask<List<ShopVo>> {
        @Override
        public List<ShopVo> call() throws Exception {
        /* 실제 네트워크 통신 코드 작성 */
        /* 통신은 내부적으로 쓰레드 기반의 비동기 통신 */
            List<ShopVo> list = listService.list();
            return list;
        }

//        Exception 발생시...
        @Override
        protected void onException(Exception e) throws RuntimeException {
//        super.onException(e);
            throw new RuntimeException(e);
        }

        @Override
        protected void onSuccess(List<ShopVo> list) throws Exception {
        /* 통신 결과를 처리 */
            String listText = "";
            for (ShopVo shopVo : list) {
                listText += shopVo.getNo() + "\n";
            }
            ((TextView) ((Activity) mMain).findViewById(R.id.textView)).setText(listText);
        }
    }
}
