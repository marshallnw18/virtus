package com.example.marshallnw18.virtus;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseHelper mDatabaseHelper;
    private WorkoutDatabaseHelper mWorkoutDatabaseHelper;
    private CircularProgressBar mProgressBar;

    private TextView tv_wilks, tv_squat, tv_bench, tv_deadlift, tv_total;
    private String wilksData, userWeightData, squatData, benchData, deadliftData, totalData;

    private String mParam1;
    private String mParam2;

    private double wilksScore;

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
        mWorkoutDatabaseHelper = new WorkoutDatabaseHelper(getActivity());

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
        userWeightData = mDatabaseHelper.populateUserWeight(mDatabaseHelper);
        squatData = mDatabaseHelper.populateSquatData(mDatabaseHelper);
        benchData = mDatabaseHelper.populateBenchData(mDatabaseHelper);
        deadliftData = mDatabaseHelper.populateDeadliftData(mDatabaseHelper);

        double calcSquat = Double.parseDouble(squatData);
        double calcBench = Double.parseDouble(benchData);
        double calcDeadlift = Double.parseDouble(deadliftData);
        double calcTotal = calcBench + calcSquat + calcDeadlift;

        totalData = Double.toString(calcTotal);
        wilksScore = round(Double.parseDouble(wilksData) * (calcTotal * 0.453592), 2);

        wilksProgressBar((int) wilksScore);

        //TODO: Idea: Make function returning an array of the most recent five lifts and their accompanying date/time's. Use those as the data points
        //Line Data for Wilks Progression
        LineGraphSeries<DataPoint> liftTotalSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 5)
        });

        /* Series are currently just testing data for formatting the UI */
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(1, 3),
                new DataPoint(2, 6),
                new DataPoint(3, 2),
                new DataPoint(4, 5)
        });
        graphTotal.addSeries(series2);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 2),
                new DataPoint(2, 3),
                new DataPoint(3, 4),
                new DataPoint(4, 2)
        });
        graphTotal.addSeries(series3);

       // LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);

        graphTotal.addSeries(liftTotalSeries);
        graphTotal.setTitle("Squat/Bench/Deadlift Progression");

        liftTotalSeries.setTitle("Squat");
        series2.setTitle("Bench");
        series3.setTitle("Deadlift");
        graphTotal.getLegendRenderer().setVisible(true);
        graphTotal.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphTotal.getLegendRenderer().setBackgroundColor(getResources().getColor(R.color.colorAccent));

        /* Setting colors for the line data */
        liftTotalSeries.setColor(getResources().getColor(R.color.colorPrimary));
        liftTotalSeries.setBackgroundColor(getResources().getColor(R.color.colorAccentTertiaryLight));
        liftTotalSeries.setThickness(10);
        liftTotalSeries.setDrawBackground(true);
        liftTotalSeries.setDrawDataPoints(true);

        series2.setColor(getResources().getColor(R.color.colorAccentSecondary));
        series2.setBackgroundColor(getResources().getColor(R.color.colorAccentSecondaryLight));
        series2.setThickness(4);
        series2.setDrawBackground(true);
        series2.setDrawDataPoints(true);

        series3.setColor(getResources().getColor(R.color.colorSecondary));
        series3.setBackgroundColor(getResources().getColor(R.color.colorSecondaryLight));
        series3.setThickness(4);
        series3.setDrawBackground(true);
        series3.setDrawDataPoints(true);

        tv_squat.setText(squatData);
        tv_bench.setText(benchData);
        tv_deadlift.setText(deadliftData);
        tv_total.setText(totalData);
        tv_wilks.setText(Double.toString(wilksScore));

        tv_wilks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.dialog);

                TextView dialogText = (TextView) dialog.findViewById(R.id.dialogTextView);
                try{
                    dialogText.setText("Your current lifter classification is: " + calculateLifterClassification((int)wilksScore, Integer.parseInt(userWeightData)));
                } catch (Exception e){
                    dialogText.setText("Input your information in the User page so we can calculate your Wilks score!");
                }

                /*  public String getClassification(Date ddMonth, double age, double weight, double bodyweight){
                 *
                 *  return classification;
                 *
                 * }
                 */

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);
                dialogButton.setBackgroundResource(R.drawable.dialogbuttonshape);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    public void wilksProgressBar(int wilksScore){
        int progressToNextStage = (wilksScore % 10) * 10;
        mProgressBar.setProgress(progressToNextStage);

        //TODO: Update progress increments based on AVG Wilks jumps: http://ironstrong.org/topic/1042-strength-standards-beginner-intermediate-advanced/
    }

    public String calculateLifterClassification(int wilksScore, int bodyweight){
        String lifterClassification = "";
        //Implemented Lifter Classification Method based on Strength Standards: http://ironstrong.org/topic/1042-strength-standards-beginner-intermediate-advanced/

        /* Classifies all individuals weighing below 114lbs (52kg) */
        if(bodyweight <= 114){
            if(wilksScore >= 116 && wilksScore <= 192){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 193 && wilksScore <= 226){
                lifterClassification = "Novice";
            } else if (wilksScore >= 227 && wilksScore <= 320){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 321 && wilksScore <= 415){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 416){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 114lbs (52kg) and 123lbs (56kg) */
        else if(bodyweight > 114 && bodyweight <= 123){
            if(wilksScore >= 116 && wilksScore <= 192){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 193 && wilksScore <= 229){
                lifterClassification = "Novice";
            } else if (wilksScore >= 230 && wilksScore <= 319){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 320 && wilksScore <= 414){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 415){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 123lbs (56kg and 132lbs (60kg) */
        else if(bodyweight > 123 && bodyweight <= 132){
            if(wilksScore >= 117 && wilksScore <= 194){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 195 && wilksScore <= 230){
                lifterClassification = "Novice";
            } else if (wilksScore >= 231 && wilksScore <= 320){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 321 && wilksScore <= 413){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 414){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 132lbs (60kg) and 148lbs (67kg) */
        else if(bodyweight > 132 && bodyweight <= 148){
            if(wilksScore >= 118 && wilksScore <= 196){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 197 && wilksScore <= 235){
                lifterClassification = "Novice";
            } else if (wilksScore >= 236 && wilksScore <= 325){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 326 && wilksScore <= 415){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 416){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 148lbs (67kg) and 165lbs (75kg) */
        else if(bodyweight > 148 && bodyweight <= 165){
            if(wilksScore >= 119 && wilksScore <= 197){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 198 && wilksScore <= 235){
                lifterClassification = "Novice";
            } else if (wilksScore >= 236 && wilksScore <= 325){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 326 && wilksScore <= 414){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 415){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 165lbs (75kg) and 181lbs (82kg) */
        else if(bodyweight > 165 && bodyweight <= 181){
            if(wilksScore >= 120 && wilksScore <= 200){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 201 && wilksScore <= 238){
                lifterClassification = "Novice";
            } else if (wilksScore >= 239 && wilksScore <= 328){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 329 && wilksScore <= 417){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 418){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 181lbs (82kg) and 198lbs (90kg) */
        else if(bodyweight > 181 && bodyweight <= 198){
            if(wilksScore >= 121 && wilksScore <= 200){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 201 && wilksScore <= 240){
                lifterClassification = "Novice";
            } else if (wilksScore >= 241 && wilksScore <= 328){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 329 && wilksScore <= 415){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 416){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 198lbs (90kg) and 220lbs (100kg) */
        else if(bodyweight > 198 && bodyweight <= 220){
            if(wilksScore >= 121 && wilksScore <= 201){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 202 && wilksScore <= 242){
                lifterClassification = "Novice";
            } else if (wilksScore >= 243 && wilksScore <= 329){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 330 && wilksScore <= 414){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 415){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 220lbs (100kg) and 242lbs (110kg) */
        else if(bodyweight > 220 && bodyweight <= 242){
            if(wilksScore >= 123 && wilksScore <= 203){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 204 && wilksScore <= 241){
                lifterClassification = "Novice";
            } else if (wilksScore >= 242 && wilksScore <= 328){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 329 && wilksScore <= 411){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 412){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        //TO DO FROM MAJ LASISI: WRITE A METHOD FOR THESE - ELIMINATE ALL REPEATED CODE

        /* Classifies all individuals weighing between 242lbs (110kg) and 275lbs (125kg) */
        else if(bodyweight > 242 && bodyweight <= 275){
            if(wilksScore >= 122 && wilksScore <= 202){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 203 && wilksScore <= 240){
                lifterClassification = "Novice";
            } else if (wilksScore >= 241 && wilksScore <= 325){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 326 && wilksScore <= 407){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 408){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing between 275lbs (125kg) and 319lbs (110kg) */
        //NOT UPDATED, come back to work on this
        else if(bodyweight > 275 && bodyweight <= 319){
            if(wilksScore >= 121 && wilksScore <= 201){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 202 && wilksScore <= 240){
                lifterClassification = "Novice";
            } else if (wilksScore >= 241 && wilksScore <= 323){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 324 && wilksScore <= 404){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 405){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        /* Classifies all individuals weighing more than 319lbs (145+kg) */
        else if(bodyweight > 319){
            if(wilksScore >= 124 && wilksScore <= 205){
                lifterClassification = "Untrained";
            } else if (wilksScore >= 206 && wilksScore <= 244){
                lifterClassification = "Novice";
            } else if (wilksScore >= 245 && wilksScore <= 329){
                lifterClassification = "Intermediate";
            } else if (wilksScore >= 330 && wilksScore <= 412){
                lifterClassification = "Advanced";
            } else if (wilksScore >= 413){
                lifterClassification = "Elite";
            } else {
                lifterClassification = "Unaccounted Lifter Classification";
            }
        }

        else {
            lifterClassification = "Unaccounted Lifter Classification";
        }

        return lifterClassification;
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
            Log.d("TAG","Lift data successfully inserted");
        } else {
            Log.d("TAG","Lift data insertion failed");
        }
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
