package com.zs.mol.view.dungeon

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.zs.mol.MainActivity
import com.zs.mol.databinding.FragmentDungeonBinding
import com.zs.mol.view.base.MainTabFragment
import kotlinx.android.synthetic.main.fragment_dungeon.*


class DungeonTabFragment : MainTabFragment() {
    lateinit var binding: FragmentDungeonBinding

    val viewModel: DungeonViewModel by lazy {
        ViewModelProvider(this).get(DungeonViewModel::class.java).apply {
            binding.viewModel = this
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDungeonBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).component.dungeonTabComponent().create().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.currentPosition.observe(viewLifecycleOwner, Observer { onChangePlace() })
        viewModel.enterDungeon()

        initDungeonMap()
    }

    private fun initDungeonMap() {
        viewModel.dungeon.observe(viewLifecycleOwner, Observer {
            dungeonMapView?.apply {
                adapter = DungeonMapAdapter(viewModel)
                addItemDecoration(SpacesItemDecoration(8))
            }
        })
    }

    private fun onChangePlace() {
        // 클릭이벤트 처리해야해
        dungeonMapView?.apply {
            adapter = DungeonMapAdapter(viewModel)
        }
    }

    class SpacesItemDecoration(private val mSpacing: Int) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = mSpacing
            outRect.top = mSpacing
            outRect.right = mSpacing
            outRect.bottom = mSpacing
        }
    }
}