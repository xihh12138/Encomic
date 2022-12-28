package com.xihh.encomic

import org.junit.Test

class LeetCode {


    class Solution169 {

        /**
         * 排序法，因为多数元素的个数一定大于数组长度/2，所以排序完直接取数组中间的数就行了
         */
        fun majorityElement1(nums: IntArray): Int {
            nums.sort()
            return nums[nums.size shr 1]
        }

        /**
         * 摩尔投票法
         *
         */
        fun majorityElement2(nums: IntArray): Int {
            var tempNum = nums[0]
            var count = 1
            for (i in 1 until nums.size) {
                if (tempNum == nums[i]) {
                    count++
                } else {
                    count--
                }
                if (count == 0) {
                    count = 1
                    tempNum = nums[i]
                }
            }

            return tempNum
        }
    }

    class Solution15 {
        /**
         * 双指针查找
         * 原理：
         * 1.先排序，如果数组长度小于3或者数组最小的数都大于0或者数组最大的数都小于0，那就直接返回空
         * 2.开始遍历排序后的数组，如果当前下标"i"的值大于0，说明数组里已经没有可以互相抵消的负值了，直接结束遍历
         * 3.确定一个不重复的"nums[i]"，开始用双指针left(x+1)..right(size-1)遍历数组。
         * 4.如果nums[i],nums[left],nums[right]加起来大于0，说明nums[right]大了，right左移
         *   如果nums[i],nums[left],nums[right]加起来小于0，说明nums[left]小了，left右移
         *   如果等于0，添加结果，并将right和left移到不为重复值的下标，继续遍历，直到left>=right
         * 要保证“不重复”，表示，取每个数的时候，同一个数字只取一次，例如：nums[i]只能等于一次2，nums[right]也只能等于一次2，但是他们可以同时等于二
         */
        fun threeSum(nums: IntArray): List<List<Int>> {
            nums.sort()
            if (nums.size < 3 || nums.first() > 0 || nums.last() < 0) {
                return emptyList()
            }
            val answer = mutableListOf<List<Int>>()

            val size = nums.size
            for (i in 0 until size) {
                val value = nums[i]
                if (value > 0) {
                    break
                }
                if (i > 0 && value == nums[i - 1]) {
                    continue
                }
                var left = i + 1
                var right = size - 1
                while (left < right) {
                    val sum = nums[i] + nums[left] + nums[right]
                    when {
                        sum > 0 -> right--
                        sum < 0 -> left++
                        else -> {
                            answer.add(listOf(nums[i], nums[left], nums[right]))
                            while (left < right && nums[left] == nums[left + 1]) {
                                left++
                            }
                            while (right > left && nums[right] == nums[right - 1]) {
                                right--
                            }
                            left++
                            right--
                        }
                    }
                }
            }
            return answer
        }
    }

    class Solution75 {
        /**
         * 计数排序，要排序的列表只有3种type，那就记住每种type出现的次数，再根据需要的排序结果赋值回原数组
         */
        fun sortColors1(nums: IntArray) {
            var red = 0
            var white = 0
            var blue = 0
            for (i in nums.indices) {
                when (nums[i]) {
                    0 -> red++
                    1 -> white++
                    2 -> blue++
                }
            }
            for (i in nums.indices) {
                when {
                    red > 0 -> {
                        nums[i] = 0
                        red--
                    }
                    white > 0 -> {
                        nums[i] = 1
                        white--
                    }
                    else -> nums[i] = 2
                }
            }
        }

        /**
         * 荷兰国旗问题
         * 1.设置 3 个指针，一个指向头部，一个指向尾部，还有一个指向当前遍历的元素。
         * 2.从头部开始遍历数组，如果遇到 0（红色）就把它放到头部指针的位置，如果遇到 2（蓝色）就把它放到尾部指针的位置。如果遇到 1（白色），就跳过它，继续遍历。
         */
        fun sortColors2(nums: IntArray) {
            var left = 0
            var i = 0
            var right = nums.size - 1

            while (i <= right) {
                if (nums[i] == 0) {
                    nums.swap(left, i)
                    left++
                } else if (nums[i] == 2) {
                    nums.swap(right, i)
                    right--
                    // ------------ 为什么要-1呢，因为换了之后i当前的值是未知的，需要再此确认，所以把指针往回移1位，下次循环就会重新判断 ------------
                    i--
                }
                i++
            }
        }

        private fun IntArray.swap(pos1: Int, pos2: Int) {
            val temp = this[pos1]
            this[pos1] = this[pos2]
            this[pos2] = temp
        }
    }

    @Test
    fun test56() {
        println(
            Solution56().merge(
                arrayOf(
                    intArrayOf(2, 3), intArrayOf(4, 5), intArrayOf(6, 7),
                    intArrayOf(8, 9), intArrayOf(1, 10)
                )
            ).contentDeepToString()
        )
    }

    class Solution56 {
        fun merge(intervals: Array<IntArray>): Array<IntArray> {
            intervals.sortBy { it[0] }
            val ans = mutableListOf<IntArray>()
            for (i in intervals.indices) {
                val interval = intervals[i]

                var hasMerged = false
                for (j in ans.indices) {
                    if (ans[j].hasIntersection(interval)) {
                        ans[j].merge(interval)
                        hasMerged = true
                    }
                }

                if (!hasMerged) {
                    ans.add(interval)
                }

            }
            return ans.toTypedArray()
        }

        private fun IntArray.hasIntersection(other: IntArray): Boolean {
            val smaller: IntArray
            val bigger: IntArray
            if (get(0) < other[0]) {
                smaller = this
                bigger = other
            } else {
                smaller = other
                bigger = this
            }

            return smaller[1] >= bigger[0]
        }

        private fun IntArray.merge(other: IntArray) {
            if (hasIntersection(other)) {
                set(0, Math.min(get(0), other[0]))
                set(1, Math.max(get(1), other[1]))
            }
        }
    }
}