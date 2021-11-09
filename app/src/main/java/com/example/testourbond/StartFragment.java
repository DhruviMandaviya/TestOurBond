package com.example.testourbond;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private ImageView image;
    private TextView appName;
    private TextView startFeedBackText;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private static final String  Start_Tag="start_log";
    private NavController navController;
    private  static int SPLASH_SCREEN=5000;


    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start,container,false);

    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        firebaseAuth=FirebaseAuth.getInstance();

        navController= Navigation.findNavController(view);


        image=view.findViewById(R.id.image);
        appName=view.findViewById(R.id.App_name);
        progressBar=view.findViewById(R.id.progressBar);
        startFeedBackText=view.findViewById(R.id.text_view);

        appName.setScaleX(0);
        appName.setScaleY(0);

        appName.animate().scaleX(1).scaleY(1).setDuration(3000).start();
        startFeedBackText.setText("Checking User Account....");
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseUser CurrentUser=firebaseAuth.getCurrentUser();
        if(CurrentUser==null)
        {
            startFeedBackText.setText("Creating Account....");
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        startFeedBackText.setText("Account Created....");
                        navController.navigate(R.id.action_startFragment_to_listFragment);
                    }
                    else
                    {
                        Log.d(Start_Tag,"Start Log"+task.getException());
                    }
                }
            });
        }
        else
        {
            //navigate to home page
            startFeedBackText.setText("Logged In...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navController.navigate(R.id.action_startFragment_to_listFragment);
                }
            },SPLASH_SCREEN);
        }
    }
}
