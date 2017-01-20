package app.test.testapp4.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import app.test.testapp4.R;
import app.test.testapp4.app.core.service.ListService;
import app.test.testapp4.app.ui.list.ListAsyncTask;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        ListAsyncTask.init(this);
        ListService.init(this);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday", "email", "user_location", "user_hometown"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.d("data", object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, age_range, gender, locale, birthday, email, location, hometown");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("cancel", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Login Error", error.toString());
            }
        });

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListAsyncTask().start(); // ListAsync 작동을 위한 메서드 실행
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        Log.w("얻은 권한", AccessToken.getCurrentAccessToken().getPermissions().toString());
        Log.w("거부된 권한", AccessToken.getCurrentAccessToken().getDeclinedPermissions().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayShowCustomEnabled(true); // Custom Actionbar를 사용하겠다!!

//        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE); // inflater 얻어오기
//        View actionbar = layoutInflater.inflate(R.layout.custom_actionbar, null); // xml(설계도)의 view를 실제 view객체로 만드는 역할

        View actionbar = View.inflate(this, R.layout.custom_actionbar, null); // 위에 두줄을 한줄로 줄인것

        getSupportActionBar().setCustomView(actionbar); // 가져온 view를 화면에 표시

        ((Toolbar)actionbar.getParent()).setContentInsetsAbsolute(0, 0); // 액션바 위치 지정(좌우 여백을 없애기 위해서)

        return true;

    }
}
