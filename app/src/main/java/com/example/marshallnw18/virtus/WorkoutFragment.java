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
import android.widget.TextView;

import com.example.marshallnw18.virtus.supportingClasses.Exercise;
import com.example.marshallnw18.virtus.supportingClasses.WeekOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {
    WorkoutDatabaseHelper myWorkoutDatabaseHelper;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<Exercise> exerciseList = new ArrayList<>();
    private TextView mWorkoutText;
    private final String TAG = "WORKOUT FRAGMENT";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Friday: Links Day X to Loopable variable
    private Map<Integer, String> map = new HashMap<Integer, String>();

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */

    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        map.put(1, "Day 1");
        map.put(2, "Day 2");
        map.put(3, "Day 3");
        map.put(4, "Day 4");
        map.put(5, "Day 5");
        map.put(6, "Day 6");

        Log.d(TAG,"onCreate() method called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"onCreateView() method called");
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        mWorkoutText = (TextView) view.findViewById(R.id.workout_text);
        myWorkoutDatabaseHelper = new WorkoutDatabaseHelper(this.getActivity());
        myWorkoutDatabaseHelper.close();

        setInitialData();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        MyAdapter myAdapter = new MyAdapter(getActivity(),exerciseList);
        recyclerView.setAdapter(myAdapter);


        return view;
    }

    //TODO: Create a FOR loop method. Iterate through # of cards on the page and dynamically populate them based on Week #
    private void setInitialData(){
        String[][] week_one = myWorkoutDatabaseHelper.pullWorkoutWeekOne(myWorkoutDatabaseHelper);

        for(int i = 0; i < week_one.length; i++){
            exerciseList.add(new Exercise(week_one[i][2], week_one[i][3], R.drawable.benchpress));
        }

        exerciseList.add(new Exercise("Bench Press","5x5",R.drawable.benchpress));
        exerciseList.add(new Exercise("Squat","5x5",R.drawable.squats));
        exerciseList.add(new Exercise("Deadlift","5x5",R.drawable.deadlift));
        exerciseList.add(new Exercise("text3","text3",R.mipmap.ic_launcher));
        exerciseList.add(new Exercise("text3","text3",R.mipmap.ic_launcher));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
