package com.zs.mol.model.dungeon.generator

import org.junit.Test

class CellularAutoMataTest {
    @Test
    fun countNeighborTest() {
        val array =
            arrayOf(BooleanArray(3) { true }, BooleanArray(3) { true }, BooleanArray(3) { true })

        println(CellularAutoMata(10).countNeighbor(array, 0, 0))
    }
}
