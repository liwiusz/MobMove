package mgr.mobmove.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import mgr.mobmove.R;
import mgr.mobmove.Settings;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ustawienia extends Fragment {


    public Ustawienia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_ustawienia, container, false);
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.liczenieLinearA);
        if (Settings.isLiczLinearAccleration())
        {
            checkBox.setChecked(true);
        }
        else checkBox.setChecked(false);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    Settings.setLiczLinearAccleration(true);
                }
                else Settings.setLiczLinearAccleration(false);
            }
            });

        return v;
    }

}
