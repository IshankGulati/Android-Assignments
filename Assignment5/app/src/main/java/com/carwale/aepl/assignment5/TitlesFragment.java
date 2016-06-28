package com.carwale.aepl.assignment5;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by aepl on 27/6/16.
 */
public class TitlesFragment extends ListFragment{
    boolean dualPane;
    int curentCheckIndex = 0;
    int currentShowingindex = -1;
    ArrayList<State> states;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        states = (ArrayList<State>) bundle.getSerializable("States");
        //dualPane = bundle.getBoolean("dual");

        View detailsFrame = getActivity().findViewById(R.id.details);
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        int layoutId;
        layoutId = android.R.layout.simple_list_item_activated_1;
        ArrayList<String> stateNames= new ArrayList<>();
        for (State state : states){
            stateNames.add(state.stateName);
        }
        setListAdapter(new ArrayAdapter<String>(getActivity(), layoutId, stateNames));

        if(savedInstanceState != null){
            curentCheckIndex = savedInstanceState.getInt("curCheck", 0);
            currentShowingindex = savedInstanceState.getInt("curshow", -1);
        }

        if(dualPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(curentCheckIndex);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("curCheck", curentCheckIndex);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int index, long id){
        showDetails(index);
    }

    private void showDetails(int index){
        curentCheckIndex = index;

        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(dualPane){
            getListView().setItemChecked(index, true);

            if(curentCheckIndex != currentShowingindex){
                currentShowingindex = curentCheckIndex;
                DetailsFragment fragment = new DetailsFragment();
                Bundle bundle= new Bundle();
                bundle.putInt("index", curentCheckIndex);
                bundle.putSerializable("States", states);
                fragment.setArguments(bundle);
                transaction.replace(R.id.details, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        }
        else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("index", curentCheckIndex);
            intent.putExtra("States", states);
            startActivity(intent);
        }

    }
}
