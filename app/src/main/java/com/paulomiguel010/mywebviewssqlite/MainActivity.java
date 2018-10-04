package com.paulomiguel010.mywebviewssqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;


import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG= "MainActivity";
    private WebView webView;
    private EditText etName, etNumber, etAge, etGender, etNationality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        etNationality = findViewById(R.id.etNationality);


        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

        WebViewClient webViewClient= new WebViewClient();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);

    //    webView.loadUrl("http://developer.android.com");
    }

    public void saveContact(View view){
        MyContact contact = new MyContact(etName.getText().toString(), etNumber.getText().toString(),
                etAge.getText().toString(),etGender.getText().toString(),etNationality.getText().toString() );
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.saveNewContact(contact);
    }
//    public void displayContact(View view){
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        List<MyContact> contacts = databaseHelper.getContacts();
//
//        for(MyContact contact: contacts){
//            Log.d(TAG, "displayContact: " +contact.getName()  +" "+ contact.getNumber() +" "+ contact.getAge()
//                    +" "+ contact.getGender() +" "+ contact.getNationality());
//        }
//    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);


        String data = tvName.getText().toString();
        outState.putString("data", data);

        Log.d(TAG, "onSaveInstanceState: "+ tvName.getText().toString());
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        tvName.setText(savedInstance.getString("data"));
        Log.d(TAG, "onRestoreInstanceState: " + tvName.getText().toString());

    }
    public void doSomething(View view) throws NoSuchAlgorithmException {
        switch(view.getId()){
            case R.id.btnChangeText:
                String name = etName.getText().toString();
                tvName.setText(name);
                break;
            case R.id.btnGoToSecond:
                List<MyContact> personList = new ArrayList<>();
                personList.add(new MyContact( etName.getText().toString(), etNumber.getText().toString(),
                        etAge.getText().toString(),etGender.getText().toString(),etNationality.getText().toString()));
                personList.add(new MyContact(getMessageDigest(etName.toString()), getMessageDigest(etNumber.getText().toString()),getMessageDigest(etAge.toString()),getMessageDigest(etGender.toString()),getMessageDigest(etNationality.toString())));
                Intent intent = new Intent(this, SecondActivity.class);
                intent.setAction("goToSecond");
                intent.putParcelableArrayListExtra("person", (ArrayList<? extends Parcelable>) personList);
                startActivity(intent);
                break;

            case R.id.btnGoToSecondForResult:
                Intent forResultIntent = new Intent(this, SecondActivity.class);
                MyContact person = new MyContact(name.getText().toString(), etNumber.getText().toString() );
                forResultIntent.setAction("goToSecondResult");
                forResultIntent.putExtra("person", person);
                startActivityForResult(forResultIntent, 2);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode==2){
            String name = data.getStringExtra("personName");
            tvName.setText(name);
        }
    }

    private String getMessageDigest(String message) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.reset();
        messageDigest.update(message.getBytes(Charset.forName("UTF-8")));
        StringBuilder hexString = new StringBuilder();
        byte[] messageDigestArray = messageDigest.digest();
        for(int i = 0; i < messageDigestArray.length; i++){
            hexString.append(Integer.toHexString( 0xFF & messageDigestArray[i]));
        }
        return hexString.toString();
    }

}
