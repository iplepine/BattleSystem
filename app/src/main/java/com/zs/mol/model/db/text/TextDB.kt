package com.zs.mol.model.db.text

object TextDB : HashMap<Int, String>() {

    object Key {
        const val QUEST_TITLE_HIRE = 111
        const val QUEST_DESC_HIRE = 112
    }

    fun loadLocaleText(locale: String = "kr") {
        clear()
        when (locale) {
            "kr" -> loadKR()
        }
    }

    fun getText(key: Int): String {
        return get(key)?:""
    }

    fun loadKR() {
        put(Key.QUEST_TITLE_HIRE, "나는 일하고 싶다!")
        put(Key.QUEST_DESC_HIRE, "지나가던 모험가가 당신과 함께하기를 원합니다.")
    }
}