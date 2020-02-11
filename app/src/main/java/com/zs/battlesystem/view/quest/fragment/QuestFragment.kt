package com.zs.battlesystem.view.quest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.battlesystem.R
import com.zs.battlesystem.view.base.BaseFragment

class QuestFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quest, container, false)
    }
}