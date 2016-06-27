package com.carwale.aepl.assignment4;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.carwale.aepl.assignment4.Shakespeare;

/**
 * Created by aepl on 27/6/16.
 */
public class TitlesFragment extends ListFragment{
    boolean dualPane;
    int curentCheckIndex = 0;
    int currentShowingindex = -1;


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //Bundle bundle = getArguments();
        //dualPane = bundle.getBoolean("dual");

        View detailsFrame = getActivity().findViewById(R.id.details);
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        int layoutId;
        layoutId = android.R.layout.simple_list_item_activated_1;

        setListAdapter(new ArrayAdapter<String>(getActivity(), layoutId, Shakespeare.TITLES));

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
                fragment.setArguments(bundle);
                transaction.replace(R.id.details, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        }
        else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("index", curentCheckIndex);
            startActivity(intent);
        }

    }
}
