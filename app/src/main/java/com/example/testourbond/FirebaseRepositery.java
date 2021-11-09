package com.example.testourbond;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class FirebaseRepositery {
    private OnFirebaseTaskComplete onFirebaseTaskComplete;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private CollectionReference QuizRef= (CollectionReference) firebaseFirestore.collection("QuizList");

    public FirebaseRepositery(OnFirebaseTaskComplete onFirebaseTaskComplete)
    {
        this.onFirebaseTaskComplete=onFirebaseTaskComplete;
    }

    public void getQuizData()
    {
        QuizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    onFirebaseTaskComplete.QuizListDataAdded(task.getResult().toObjects(QuizListModel.class));
                } else
                {
                    onFirebaseTaskComplete.isError(task.getException());
                }
            }
        });

    }

    public interface OnFirebaseTaskComplete
    {
        void QuizListDataAdded(List<QuizListModel> QuizListViewModel);
        void isError(Exception e);
    }
}
