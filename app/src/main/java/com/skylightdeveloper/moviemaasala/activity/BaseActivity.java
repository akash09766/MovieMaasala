package com.skylightdeveloper.moviemaasala.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skylightdeveloper.moviemaasala.R;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();

    public void showShortToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public ProgressDialog getProgressDialog(String message) {
        ProgressDialog p = new ProgressDialog(this);
        p.setCancelable(false);
        p.setMessage(message);
        return p;
    }

    public ProgressDialog getProgressDialog() {
        ProgressDialog p = new ProgressDialog(this);
        p.setCancelable(false);
        p.setMessage(getResources().getString(R.string.loading_title));
        return p;
    }


    public void showShortSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void showIndifiniteSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setMaxLines(4);
        snackbar.show();
    }

    public void showLongSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showLongSnackBar(int viewId, String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(viewId), message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public void showLongSnackBar(String message,View view) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
    public void showLongSnackBarWhite(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.list_item_sub_title));
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyBoard(EditText mUserNameEdit) {
        InputMethodManager keyboard = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(mUserNameEdit, 0);
    }

    private void doRestart() {
        Intent restartIntent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        restartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(restartIntent);
    }
}
