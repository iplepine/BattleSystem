package com.zs.mol.model.db.text

object TextDB : HashMap<Int, String>() {

    object Key {
        const val QUEST_ACCEPT = 110
        const val QUEST_DENY = 111
        const val QUEST_TITLE_HIRE = 112
        const val QUEST_DESC_HIRE = 113

        const val ERROR_QUEST_NOT_FOUND = -100
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
        put(Key.QUEST_ACCEPT, "수락")
        put(Key.QUEST_DENY, "거절")
        put(Key.QUEST_TITLE_HIRE, "나는 일하고 싶다!")
        put(Key.QUEST_DESC_HIRE, "지나가던 모험가가 당신과 함께하기를 원합니다.")

        put(Key.ERROR_QUEST_NOT_FOUND, "퀘스트 정보를 찾을 수 없습니다.")

    }
}