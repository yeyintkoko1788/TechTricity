package com.yeyintkoko.techtricity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yeyintkoko.techtricity.R;

import butterknife.ButterKnife;

public class AuthorFragment extends Fragment {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_author, container, false);
        //ButterKnife.bind(this, rootView);

        init();
        context = rootView.getContext();
        return rootView;
    }

    private void init(){

    }
}
