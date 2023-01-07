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

        /**
         * 排序大法
         * 1.根据区间左边界从小到大排序，我们就只用得到的结果的右区间进行判断了，
         * 1.1如果某个待比较的区间的左边界大于当前区间的右边界，那说明两个区间没有重合，直接加到结果的最后面，
         *    同时因为我们根据区间左边界进行了排序，能保证后面所有的区间的左边界都不会小于新加进来的区间的左边界，
         *    所以把新加的区间当作比较基准继续和后面的区间比较就行了
         * 1.2如果某个待比较的区间的左边界小于等于当前区间的右边界，那说明两个区间有重合，这时候只需要修改当前区间的右边界为两个区间的最大值就行了
         *   （这就是排序的好处）
         **/
        fun merge(intervals: Array<IntArray>): Array<IntArray> {
            intervals.sortBy { it[0] }
            val ans = mutableListOf<IntArray>()

            var curArray: IntArray? = null
            for (interval in intervals) {
                if (curArray == null || curArray[1] < interval[0]) {
                    ans.add(interval)
                    curArray = interval
                } else {
                    curArray[1] = Math.max(interval[1], curArray[1])
                }
            }

            return ans.toTypedArray()
        }
    }

    class Solution119 {

        /**
         * 滚动数组
         * 从上到下遍历到需要获取的杨辉三角行，因为只需要上一行就能得到下一行的结果，所以可以滚动向下直到算出最后一行
         * 进阶：只使用一个数组的空间，倒叙遍历的话，能保证计算下一个数所需的其他数还是上一个
         **/
        fun getRow(rowIndex: Int): List<Int> {
            val result = ArrayList<Int>(rowIndex + 1)

            for (i in 0..rowIndex) {
                result.add(0)
                for (j in i downTo 0) {
                    if (j == 0 || j == i) {
                        result[j] = 1
                    } else {
                        result[j] = result[j - 1] + result[j]
                    }
                }
            }

            return result
        }
    }


    class Solution48 {

        /**
         *[[0,0],[1,0],[2,0],[3,0]]
         *[[0,1],[1,1],[2,1],[3,1]]
         *[[0,2],[1,2],[2,2],[3,2]]
         *[[0,3],[1,3],[2,3],[3,3]]
         */
        /**
         *[[0,3],[0,2],[0,1],[0,0]]
         *[[1,3],[1,2],[1,1],[1,0]]
         *[[2,3],[2,2],[2,1],[2,0]]
         *[[3,3],[3,2],[3,1],[3,0]]
         */
        /**
         * 找规律，没啥好说的，但是要注意坐标系，注释里用的是Android坐标系，但是题目是正常的数学坐标系，所以仅供参考
         */
        fun rotate1(matrix: Array<IntArray>) {
            val size = matrix.size
            val rotateMatrix = Array(size) { IntArray(size) }
            for (x in 0 until size) {
                for (y in 0 until size) {
                    rotateMatrix[x][y] = matrix[size - 1 - y][x]
                }
            }
            for (x in 0 until size) {
                for (y in 0 until size) {
                    matrix[x][y] = rotateMatrix[x][y]
                }
            }
        }

        /**
         * 由于是旋转1/4个圆(90/360°)，所以如果要进行原地旋转的话，那我们只用4项循环加一个临时变量就可以完成四个坐标的旋转
         * （因为正方形里只要旋转4次90°就能转回原样）
         * 剩下的就是如何判断需要开始旋转的起始坐标了，我们把正方形平均分为四份，取其中任何一份里面的坐标作为旋转起始坐标，
         * 再加上四次旋转就能原地旋转正方形了
         * 如果边长为偶数，那就是(size/2)*(size/2)
         * 如果边长为奇数，因为中间那一块不会旋转，所以就是把除了中间以外的其他块平分，我是分成了(n^2-1)/4 = ((size-1)/2)*((size+1)/2)
         */
        fun rotate2(matrix: Array<IntArray>) {
            val size = matrix.size
            for (x in 0 until size / 2) {
                for (y in 0 until (size + 1) / 2) {
                    val temp = matrix[x][y]
                    matrix[x][y] = matrix[size - 1 - y][x]
                    matrix[size - 1 - y][x] = matrix[size - 1 - x][size - 1 - y]
                    matrix[size - 1 - x][size - 1 - y] = matrix[y][size - 1 - x]
                    matrix[y][size - 1 - x] = temp
                }
            }
        }

        /**
         * 翻转代替旋转
         * 观察矩阵就能发现，只要先镜像翻转再对角线翻转就能达到顺时针旋转90°的效果
         */
        fun rotate3(matrix: Array<IntArray>) {
            val size = matrix.size
            for (x in 0 until size / 2) {
                for (y in 0 until size) {
                    val temp = matrix[x][y]
                    matrix[x][y] = matrix[size - 1 - x][y]
                    matrix[size - 1 - x][y] = temp
                }
            }

            for (x in 0 until size) {
                for (y in 0 until x) {
                    val temp = matrix[x][y]
                    matrix[x][y] = matrix[y][x]
                    matrix[y][x] = temp
                }
            }

        }


    }
}