package com.example.testourbond;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class QuizListViewModel extends ViewModel implements FirebaseRepositery.OnFirebaseTaskComplete {
    private MutableLiveData<List<QuizListModel>>  QuizListModelData= new MutableLiveData<>();

    public LiveData<List<QuizListModel>> getQuizListModelData() {
        return QuizListModelData;
    }

    private FirebaseRepositery firebaseRepositery= new FirebaseRepositery( this);


    public QuizListViewModel()
    {
        firebaseRepositery.getQuizData();
    }
    @Override
    public void QuizListDataAdded(List<QuizListModel> QuizListViewModel) {
        QuizListModelData.setValue(QuizListViewModel);
    }

    @Override
    public void isError(Exception e) {

    }
}
