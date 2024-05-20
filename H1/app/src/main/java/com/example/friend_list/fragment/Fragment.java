package com.example.friend_list.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.friend_list.R;

public class Fragment extends androidx.fragment.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用布局填充器（LayoutInflater）从XML布局文件（fragment1.xml）中创建一个View对象
        View view = inflater.inflate(R.layout.fragment1, container, false);
        // 返回创建的View对象作为该Fragment的根视图
        return view;
    }
}
