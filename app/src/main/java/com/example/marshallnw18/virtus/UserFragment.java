package com.example.marshallnw18.virtus;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    SharedPreferences sharedPreferences;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseHelper mDatabaseHelper;
    private Button UpdateSettings;
    private EditText editHeight, editWeight, editAge, editSquat, editBench, editDeadlift;
    private TextView tvTDEE, tvCarbs, tvProteins, tvFats;

    private String TDEEdata, carbData, proteinData, fatsData;
    private String gender, activityLevel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        this.getRetainInstance();

        /* Instantiating DBHelper for this Fragment's Context */
        mDatabaseHelper = new DatabaseHelper(getActivity());

        /* Assigning Button view to a variable */
        UpdateSettings = view.findViewById(R.id.UpdateSettings);

        /* Assigning EditText views to variables */
        editHeight = view.findViewById(R.id.editHeight);
        editWeight = view.findViewById(R.id.editWeight);
        editAge = view.findViewById(R.id.editAge);
        editBench = view.findViewById(R.id.editBench);
        editSquat = view.findViewById(R.id.editSquat);
        editDeadlift = view.findViewById(R.id.editDeadlift);

        /* Assigning TextView views to variables */
        tvTDEE = view.findViewById(R.id.tv_tdee);
        tvProteins = view.findViewById(R.id.tv_proteins);
        tvCarbs = view.findViewById(R.id.tv_carbs);
        tvFats = view.findViewById(R.id.tv_fats);

        /* Assigning individual data to it's appropriate Database reference */
        TDEEdata = mDatabaseHelper.populateTDEEData(mDatabaseHelper);
        carbData = mDatabaseHelper.populateCarbData(mDatabaseHelper);
        fatsData = mDatabaseHelper.populateFatsData(mDatabaseHelper);
        proteinData = mDatabaseHelper.populateProteinData(mDatabaseHelper);

        /* Populating TextViews with their data on View creation */
        tvTDEE.setText(TDEEdata);
        tvCarbs.setText(carbData + "g");
        tvFats.setText(fatsData + "g");
        tvProteins.setText(proteinData + "g");


        //Creating Gender Spinner for gender selection
        final Spinner genderSpinner = (Spinner) view.findViewById(R.id.genderspinner);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        //Creating spinner for User Activity Levels
        final Spinner activitySpinner = (Spinner) view.findViewById(R.id.activityspinner);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.activity_options, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);

        //TODO: Add prompt if any EditText Views are null when UpdateSettings is clicked
        //TODO: Toast/Prompt to confirm updated data

        /* Item Selected Listener for pulling data from Gender Spinner */
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderSpinner.getSelectedItem().toString();
                Log.d("Gender Spinner", "Gender: " + gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO Auto-generated method stub
            }
        });

        /* Item Selected Listener for pulling data from Activity Level Spinner */
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityLevel = activitySpinner.getSelectedItem().toString();
                Log.d("Activity Level", "Activity Level: " + activityLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO Auto-generated method stub
            }
        });

        /* Sets the text of the EditText views with strings that show the numbers last entered by the user; Default values are 0 */
        editAge.setText(sharedPreferences.getString("userAge", "18"));
        editWeight.setText(sharedPreferences.getString("userWeight", "180"));
        editHeight.setText(sharedPreferences.getString("userHeight", "70"));
        editSquat.setText(sharedPreferences.getString("userSquat", "0"));
        editBench.setText(sharedPreferences.getString("userBench", "0"));
        editDeadlift.setText(sharedPreferences.getString("userDeadlift", "0"));

        /* Sets the Spinner values with the most recently updated selections from the user; Defaults to the first value on the list (index = 0) */
        genderSpinner.setSelection(sharedPreferences.getInt("userGender", 0));
        activitySpinner.setSelection(sharedPreferences.getInt("userActivityLevel", 0));

        //Button activity for updating settings
        UpdateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Instantiating an editor to allow for changses to user's SharedPreferences when they change data */
                SharedPreferences.Editor editor = sharedPreferences.edit();

                /* Pulling Strings from user inputs on the page. User entries are converted to Strings and then parsed for numerical values */
                //TODO: If any of these == null, pull from the most recent entry in the database as the entry. Then commit that last entry as the current one as well.
                int age = Integer.parseInt(editAge.getText().toString());
                int height = Integer.parseInt(editHeight.getText().toString());
                int weight = Integer.parseInt(editWeight.getText().toString());
                int squat = Integer.parseInt(editSquat.getText().toString());
                int bench = Integer.parseInt(editBench.getText().toString());
                int deadlift = Integer.parseInt(editDeadlift.getText().toString());

                double finalCarb, finalProtein, finalFat;
                double finalWilks;
                double bmr, tdee;

                if(gender.equals("Male")){
                    bmr = 66 + (6.23 * weight) + (12.7 * height) - (6.8 * age);
                } else {
                    bmr = 655 + (4.35 * weight) + (4.7 * height) - (4.7 * age);
                }

                if(activityLevel.equals("Sedentary: Little or no Exercise")){
                    tdee = bmr * 1.2;
                } else if (activityLevel.equals("Lightly Active: Exercise 1-3 days per week")){
                    tdee = bmr * 1.375;
                } else if (activityLevel.equals("Moderately Active: Exercise 3-5 days per week")){
                    tdee = bmr * 1.55;
                } else if (activityLevel.equals("Very Active: Exercise 6-7 days per week")){
                    tdee = bmr * 1.725;
                } else {
                    tdee = bmr * 1.9;
                }

                Log.d("tdee","Total Daily Energy Expenditure: " + tdee);

                finalWilks = calculateWilks((double)weight, gender);
                finalProtein = 0.9 * weight;
                finalFat = (0.25 * tdee) / 9;
                finalCarb = ((tdee
                            - ((finalProtein * 4) + (0.25 * tdee)))
                            / 4.0);

                Log.d("USER FRAGMENT", "Wilks: " + finalWilks);

                addDataUsers(height, weight, gender);
                addDataLifts(squat, bench, deadlift, finalWilks);
                addDataNutrition(round(finalCarb, 1), round(finalProtein, 1), round(finalFat,1), (int) tdee);


                TDEEdata = mDatabaseHelper.populateTDEEData(mDatabaseHelper);
                carbData = mDatabaseHelper.populateCarbData(mDatabaseHelper);
                fatsData = mDatabaseHelper.populateFatsData(mDatabaseHelper);
                proteinData = mDatabaseHelper.populateProteinData(mDatabaseHelper);

                /* Updated user input values are added to Shared Preferences */
                editor.putString("userAge", editAge.getText().toString());
                editor.putString("userHeight", editHeight.getText().toString());
                editor.putString("userWeight", editWeight.getText().toString());
                editor.putString("userSquat", editSquat.getText().toString());
                editor.putString("userBench", editBench.getText().toString());
                editor.putString("userDeadlift", editDeadlift.getText().toString());
                editor.putInt("userGender", genderSpinner.getSelectedItemPosition());
                editor.putInt("userActivityLevel", activitySpinner.getSelectedItemPosition());
                editor.commit();


                tvTDEE.setText(TDEEdata);
                tvCarbs.setText(carbData + "g");
                tvFats.setText(fatsData + "g");
                tvProteins.setText(proteinData + "g");
            }
        });

        //TODO: Override methods to set cursor to the end of EditTexts when clicked on

        return view;
    }

    //Public function that's used for rounding a double to x number of places past the decimal
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public void addDataUsers (int height, int weight, String gender){
        boolean insertData = mDatabaseHelper.addDataUsers(height,weight,gender);

        if(insertData = true){
            Log.d("User Fragment","User data successfully inserted");
        } else {
            Log.d("User Fragment","User data insertion failed");
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

    public void addDataNutrition (double carbs, double proteins, double fats, int tdee){

        boolean insertData = mDatabaseHelper.addDataNutrition(carbs,proteins,fats, tdee);

        if(insertData = true){
            Log.d("User Fragment","Nutrition data successfully inserted");
        } else {
            Log.d("User Fragment","Nutrition data insertion failed");
        }
    }

    public double calculateWilks(double lifterWeight, String lifterGender){
        double coeff;
        double a, b, c, d, e, f;

        if(lifterGender.equals("Male")){
            a = -216.0475144;
            b = 16.2606339;
            c = -0.002388645;
            d = -0.00113732;
            e = 7.01863e-06;
            f = -1.291e-08;
        } else {
            a = 594.31747775582;
            b = -27.23842536447;
            c = 0.82112226871;
            d = -0.00930733913;
            e = 4.731582e-05;
            f = -9.054e-08;
        }

        lifterWeight *= 0.453592;

        coeff = 500.0 /
                (a + (b * lifterWeight) +
                (c * (lifterWeight * lifterWeight)) +
                (d * (lifterWeight * lifterWeight * lifterWeight)) +
                (e * (lifterWeight * lifterWeight * lifterWeight * lifterWeight)) +
                (f * (lifterWeight * lifterWeight * lifterWeight * lifterWeight * lifterWeight)));

        Log.d("calculateWilks", "Weight: " + lifterWeight);
        Log.d("calculateWilks", "Gender: " + lifterGender);
        Log.d("calculateWilks", "Wilks Coeff: " + coeff);

        return coeff;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            Log.d("Lifecycle", "Activity Created");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "SaveInstance executed");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("Lifecycle", "onPause executed");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Lifecycle", "onResume executed");
    }
}
