package edu.cs570.lindiechenou.mealrater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.util.Log;

public class Rater extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    private RatingBar Bar;
    // private TextView txtRatingValue;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_rater, container, false);

        Bar = view.findViewById(R.id.ratingbar);
        btnSubmit = view.findViewById(R.id.savebutton);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");
                //float input = Bar.getRating();
                String rating = " " + Bar.getRating();
                ((MainActivity)getActivity()).transresult.setText(rating);

                getDialog().dismiss();
            }
        });
        return view;
    }
}