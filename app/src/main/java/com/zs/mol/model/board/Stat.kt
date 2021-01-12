package com.zs.mol.model.board

open class Stat(var hp: Int, var str: Int, var dex: Int, var int: Int, var holy: Int) {

}

class PercentStat(hp: Int, str: Int, dex: Int, int: Int, holy: Int) :
    Stat(hp, str, dex, int, holy) {
}