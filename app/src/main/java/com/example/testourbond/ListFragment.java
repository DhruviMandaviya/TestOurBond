package com.example.testourbond;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import java.util.List;

import static com.example.testourbond.R.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements QuizListAdapter.OnQuizListItemClicked {
    private RecyclerView listView;
    private QuizListAdapter adepter;
    private ProgressBar ListProgress;
    private Animation fade_in,fade_out;
    private NavController navController;


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_list2,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        listView=view.findViewById(R.id.list_view);
        ListProgress= view.findViewById(id.List_progress);
        navController= Navigation.findNavController(view);

        fade_in= AnimationUtils.loadAnimation(getContext(),anim.fade_in);
        fade_out= AnimationUtils.loadAnimation(getContext(),anim.fade_out);
        adepter=new QuizListAdapter(this);


        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        listView.setAdapter(adepter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        QuizListViewModel quizListViewModel = new ViewModelProvider(requireActivity()).get(QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(),new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {

                listView.startAnimation(fade_in);
                ListProgress.startAnimation(fade_out);
                adepter.setQuizListModels(quizListModels);
                adepter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        ListFragmentDirections.ActionListFragmentToDetailsFragment action = ListFragmentDirections.actionListFragmentToDetailsFragment();
        action.setPosition(position);
        navController.navigate(action);
    }
}
