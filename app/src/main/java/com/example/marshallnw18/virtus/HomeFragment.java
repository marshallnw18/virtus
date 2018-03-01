package com.example.marshallnw18.virtus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseHelper mDatabaseHelper;
    private CircularProgressBar mProgressBar;
    private TextView tv_wilks, tv_squat, tv_bench, tv_deadlift, tv_total;
    private String wilksData, squatData, benchData, deadliftData, totalData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mDatabaseHelper = new DatabaseHelper(getActivity());

        //Circular Progress Bar Actions
        mProgressBar = (CircularProgressBar) view.findViewById(R.id.circularProgress);
        mProgressBar.setProgressWidth(30);
        mProgressBar.setProgressColor(getResources().getColor(R.color.colorPrimary));
        mProgressBar.showProgressText(false);

        GraphView graphTotal = (GraphView) view.findViewById(R.id.graph_wilks);

        tv_wilks = (TextView) view.findViewById(R.id.tv_wilks);
        tv_squat = (TextView) view.findViewById(R.id.tv_home_squatDisplay);
        tv_bench = (TextView) view.findViewById(R.id.tv_home_benchDisplay);
        tv_deadlift = (TextView) view.findViewById(R.id.tv_home_deadliftDisplay);
        tv_total = (TextView) view.findViewById(R.id.tv_home_totalDisplay);

        //Added to ensure app doesn't crash on first run

        checkDatabasePopulation();

        wilksData = mDatabaseHelper.populateWilksData(mDatabaseHelper);
        squatData = mDatabaseHelper.populateSquatData(mDatabaseHelper);
        benchData = mDatabaseHelper.populateBenchData(mDatabaseHelper);
        deadliftData = mDatabaseHelper.populateDeadliftData(mDatabaseHelper);

        double calcSquat = Double.parseDouble(squatData);
        double calcBench = Double.parseDouble(benchData);
        double calcDeadlift = Double.parseDouble(deadliftData);
        double calcTotal = calcBench + calcSquat + calcDeadlift;

        totalData = Double.toString(calcTotal);
        double wilksScore = round(Double.parseDouble(wilksData) * (calcTotal * 0.453592), 2);

        wilksProgressBar((int) wilksScore);

        //TODO: Idea: Make function returning an array of the most recent five lifts and their accompanying date/time's. Use those as the data points
        //Line Data for Wilks Progression
        LineGraphSeries<DataPoint> liftTotalSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        graphTotal.addSeries(liftTotalSeries);
        graphTotal.setTitle("Squat/Bench/Deadlift Progression");

        tv_squat.setText(squatData);
        tv_bench.setText(benchData);
        tv_deadlift.setText(deadliftData);
        tv_total.setText(totalData);
        tv_wilks.setText(Double.toString(wilksScore));

        return view;
    }

    public void wilksProgressBar(int wilksScore){
        int progressToNextStage = (wilksScore % 10) * 10;
        mProgressBar.setProgress(progressToNextStage);
    }

    //Rounding function found at: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void checkDatabasePopulation(){
        if(mDatabaseHelper.populateSquatData(mDatabaseHelper) != null && mDatabaseHelper.populateSquatData(mDatabaseHelper).isEmpty()){
            Log.d("HOME FRAGMENT", "SQLite Database Is Empty...Populating With Default Values");
            addDataLifts(0,0,0,0);
        }
    }

    public void addDataLifts (int squat, int bench, int deadlift, double wilks){

        boolean insertData = mDatabaseHelper.addDataLifts(squat,bench,deadlift, wilks);

        if(insertData = true){
            Log.d("User Fragment","Lift data successfully inserted");
        } else {
            Log.d("User Fragment","Lift data insertion failed");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
