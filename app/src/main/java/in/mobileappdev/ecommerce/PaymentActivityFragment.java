package in.mobileappdev.ecommerce;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentActivityFragment extends Fragment implements View.OnClickListener{

    public PaymentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_payment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setOnClickListener(this);




        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
