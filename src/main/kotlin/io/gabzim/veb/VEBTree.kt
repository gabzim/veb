package io.gabzim.veb

import java.lang.Integer.numberOfTrailingZeros
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log2
import kotlin.math.pow

/**
 * @param universeSize Int: Should be a power of 2 (No enforcement yet)
 */
class VEBTree(private val universeSize: Int) {

    companion object {
        const val NONE = -1
        const val MIN_UNIVERSE_SIZE = 2
    }

    private var summary = VEBTree(calculateSmallerUniverseSize(universeSize))
    private val clusters = Array<VEBTree?>(calculateSmallerUniverseSize(universeSize)) { null }
    private val highShift = numberOfTrailingZeros(lowerSquareRoot(universeSize))
    private val lowMask = lowerSquareRoot(universeSize) - 1


    var min: Int = NONE
    var max: Int = NONE

    fun insert(x: Int) {
        var numToInsert = x
        val i = high(x)

        if (min == NONE) {
            min = numToInsert
            max = numToInsert
            return
        } else if (numToInsert < min) {
            val oldMin = min
            min = numToInsert
            numToInsert = oldMin
        }

        if (numToInsert > max) {
            max = numToInsert
        }

        if (universeSize < MIN_UNIVERSE_SIZE) {
            return
        }

        if (clusters[i] == null) {
            clusters[i] = VEBTree(calculateSmallerUniverseSize(universeSize))
            summary.insert(i)
        }

        clusters[i]!!.insert(low(numToInsert))
    }

    fun successor(x: Int): Int {
        var i = high(x)
        var j: Int
        if (x < min) {
            return min
        }
        if (clusters[i] != null && low(x) < clusters[i]!!.max)
            j = clusters[i]!!.successor(low(x))
        else {
            i = summary.successor(high(x))
            if (i == NONE) return NONE
            j = clusters[i]!!.min
        }
        return index(i, j)
    }

    fun index(i: Int, j: Int) = i shl highShift or (j and lowMask)

    fun lowerSquareRoot(x: Int) = 2.0.pow(floor(log2(x.toDouble()) / 2.0)).toInt()

    fun high(x: Int) = x ushr highShift

    fun low(x: Int) = x and lowMask

    private fun calculateSmallerUniverseSize(currentUniverseSize: Int): Int {
        val bitsNeededForCurrentUniverse = log2(currentUniverseSize.toDouble())
        val bitsNeededForSmallerUniverse = ceil(bitsNeededForCurrentUniverse / 2)
        return (2.0).pow(bitsNeededForSmallerUniverse).toInt()
    }
}
