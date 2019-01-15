package com.nexgensm.reswye.ui.signinpage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nexgensm.reswye.R;
import com.nexgensm.reswye.data.db.model.Onboarding;
import com.nexgensm.reswye.ui.bottomtabbar.BottomTabbarActivity;
import com.nexgensm.reswye.ui.lead.LeadFindActivity;
import com.nexgensm.reswye.ui.signupactivity.AgentSignupActivity;
import com.nexgensm.reswye.ui.signupactivity.SignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SigninActivity extends AppCompatActivity {
    public String email;
    public String password, emailedittext, oldpasstxt, newpasstxt, confrmpasstxt;
    private KeyStore keyStore;
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private SigninActivity activity;
    TextView textView;
    public TextView SignIn;
    EditText oldpass, forgot_emailTxt, forgot_mobTxt;
    EditText newpass;
    EditText confrmpass;
    int firstTime;
    String emailForgot, mobForgot, loginfirsttime;
    Button forgotsendbtn;
    RequestQueue requestQueue, requestChangePassword;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    ProgressDialog pd;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        final EditText emailfield = (EditText) findViewById(R.id.Login_Emailid);
        final EditText passwordfield = (EditText) findViewById(R.id.Login_Password);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        final TextView login_forgot = (TextView) findViewById(R.id.login_forgot);
        requestQueue = Volley.newRequestQueue(this);
        requestChangePassword = Volley.newRequestQueue(this);
        login_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog forgot_pass_dialog = new Dialog(SigninActivity.this);
                forgot_pass_dialog.setContentView(R.layout.forgot_password_dialog);
                forgot_pass_dialog.setCanceledOnTouchOutside(true);

                String titleTextForgot = "Forgot Password";
                ForegroundColorSpan backgroundColorSpan = new ForegroundColorSpan(Color.BLACK);
                SpannableStringBuilder sBuilder = new SpannableStringBuilder(titleTextForgot);

                sBuilder.setSpan(
                        backgroundColorSpan,
                        0,
                        titleTextForgot.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                forgot_pass_dialog.setTitle(titleTextForgot);
                forgot_pass_dialog.show();
                forgot_emailTxt = (EditText) forgot_pass_dialog.findViewById(R.id.forgot_email);
                forgot_mobTxt = (EditText) forgot_pass_dialog.findViewById(R.id.forgot_mob);
                forgotsendbtn = (Button) forgot_pass_dialog.findViewById(R.id.Forgotsend);
                forgotsendbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emailForgot = forgot_emailTxt.getText().toString();
                        mobForgot = forgot_mobTxt.getText().toString();

////////////////////////////////////////////////////// FORGOT PASSWORD ////////////////////////////////////////////////////////////

                        String url = "http://192.168.0.4:88/api/UserRegistration/forgotpassword";
                        Map<String, Object> jsonParams = new ArrayMap<>();
                        jsonParams.put("Email", emailForgot);
                        jsonParams.put("Mobile", mobForgot);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String status = response.getString("status").toString().trim();
                                            String message = response.getString("message").toString().trim();
                                            String str3 = "Success";
                                            if (status.compareTo(str3) == 0) {
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                forgot_pass_dialog.dismiss();
                                            } else {
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                forgot_pass_dialog.dismiss();
                                            }
//                                            int result = re.compareTo(str3);
//                                            if (result == 0) {
//                                                //   loading.dismiss();
//                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//
//
//                                            } else {
//                                                //  loading.dismiss();
//                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                                                Intent in = new Intent(SigninActivity.this, SigninActivity.class);
//                                                startActivity(in);
//                                                finish();
//                                            }

                                        } catch (JSONException e) {
                                            //loading.dismiss();
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        requestQueue.add(request);


                    }
                });

            }
        });

        Button Signin = (Button) findViewById(R.id.Signin_Btm);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(SigninActivity.this);
                pd.setMessage("loading");
                pd.show();
                goToHome();
           /*     email = emailfield.getText().toString();
                emailedittext = emailfield.getText().toString().trim();
                password = passwordfield.getText().toString();
                if ((email.equals("")) || (password.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
//
                    if (emailedittext.length() > 0) {

                        if (emailedittext.matches(emailPattern)) {
/////////////////////////////////////////////// SIGN IN /////////////////////////////////////////////////////
                            String url = "http://202.88.239.14:8169/api/userregistration/UserLogin";
                            Map<String, Object> jsonParams = new ArrayMap<>();
                            jsonParams.put("Email_Id", email);
                            jsonParams.put("Password", password);

                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String status = response.getString("status").toString().trim();
                                                String message = response.getString("message").toString().trim();
                                                String str3 = "Success";
                                                if (status.compareTo(str3) == 0) {
                                                    pd.dismiss();

                                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                    int Userid = response.getInt("userid");
                                                    String token = response.getString("token");
                                                    String bearerToken="bearer "+token;
                                                    loginfirsttime = response.getString("islogineduser");
                                                    String Tokenexpiration = response.getString("tokenexpiration");
                                                    String imageUrl = "http://202.88.239.14:8169/FileUploads/";
                                                    sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putInt("UserId", Userid);
                                                    editor.putString("token", bearerToken);
                                                    editor.putString("imageURL", imageUrl);
                                                    editor.putString("Email",email);
                                                    editor.commit();

                                                    sharedpreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                                                    firstTime=sharedpreferences.getInt("firstTime",0);
                                                    if (firstTime == 0) {
                                                        final Dialog instructiondia = new Dialog(SigninActivity.this);
                                                        instructiondia.setContentView(R.layout.instructiondialog);
                                                        instructiondia.setCanceledOnTouchOutside(false);
                                                        instructiondia.show();
                                                        Button okins = (Button) instructiondia.findViewById(R.id.okinstruction);
                                                        okins.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                instructiondia.dismiss();
                                                                final Dialog dialog = new Dialog(SigninActivity.this);
                                                                dialog.setContentView(R.layout.password_change_dialog);
                                                                dialog.setCanceledOnTouchOutside(false);

                                                                String titleText = "Change Password";
                                                                ForegroundColorSpan backgroundColorSpan = new ForegroundColorSpan(Color.BLACK);
                                                                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                                                                ssBuilder.setSpan(
                                                                        backgroundColorSpan,
                                                                        0,
                                                                        titleText.length(),
                                                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                                                );
                                                                dialog.setTitle(ssBuilder);
                                                                dialog.show();

                                                                Button okbtn = (Button) dialog.findViewById(R.id.ok);
                                                                oldpass = (EditText) dialog.findViewById(R.id.oldpassword);
                                                                newpass = (EditText) dialog.findViewById(R.id.newpassword);
                                                                confrmpass = (EditText) dialog.findViewById(R.id.confirmpassword);

                                                                okbtn.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        oldpasstxt = oldpass.getText().toString();
                                                                        newpasstxt = newpass.getText().toString();
                                                                        confrmpasstxt = confrmpass.getText().toString();
                                                                        if ((oldpasstxt.equals("")) || (newpasstxt.equals("")) || (confrmpasstxt.equals(""))) {
                                                                            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                                                            return;
                                                                        } else {
                                                                            if (!(newpasstxt.equals(confrmpasstxt))) {
                                                                                Toast.makeText(getApplicationContext(), "New password and confirm password should be same", Toast.LENGTH_SHORT).show();
                                                                                return;
                                                                            } else {


//////////////////////////////////////////////// CHANGE PASSWORD --> FIRST TIME LOGIN ////////////////////////////////////////////

                                                                                //emailForgot = forgot_emailTxt.getText().toString();
                                                                                //  mobForgot = forgot_mobTxt.getText().toString();
                                                                                String url = "http://202.88.239.14:8169/api/userregistration/Changepassword";
                                                                                Map<String, Object> jsonParams = new ArrayMap<>();
                                                                                jsonParams.put("Email", email);
                                                                                jsonParams.put("Mobile",mobForgot);
                                                                                jsonParams.put("newpassword", newpasstxt);


                                                                                JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                                                                                        new Response.Listener<JSONObject>() {
                                                                                            @Override
                                                                                            public void onResponse(JSONObject response) {
                                                                                                try {
                                                                                                    String status = response.getString("status").toString().trim();
                                                                                                    String message = response.getString("message").toString().trim();
                                                                                                    String str3 = "Success";
                                                                                                    if (status.compareTo(str3) == 0) {
                                                                                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                                                                        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                                                                                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                                                                                        editor.putInt("firstTime", 1);
                                                                                                        editor.commit();
                                                                                                        Intent in = new Intent(SigninActivity.this, BottomTabbarActivity.class);
                                                                                                        startActivity(in);
                                                                                                        finish();
                                                                                                    } else {
                                                                                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                                                                    }
//                                            int result = re.compareTo(str3);
//                                            if (result == 0) {
//                                                //   loading.dismiss();
//                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//
//
//                                            } else {
//                                                //  loading.dismiss();
//                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                                                Intent in = new Intent(SigninActivity.this, SigninActivity.class);
//                                                startActivity(in);
//                                                finish();
//                                            }

                                                                                                } catch (JSONException e) {
                                                                                                    //loading.dismiss();
                                                                                                    e.printStackTrace();
                                                                                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                                                                                }

                                                                                            }
                                                                                        },
                                                                                        new Response.ErrorListener() {
                                                                                            @Override
                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                                requestChangePassword.add(request1);
//                                                                            }
//                                                                            else {
//                                                                                Intent in = new Intent(SigninActivity.this, Onboarding.class);
//                                                                                startActivity(in);
//                                                                            }

                                                                            }

                                                                        }
                                                                    }


                                                                });
                                                                Button cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                                                                cancel.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                      //  dialog.dismiss();
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                        Intent in = new Intent(SigninActivity.this, BottomTabbarActivity.class);
                                                        startActivity(in);
                                                        finish();
                                                    }

                                                }
                                                else {
                                                    pd.dismiss();
                                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                    Intent in = new Intent(SigninActivity.this, Onboarding.class);
                                                    startActivity(in);
                                                    finish();
                                                }
//                                            int result = re.compareTo(str3);
//                                            if (result == 0) {
//                                                //   loading.dismiss();
//                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//
//
//                                            } else {
//                                                //  loading.dismiss();
//                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                                                Intent in = new Intent(SigninActivity.this, SigninActivity.class);
//                                                startActivity(in);
//                                                finish();
//                                            }

                                            } catch (JSONException e) {
                                               pd.dismiss();
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            requestQueue.add(request);


                        } else {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                }*/
            }

        });
        TextView SignUp = (TextView) findViewById(R.id.Signup_link);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent in = new Intent(SigninActivity.this, SignupActivity.class);
                    startActivity(in);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            ImageView thumpimg = (ImageView) findViewById(R.id.thump_image);
            thumpimg.setImageResource(R.mipmap.thump_icon);
            // Initializing both Android Keyguard Manager and Fingerprint Manager
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            textView = (TextView) findViewById(R.id.errorText);

            if (!fingerprintManager.isHardwareDetected()) {
                /**
                 * An error message will be displayed if the device does not contain the fingerprint hardware.
                 * However if you plan to implement a default authentication method,
                 * you can redirect the user to a default authentication activity from here.
                 * Example:
                 * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                 * startActivity(intent);
                 */
                textView.setText("Your Device does not have a Fingerprint Sensor");
            } else {
                // Checks whether fingerprint permission is set on manifest
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    textView.setText("Fingerprint authentication permission not enabled");
                } else {
                    // Check whether at least one fingerprint is registered
                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        textView.setText("Register at least one fingerprint in Settings");
                    } else {
                        // Checks whether lock screen security is enabled or not
                        if (!keyguardManager.isKeyguardSecure()) {
                            textView.setText("Lock screen security not enabled in Settings");
                        } else {
                            generateKey();

                            if (cipherInit()) {
                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                FingerprintHandler helper = new FingerprintHandler(this);
                                helper.startAuth(fingerprintManager, cryptoObject);
                            }
                        }
                    }
                }
            }

        }
    }
    private void goToHome() {
        pd.dismiss();
        Intent i=new Intent(getApplicationContext(),BottomTabbarActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Signup_link:
                Intent signinactivity = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(signinactivity);
                break;
            default:
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

}