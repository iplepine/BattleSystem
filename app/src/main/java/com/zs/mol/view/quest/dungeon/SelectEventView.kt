package com.zs.mol.view.quest.dungeon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.dungeon.SelectEvent
import com.zs.mol.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_quest.*

class SelectEventView : BaseFragment() {
    var viewModel: SelectEventViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quest, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init() {
        initViewModels()
    }

    private fun initViewModels() {
        viewModel = ViewModelProvider(requireActivity()).get(SelectEventViewModel::class.java)

        viewModel?.event?.observe(viewLifecycleOwner, Observer {
            updateEventView(it)
        })
    }

    private fun updateEventView(event: SelectEvent) {
        recyclerView?.apply {
        }
    }

    class SelectEventAdapter : RecyclerView.Adapter<SelectOptionViewHolder>() {
        var items = ArrayList<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectOptionViewHolder {
            return SelectOptionViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: SelectOptionViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    class SelectOptionViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(TextView(parent.context)) {
        var option = ""
        var optionView = itemView as TextView

        fun bind(option: String) {
            optionView.text = option
        }
    }
}