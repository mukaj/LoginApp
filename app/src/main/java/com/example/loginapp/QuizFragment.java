package com.example.loginapp;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class QuizFragment extends Fragment implements VolleyListener {

    private JSONArray questions;
    private int score;
    private int index;

    private Button answer_a_button;
    private Button answer_b_button;
    private Button answer_c_button;
    private Button answer_d_button;
    private Button answer_e_button;
    private Button answer_f_button;

    private TextView question_text;
    private TextView score_text;

    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        fragment.score = 0;
        fragment.index = 0;
        return fragment;
    }

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Inflate the layout for this fragment
        answer_a_button = (Button) view.findViewById(R.id.answer_a);
        answer_a_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("answer_a");
            }
        });
        answer_b_button = (Button) view.findViewById(R.id.answer_b);
        answer_b_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("answer_b");
            }
        });
        answer_c_button = (Button) view.findViewById(R.id.answer_c);
        answer_c_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("answer_c");
            }
        });
        answer_d_button = (Button) view.findViewById(R.id.answer_d);
        answer_d_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("answer_d");
            }
        });
        answer_e_button = (Button) view.findViewById(R.id.answer_e);
        answer_e_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("answer_e");
            }
        });
        answer_f_button = (Button) view.findViewById(R.id.answer_f);
        answer_f_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("answer_f");
            }
        });

        question_text = (TextView) view.findViewById(R.id.question_text);
        score_text = (TextView) view.findViewById(R.id.ScoreText);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getQuestions(new VolleyListener() {
            @Override
            public void requestFinished(boolean existence) {
                nextQuestion(null);


            }
        });
    }

    private void nextQuestion(View vw) {
        if(questions.length() > index + 1)
            populateQuiz(index++);
        else
            return;
    }

    private void populateQuiz(int index) {

        try {
            JSONObject jsonObject = questions.getJSONObject(index);
            JSONObject answers = jsonObject.getJSONObject("answers");

            question_text.setText(jsonObject.getString("question"));

            answer_a_button.setText(answers.getString("answer_a"));
            answer_b_button.setText(answers.getString("answer_b"));
            answer_c_button.setText(answers.getString("answer_c"));
            answer_d_button.setText(answers.getString("answer_d"));
            answer_e_button.setText(answers.getString("answer_e"));
            answer_f_button.setText(answers.getString("answer_f"));


        }
        catch (Exception ex) {
            Log.e("Population", ex.getMessage());
        }

    }

    private void getQuestions(final VolleyListener callBack) {
        String url = "https://quizapi.io/api/v1/questions?apiKey=i48BhFoedMbwz2eMip9uVu6Yw5kph9ypE8BM14IL";

        Context test = getContext();

        RequestQueue queue = Volley.newRequestQueue(test);


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        questions = new JSONArray(response);

                        callBack.requestFinished(true);
                    }
                    catch (Exception ex) {
                        ex.getMessage();
                    }
                }, error -> {
                    error.getMessage();
                });

        queue.add(jsonObjectRequest);
        queue.start();
    }

    private void checkAnswer(String button_name) {
        try {
            if(questions.getJSONObject(index).getJSONObject("correct_answers").getString(button_name + "_correct").equals("true")){
                score_text.setText("Score: " + String.valueOf(++score));
            }

            nextQuestion(null);
        }
        catch (Exception ex) {
            ex.getMessage();
        }

    }

    @Override
    public void requestFinished(boolean existence) {

    }
}