package com.example.dinesh.lab3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private int RC_SIGN_IN = 2;
    GoogleApiClient mGoogleApiClient;
    ImageView iv;

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(getApplicationContext(),News.class));
                }
            }
        };
        iv = (ImageView)findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
.enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
})
 .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
.build();
    }
    private void signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
     startActivityForResult(signInIntent, RC_SIGN_IN);
     }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
super.onActivityResult(requestCode, resultCode, data);

// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
 if (requestCode == RC_SIGN_IN) {
GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
if (result.isSuccess()) {
// Google Sign In was successful, authenticate with Firebase
GoogleSignInAccount account = result.getSignInAccount();
 firebaseAuthWithGoogle(account);
} else {
// Google Sign In failed, update UI appropriately
// ...
 }
}
}

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
mauth.signInWithCredential(credential)
.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {
if (task.isSuccessful()) {
 // Sign in success, update UI with the signed-in user's information
Log.d("Tag", "signInWithCredential:success");
FirebaseUser user = mauth.getCurrentUser();
//updateUI(user);
} else {
// If sign in fails, display a message to the user.
Log.w("Tag", "signInWithCredential:failure", task.getException());
Toast.makeText(MainActivity.this, "Authentication failed.",
Toast.LENGTH_SHORT).show();
//updateUI(null);
}

// ...
}
});
    }

    void clicked(View view)
    {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
