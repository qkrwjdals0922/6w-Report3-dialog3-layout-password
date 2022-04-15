package com.pjm0922;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressDialog spinner_dialog;
    ProgressDialog horizontal_dialog;
    Handler handler = new Handler();
    int progress_value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Report3");
    }

    public void spinner_onclick(View view) {dialog_ex("spinner");}
    public void horizontal_onclick(View view) {dialog_ex("horizontal");}
    public void custom_onclick(View view) {dialog_ex("custom");}
    public void dialog_ex(String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch (type) {
            case "spinner":
                spinner_dialog = new ProgressDialog(MainActivity.this);
                spinner_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                spinner_dialog.setIcon(R.drawable.img2);
                spinner_dialog.setTitle("프로그래스 다이얼로그 \n (Spinner)");
                spinner_dialog.setMessage("현재 진행중입니다");
                spinner_dialog.setButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "취소하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                spinner_dialog.show();
                break;

            case "horizontal":
                progress_value = 0;
                horizontal_dialog = new ProgressDialog(MainActivity.this);
                horizontal_dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                horizontal_dialog.setIcon(R.drawable.img2);
                horizontal_dialog.setTitle("프로그래스 다이얼로그 \n (Horizontal)");
                horizontal_dialog.setMessage("잠시만 기다려주세요");
                horizontal_dialog.setMax(100);
                horizontal_dialog.setButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "취소하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                horizontal_dialog.show();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progress_value < 100) {
                            progress_value ++;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    horizontal_dialog.setProgress(progress_value);
                                    if (progress_value == 100) {
                                        Toast.makeText(MainActivity.this, "로딩이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                        horizontal_dialog.setMessage("로딩이 완료되었습니다");
                                    }
                                }
                            });
                            try {
                                Thread.sleep(25);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
                break;

            case "custom":
                Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.custom_dialog_layout);
                dialog.show();

                EditText edt_id = dialog.findViewById(R.id.EDT_id);
                EditText edt_pwd = dialog.findViewById(R.id.EDT_pwd);

                dialog.findViewById(R.id.login_BT).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str_id = edt_id.getText().toString();
                        String str_pwd = edt_pwd.getText().toString();
                        if ((str_id.equals("") || str_pwd.equals(""))){
                            Toast.makeText(MainActivity.this, "정확하게 입력해주세요", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, str_id + " / " + str_pwd, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.findViewById(R.id.BT1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.BT2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
}