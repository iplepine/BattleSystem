package com.zs.mol.view.adventure

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.MainActivity
import com.zs.mol.databinding.FragmentAdventureBinding
import com.zs.mol.view.adventure.viewmodel.AdventureViewModel
import com.zs.mol.view.adventure.viewmodel.GameSceneViewModel
import com.zs.mol.view.base.BaseFragment
import javax.inject.Inject


class AdventureFragment : BaseFragment() {
    lateinit var binding: FragmentAdventureBinding

    @Inject
    lateinit var gameSceneViewModel: GameSceneViewModel

    private var previousX = 0f
    private var previousY = 0f

    val viewModel: AdventureViewModel by lazy {
        ViewModelProvider(this).get(AdventureViewModel::class.java).apply {
            binding.viewModel = this
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("ttest", "onAttach")
        (requireActivity() as MainActivity).gameComponent.inject(this)
        //binding.surfaceView.gameViewModel = gameViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("ttest", "binding")
        binding = FragmentAdventureBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.surfaceView.gameSceneViewModel = gameSceneViewModel
        binding.surfaceView.setOnTouchListener { v, event ->
            Log.e("event x, y", "${event.rawX}, ${event.rawY}")
            if (event.action == MotionEvent.ACTION_DOWN) {
                previousX = event.rawX
                previousY = event.rawY
            }
            if (event.action == MotionEvent.ACTION_MOVE) {
                gameSceneViewModel.moveCamera(previousX-event.rawX, previousY-event.rawY)
                previousX = event.rawX
                previousY = event.rawY
            }
            true
        }
    }


}