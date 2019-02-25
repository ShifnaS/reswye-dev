package com.nexgensm.reswye.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexgensm.reswye.R;
import com.seatgeek.placesautocomplete.PlacesApi;
import com.seatgeek.placesautocomplete.adapter.AbstractPlacesAutocompleteAdapter;
import com.seatgeek.placesautocomplete.history.AutocompleteHistoryManager;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.Place;

public class TestPlacesAutocompleteAdapter extends AbstractPlacesAutocompleteAdapter {
    public TestPlacesAutocompleteAdapter(@NonNull Context context, @NonNull PlacesApi api, @Nullable AutocompleteResultType autocompleteResultType, @Nullable AutocompleteHistoryManager history) {
        super(context, api, autocompleteResultType, history);
    }

    @Override
    protected View newView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.places_autocomplete_item, parent, false);
    }

    @Override
    protected void bindView(View view, Place item) {
        ((TextView) view).setText(item.description);

    }
}

