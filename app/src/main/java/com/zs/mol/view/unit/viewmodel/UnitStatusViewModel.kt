package com.zs.mol.view.unit.viewmodel

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.stat.SecondStat
import com.zs.mol.model.unit.BattleUnit

class UnitStatusViewModel : ViewModel() {
    lateinit var unit: BattleUnit

    fun getName(): LiveData<String?> {
        return unit?.status?.nameLiveData
    }

    fun getLevelText(): String {
        return "Lv. ${unit.getLevel()}"
    }

    fun getExpText(): String {
        return if (unit.status.exp == 0L) {
            ""
        } else {
            "(${unit.status.exp})"
        }
    }

    fun getHpTex(): String {
        return "${unit.currentStat.getStat(SecondStat.HP).toInt()} / ${unit.totalStat.getStat(
            SecondStat.HP
        ).toInt()}"
    }

    fun getMpTex(): String {
        return "${unit.currentStat.getStat(SecondStat.MP).toInt()} / ${unit.totalStat.getStat(
            SecondStat.MP
        ).toInt()}"
    }

    fun getStatText(key: String): String {
        return unit.currentStat.getStat(key).toInt().toString()
    }

    fun getFaceImage(): String {
        return unit.status.faceImage
    }

    companion object {
        @BindingAdapter("setFaceImage")
        @JvmStatic
        fun setFaceImage(view: ImageView, faceImage: String) {
            val context = view.context.applicationContext
            val id = context.resources.getIdentifier(faceImage, "drawable", context.packageName)
            if (id <= 0) {
                //Logger.e("face image not found : $faceImage")
                Log.e("face image not found", "imageUrl: $faceImage")
            }
            view.setImageResource(id)
        }
    }
}