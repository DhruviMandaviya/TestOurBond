package com.example.testourbond;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {
    private QuizListAdapter adepter;
    private NavController navController;
    private int position;
    private ImageView detailImage;
    private TextView detailTitle,detailDifficulty,detailQuestion,detailsScore,detailDesc;
    private Button start_quiz;
    private String quizId;
    private long totalQuestions = 0;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String quizName;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        navController= Navigation.findNavController(view);
        position=DetailsFragmentArgs.fromBundle(getArguments()).getPosition();
        detailImage=view.findViewById(R.id.details_image);
        detailTitle=view.findViewById(R.id.details_title);
        detailsScore=view.findViewById(R.id.details_score_text);
        detailDifficulty=view.findViewById(R.id.details_difficulty_text);
        detailQuestion=view.findViewById(R.id.details_questions_text);
        detailDesc=view.findViewById(R.id.details_desc);
        start_quiz=view.findViewById(R.id.details_start_btn);

        start_quiz.setOnClickListener(this);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QuizListViewModel quizListViewModel = new ViewModelProvider(requireActivity()).get(QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(),new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                String imageUri=quizListModels.get(position).getImage();

                Glide.with(getContext())
                        .load(imageUri)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image)
                        .into(detailImage);

                detailTitle.setText(quizListModels.get(position).getName());
                detailDesc.setText(quizListModels.get(position).getDesc());
                detailDifficulty.setText(quizListModels.get(position).getLevel());
                detailQuestion.setText(quizListModels.get(position).getQuestions()+"");
                quizId=quizListModels.get(position).getQuiz_id();
                totalQuestions= quizListModels.get(position).getQuestions();
                quizId = quizListModels.get(position).getQuiz_id();
                quizName = quizListModels.get(position).getName();


                //Load Results Data
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_start_btn:
                DetailsFragmentDirections.ActionDetailsFragmentToQuizFragment action = DetailsFragmentDirections.actionDetailsFragmentToQuizFragment();
                action.setQuizid(quizId);
                action.setQuizName(quizName);
                action.setTotalQuestions(totalQuestions);
                navController.navigate(action);
                break;
        }
    }
}
