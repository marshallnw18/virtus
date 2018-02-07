package com.example.marshallnw18.wilksv2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseHelper mDatabaseHelper;
    private Button UpdateSettings;
    private EditText editHeight, editWeight, editAge, editSquat, editBench, editDeadlift;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        UpdateSettings = view.findViewById(R.id.UpdateSettings);

        editHeight = view.findViewById(R.id.editHeight);
        editWeight = view.findViewById(R.id.editWeight);
        editAge = view.findViewById(R.id.editAge);
        editBench = view.findViewById(R.id.editBench);
        editSquat = view.findViewById(R.id.editSquat);
        editDeadlift = view.findViewById(R.id.editDeadlift);


        //Creating Gender Spinner for gender selection
        final Spinner genderSpinner = (Spinner) view.findViewById(R.id.genderspinner);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        //Creating spinner for Activity Levels
        final Spinner activitySpinner = (Spinner) view.findViewById(R.id.activityspinner);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activity_options, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);

        //Button activity for updating settings
        UpdateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age = editAge.getText().toString();
                String height = editHeight.getText().toString();
                String weight = editWeight.getText().toString();
                String squat = editSquat.getText().toString();
                String bench = editBench.getText().toString();
                String deadlift = editDeadlift.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                String activityLevel = activitySpinner.getSelectedItem().toString();

                int finalAge = Integer.parseInt(age);
                int finalHeight = Integer.parseInt(height);
                int finalWeight = Integer.parseInt(weight);
                int finalSquat = Integer.parseInt(squat);
                int finalBench = Integer.parseInt(bench);
                int finalDeadlift = Integer.parseInt(deadlift);
                int finalWilks;
                double bmr, tdee;

                if(gender == "Male"){
                    bmr = 66 + (13.7 * finalWeight) + (5 * finalHeight) - (6.8 * finalAge);
                } else {
                    bmr = 655 + (9.6 * finalWeight) + (1.8 * finalHeight) - (4.7 * finalAge);
                }
                System.out.println(gender + " \nBasal Metabolic Rate: " + bmr);

                if(activityLevel == "Sedentary: Little or no Exercise"){
                    tdee = bmr * 1.2;
                } else if (activityLevel == "Lightly Active: Exercise 1-3 days per week"){
                    tdee = bmr * 1.375;
                } else if (activityLevel == "Moderately Active: Exercise 3-5 days per week"){
                    tdee = bmr * 1.55;
                } else if (activityLevel == "Very Active: Exercise 6-7 days per week"){
                    tdee = bmr * 1.725;
                } else {
                    tdee = bmr * 1.9;
                }

                //TODO:Check for correctness
                System.out.println(activityLevel);
                System.out.println("Total Daily Energy Expenditure: " + tdee);

                finalWilks = calculateWilks(finalWeight, gender);

        //TODO: Macro calculations

                addData(finalHeight, finalWeight, gender, finalSquat, finalBench, finalDeadlift, finalWilks,
                        0,0,0, (int) tdee);
            }
        });

        return view;
    }

    //TODO: Make sure this works with data insertions
    public void addData (int height, int weight, String gender,
                            int squat, int bench, int deadlift, int wilks,
                            int carbs, int protein, int fats, int tdee){

        boolean insertData = mDatabaseHelper.addData(height,weight,gender,squat,bench,deadlift,wilks,carbs,protein,fats,tdee);

        if(insertData = true){
            System.out.println("Data successfully inserted");
        } else {
            System.out.println("Something went wrong");
        }
    }

    public int calculateWilks(int lifterWeight, String lifterGender){
        double coeff;
        double a, b, c, d, e, f;

        if(lifterGender == "Male"){
            a = -216.0475144;
            b = 16.2606339;
            c = -0.002388645;
            d = -0.00113732;
            e = 7.01863 * Math.pow(10, -6);
            f = -1.291 * Math.pow(10, -8);
        } else {
            a = 594.31747775582;
            b = -27.23842536447;
            c = 0.82112226871;
            d = -0.00930733913;
            e = 4.731582 * Math.pow(10, -5);
            f = -9.054 * Math.pow(10, -8);
        }

        //TODO: Check for correctness
        System.out.println(lifterGender);

        coeff = (500 / (a + (b*lifterWeight) + (c * Math.pow(lifterWeight,2)) + (d * Math.pow(lifterWeight,3)) +
                (e * Math.pow(lifterWeight,4) + (f * Math.pow(lifterWeight,5)))));

        System.out.println("Wilks Coeff: " + coeff);
        return (int) coeff;
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
