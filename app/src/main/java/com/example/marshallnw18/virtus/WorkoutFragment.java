package com.example.marshallnw18.virtus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marshallnw18.virtus.supportingClasses.Exercise;
import com.example.marshallnw18.virtus.supportingClasses.WeekOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutFragment extends Fragment {
    WorkoutDatabaseHelper myWorkoutDatabaseHelper;

    List<Exercise> exerciseListOne = new ArrayList<>();
    List<Exercise> exerciseListTwo = new ArrayList<>();
    List<Exercise> exerciseListThree = new ArrayList<>();
    List<Exercise> exerciseListFour = new ArrayList<>();
    List<Exercise> exerciseListFive = new ArrayList<>();
    List<Exercise> exerciseListSix = new ArrayList<>();

    private TextView mWorkoutText;
    private Button mNextWeekButton;
    private final String TAG = "WORKOUT FRAGMENT";

    private OnFragmentInteractionListener mListener;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        Log.d(TAG,"onCreate() method called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"onCreateView() method called");
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        mWorkoutText = (TextView) view.findViewById(R.id.workout_text);
        mNextWeekButton = (Button) view.findViewById(R.id.nextWeekButton);

        myWorkoutDatabaseHelper = new WorkoutDatabaseHelper(this.getActivity());
        myWorkoutDatabaseHelper.close();

        //TODO: Check for redundant code
        RecyclerView recyclerViewOne = (RecyclerView) view.findViewById(R.id.list_day_one);
        RecyclerView recyclerViewTwo = (RecyclerView) view.findViewById(R.id.list_day_two);
        RecyclerView recyclerViewThree = (RecyclerView) view.findViewById(R.id.list_day_three);
        RecyclerView recyclerViewFour = (RecyclerView) view.findViewById(R.id.list_day_four);
        RecyclerView recyclerViewFive = (RecyclerView) view.findViewById(R.id.list_day_five);
        RecyclerView recyclerViewSix = (RecyclerView) view.findViewById(R.id.list_day_six);

        LinearLayoutManager linearLayoutManagerOne = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerTwo = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerThree = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerFour = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerFive = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManagerSix = new LinearLayoutManager(getActivity());

        recyclerViewOne.setLayoutManager(linearLayoutManagerOne);
        recyclerViewTwo.setLayoutManager(linearLayoutManagerTwo);
        recyclerViewThree.setLayoutManager(linearLayoutManagerThree);
        recyclerViewFour.setLayoutManager(linearLayoutManagerFour);
        recyclerViewFive.setLayoutManager(linearLayoutManagerFive);
        recyclerViewSix.setLayoutManager(linearLayoutManagerSix);

        MyAdapter myAdapter = new MyAdapter(getActivity(),exerciseListOne);
        MyAdapter myAdapterTwo = new MyAdapter(getActivity(),exerciseListTwo);
        MyAdapter myAdapterThree = new MyAdapter(getActivity(),exerciseListThree);
        MyAdapter myAdapterFour = new MyAdapter(getActivity(),exerciseListFour);
        MyAdapter myAdapterFive = new MyAdapter(getActivity(),exerciseListFive);
        MyAdapter myAdapterSix = new MyAdapter(getActivity(),exerciseListSix);

        recyclerViewOne.setAdapter(myAdapter);
        recyclerViewTwo.setAdapter(myAdapterTwo);
        recyclerViewThree.setAdapter(myAdapterThree);
        recyclerViewFour.setAdapter(myAdapterFour);
        recyclerViewFive.setAdapter(myAdapterFive);
        recyclerViewSix.setAdapter(myAdapterSix);

        setWorkoutData();

        //Button actions to switch to next week
        mNextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Set up a map of possible week values and cycle through them
            }
        });

        return view;
    }

    //TODO: Create a FOR loop method. Iterate through # of cards on the page and dynamically populate them based on Week #
    private void setWorkoutData(){
        String[][] workoutWeek = workoutWeekSelection(mWorkoutText.getText().toString());

        for(int i = 0; i < 4; i++){
            exerciseListOne.add(new Exercise(workoutWeek[i][2], workoutWeek[i][3], workoutWeek[i][4], R.drawable.benchpress));
        }

        for(int i = 4; i < 8; i++){
            exerciseListTwo.add(new Exercise(workoutWeek[i][2], workoutWeek[i][3], workoutWeek[i][4], R.drawable.squats));
        }

        for(int i = 8; i < 12; i++){
            exerciseListThree.add(new Exercise(workoutWeek[i][2], workoutWeek[i][3], workoutWeek[i][4], R.drawable.squats));
        }

        for(int i = 12; i < 16; i++){
            exerciseListFour.add(new Exercise(workoutWeek[i][2], workoutWeek[i][3], workoutWeek[i][4], R.drawable.squats));
        }

        for(int i = 16; i < 20; i++){
            exerciseListFive.add(new Exercise(workoutWeek[i][2], workoutWeek[i][3], workoutWeek[i][4], R.drawable.squats));
        }

        for(int i = 20; i < 24; i++){
            exerciseListSix.add(new Exercise(workoutWeek[i][2], workoutWeek[i][3], workoutWeek[i][4], R.drawable.squats));
        }
    }

    public String[][] workoutWeekSelection(String displayedWeek){
        String[][] weekSelection;

        switch(displayedWeek){
            case "Week One":
                weekSelection = myWorkoutDatabaseHelper.pullWorkoutWeekOne(myWorkoutDatabaseHelper);
                Log.d(TAG,"Pulling week one data...");
                break;

            default:
                weekSelection = myWorkoutDatabaseHelper.pullWorkoutWeekOne(myWorkoutDatabaseHelper);
        }

        return weekSelection;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
