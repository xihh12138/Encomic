package com.xihh.encomic

import org.junit.Test
import java.util.*

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
        fun getRow1(rowIndex: Int): List<Int> {
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

        /**
         * 进阶版的优化
         **/
        fun getRow2(rowIndex: Int): List<Int> {
            val result = MutableList(rowIndex + 1) { 1 }
            for (i in 1..rowIndex) {
                for (j in (i - 1) downTo 1) {
                    result[j] = result[j] + result[j - 1]
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

    class Solution136 {
        /**
         * 笨办法，先排序然后挨个对比
         * 对比的时候隔一个对比一次就行了
         */
        fun singleNumber1(nums: IntArray): Int {
            if (nums.size == 1) {
                return nums[0]
            }

            nums.sort()

            val size = nums.size
            var i = 1
            while (i < size) {
                if (nums[i] != nums[i - 1]) {
                    return nums[i - 1]
                }
                i += 2
            }
            return nums.last()
        }

        /**
         * 聪明办法，通过异或的规律（1 xor 1 = 0;0 xor 1 = 1），把所有数挨个异或一遍，最后的结果就是只出现一次的那个数
         */
        fun singleNumber2(nums: IntArray): Int {
            var result = nums[0]
            for (i in 1 until nums.size) {
                result = result xor nums[i]
            }
            return result
        }
    }

    class Solution59 {
        /**
         * [1,2,3,4]
         * [12,13,14,5]
         * [11,16,15,6]
         * [10,9,8,7]
         *
         * [[0,0],[0,1],[0,2],[0,3]]
         * [[1,0],[1,1],[1,2],[1,3]]
         * [[2,0],[2,1],[2,2],[2,3]]
         * [[3,0],[3,1],[3,2],[3,3]]
         *
         * val side = size - 1
         * i==0  x==i --> y==side-i --> x==side-i --> y==i(x>i)
         * i==1(i<size/2)  x==i(y<=side-i) --> y==side-i(x<=side-i) --> x==side-i(y>=i) --> y==i(x>i)
         **/
        /**
         * 纯模拟，上面是推导过程
         **/
        fun generateMatrix(n: Int): Array<IntArray> {
            val array = Array(n) { x ->
                IntArray(n)
            }

            val side = n - 1
            var count = 0
            for (i in 0..(n + 1) / 2) {
                var y = i
                var x = i
                while (y <= side - i) {
                    array[x][y++] = ++count
                }
                y--
                while (++x <= side - i) {
                    array[x][y] = ++count
                }
                x--
                while (--y >= i) {
                    array[x][y] = ++count
                }
                y++
                while (--x > i) {
                    array[x][y] = ++count
                }
            }

            return array
        }
    }

    class Solution240 {
        /**
         * 最简单的想法，逐行遍历找到相等值，如果遇到大于[target]的就跳到下一行重头比较
         **/
        fun searchMatrix1(matrix: Array<IntArray>, target: Int): Boolean {
            val m = matrix.size
            val n = matrix[0].size
            for (i in 0 until m) {
                for (j in 0 until n) {
                    val value = matrix[i][j]
                    if (value == target) {
                        return true
                    } else if (value > target) {
                        break
                    }
                }
            }
            return false
        }

        fun searchMatrix2(matrix: Array<IntArray>, target: Int): Boolean {
            var rowNum = matrix.size
            var columnNum = matrix[0].size
            for (i in 0 until rowNum) {
                val lastNum = matrix[i][columnNum - 1]
                if (lastNum == target) {
                    return true
                } else if (lastNum < target) {
                    /**
                     * 本行最后一个数小于目标数(本行最大的数小于目标数，那本行肯定找不到目标数了)
                     **/
                    continue
                } else {
                    /**
                     * 本行最后一个数大于目标数，那答案可能在这行里。同时因为本行最后一个数是在最后一列里最小的数，
                     * 所以最后一列所有的数都大于目标数，那最后一列也可以不用考虑了
                     **/
                    columnNum--
                }
                /**
                 * 这个判断很重要，在减小列的范围之后要判断当前范围是否还足够继续循环下去
                 **/
                if (columnNum == 0) {
                    break
                }
                for (j in 0 until columnNum) {
                    val value = matrix[i][j]
                    if (value == target) {
                        return true
                    } else if (value > target) {
                        break
                    }
                }
            }
            return false
        }
    }

    class Solution435 {

        /**
         * 把区间当成时间段
         * 贪心算法，根据结束时间由小到大进行排序。
         * 遍历需要记录当前已经安排好的最晚的结束时间，默认直接把第一个时间段加进来，所以result最小是1，最晚结束时间初始值是最小的结束时间
         * 遍历时判断 如果当前时间段的开始时间大于等于当前最晚结束时间，那当前时间段和之前的时间段都不重合，可以把这个时间段加进来
         * 加进来的操作就是不重合的时间段个数+1，然后当前时间段的结束时间覆盖最晚结束时间，然后继续遍历
         * 最后的结果就是时间段个数剪掉最大不重合的时间段的个数
         **/
        fun eraseOverlapIntervals1(intervals: Array<IntArray>): Int {
            Arrays.sort(intervals) { o1: IntArray, o2: IntArray ->
                o1[1] - o2[1]
            }

            var noCoincidenceNum = 1
            var curEnd = intervals[0][1]

            for (i in 1 until intervals.size) {
                if (intervals[i][0] >= curEnd) {
                    noCoincidenceNum++
                    curEnd = intervals[i][1]
                }
            }

            return intervals.size - noCoincidenceNum
        }


        /**
         * 这是反过来。求需要删除的时间段的算法
         * 因为是按照结束时间从小到大排，所以后面的结束时间必大于等于当前结束时间，
         * 所以在遍历的时候，如果遇到时间冲突的，结果就加一，如果遇到不冲突的，那就说明不冲突的时间段可以更新了，就更新最晚结束时间
         * 因为我们要求的是最少的移除区间数量，所以只有在不冲突的时候才更新最晚结束时间，
         * 换句话说，人家已经冲突了，要把人家赶走（移除）了，你还算他的份(更新结束时间)干什么
         **/
        fun eraseOverlapIntervals2(intervals: Array<IntArray>): Int {
            Arrays.sort(intervals) { o1: IntArray, o2: IntArray ->
                o1[1] - o2[1]
            }

            var deleteNum = 0
            var curEnd = intervals[0][1]

            for (i in 1 until intervals.size) {
                if (intervals[i][0] < curEnd) {
                    deleteNum++
                } else {
                    curEnd = intervals[i][1]
                }
            }

            return deleteNum
        }
    }

    class Solution334 {

        /**
         * 贪心算法 题目思想是复制的
         * 赋初始值的时候，已经满足right > left了，现在找第三个数num
         * (1) 如果num比right大，那就是找到了，直接返回true
         * (2) 如果num比right小，但是比left大，那就把right指向num，然后继续遍历找num
         * (3) 如果num比left还小，那就把left指向num，然后继续遍历找num（这样的话left会跑到right的后边，但是不要紧，因为在right的前边，老left还是满足的）
         **/
        fun increasingTriplet1(nums: IntArray): Boolean {
            if (nums.size < 3) return false

            val size = nums.size
            // ------------ 赋初始值，让循环一开始先把left<right这个条件成立了 ------------
            var left = nums[0]
            var right = Int.MAX_VALUE
            var i = 1

            while (i < size) {
                val num = nums[i]
                if (num <= left) {
                    left = num
                } else if (num <= right) {
                    right = num
                } else {
                    // ------------ left<right这个条件不成立的话是进不了这里的 ------------
                    return true
                }
                i++
            }

            return false
        }

        /**
         * 双向遍历？
         * 用一个数组，存储任何一个数的“前i个数中的最小值”
         * 再用一个数组，存储任何一个数的“后i个数中的最大值”
         * 然后进行正式遍历的时候，判断前i个数的最小值+当前值nums[i]+后i个数的最大值是不是递增的序列就可以判断了
         **/
        fun increasingTriplet2(nums: IntArray): Boolean {
            val size = nums.size
            val leftNums = IntArray(size)
            val rightNums = IntArray(size)

            leftNums[0] = nums[0]
            for (i in 1 until size - 1) {
                leftNums[i] = Math.min(nums[i], leftNums[i - 1])
            }

            rightNums[size - 1] = nums[size - 1]
            for (i in size - 2 downTo 1) {
                rightNums[i] = Math.max(nums[i], rightNums[i + 1])
            }

            for (i in 1 until size - 1) {
                if (leftNums[i - 1] < nums[i] && rightNums[i + 1] > nums[i]) {
                    return true
                }
            }

            return false
        }
    }

    class Solution238 {

        /**
         * 和334的解法2一样，都是用额外的2n倍空间记录双向遍历的结果，然后再根据记录的值进行快速计算，属于是用空间换时间
         **/
        fun productExceptSelf1(nums: IntArray): IntArray {
            val size = nums.size
            val leftProduct = IntArray(size)
            val rightProduct = IntArray(size)

            leftProduct[0] = nums[0]
            for (i in 1 until size) {
                leftProduct[i] = nums[i] * leftProduct[i - 1]
            }

            rightProduct[size - 1] = nums[size - 1]
            for (i in size - 2 downTo 0) {
                rightProduct[i] = nums[i] * rightProduct[i + 1]
            }

            nums[0] = rightProduct[1]
            nums[size - 1] = leftProduct[size - 2]
            for (i in 1 until size - 1) {
                nums[i] = leftProduct[i - 1] * rightProduct[i + 1]
            }

            return nums
        }

        /**
         * 由于返回结果不算空间复杂度，所以利用返回结果数组先充当left数组，
         * 但是不同的是，这时候的left数组每个[left[i]]的值代表的是数组前i个数(不包括i)的乘积，
         * 到倒序遍历的时候，只需要一个数来记录到当前索引前的所有数的乘积(后i个数的乘积)，
         * 然后这个数(i)最终的值就是[left[i]](数组前i个数(不包括i)的乘积)乘上[right](数组后i+1个数的乘积),
         * 这么处理就少了长度为n的int数组空间
         **/
        fun productExceptSelf2(nums: IntArray): IntArray {
            val size = nums.size
            val ans = IntArray(size)

            ans[0] = 1
            for (i in 1 until size) {
                ans[i] = nums[i - 1] * ans[i - 1]
            }

            var right = nums[size - 1]
            for (i in size - 2 downTo 0) {
                ans[i] = ans[i] * right
                right *= nums[i]
            }

            return ans
        }


        /**
         * 动态规划？ 一次遍历同时处理数组两头
         * 每次循环都同时更新数组头i偏移的值和尾i偏移的值，然后头的指针慢慢往尾移，尾的指针往头移，最终每个索引都会被更新两次
         * 头指针[i]只负责更新前缀元素的乘积，计算到ans[i]的时候就用前i个元素的乘积[left]更新自己，这样操作不会把num[i]给乘上
         * 尾指针[size-1-i]只负责更新后缀元素的乘积，计算到ans[size-1-i]的时候就用后i个元素的乘积[right]更新自己，这样操作不会把num[i]给乘上，
         * 就算头指针大于尾指针之后，两个指针也是互不影响的，因为它们遍历的对象是nums数组，更新的对象是ans数组，两个指针影响的对象只有left和right以及ans
         * left和right两个值也是不会互相影响而且一直在更新的
         * 这种写法很妙，但是性能没啥提升而且难以理解，不过很值得学习
         **/
        fun productExceptSelf3(nums: IntArray): IntArray {
            val size = nums.size
            // ------------ 要更新结果数值初始值，这是这个算法的局限，更新为1表示乘积的基数为1 ------------
            val ans = IntArray(size) { 1 }

            var left = 1
            var right = 1

            for (i in 0 until size) {
                ans[i] *= left
                ans[size - 1 - i] *= right

                left *= nums[i]
                right *= nums[size - 1 - i]
            }

            return ans
        }
    }

    class Solution560 {

        /**
         * 暴力枚举法，对每个索引进行遍历，相当于循环往集合里添加元素，看添加新元素之后符合条件的连续子数组数量会不会增加
         **/
        fun subarraySum1(nums: IntArray, k: Int): Int {
            val size = nums.size

            var ans = 0
            for (i in 0 until size) {
                var sum = 0
                for (j in i downTo 0) {
                    sum += nums[j]
                    if (sum == k) {
                        ans++
                    }
                }
            }

            return ans
        }

        fun subarraySum2(nums: IntArray, k: Int): Int {
            return -1
        }

    }

    class Solution415 {
        fun addStrings(num1: String, num2: String): String {
            var i1 = num1.length - 1
            var i2 = num2.length - 1
            val ans = StringBuilder()
            var sum = 0

            while (i1 >= 0 || i2 >= 0 || sum != 0) {
                if (i1 >= 0) {
                    sum += (num1[i1--] - '0')
                }
                if (i2 >= 0) {
                    sum += (num2[i2--] - '0')
                }
                ans.append(sum % 10)
                sum /= 10
            }

            return ans.reverse().toString()
        }
    }

    class Solution409 {

        /**
         * 用额外的map记录字符出现次数的方法
         **/
        fun longestPalindrome(s: String): Int {
            val map = HashMap<Char, Int>()
            var ans = 0
            var odd = 0

            s.forEach {
                val added = map.getOrDefault(it, 0) + 1
                if (added < 2) {
                    map[it] = added
                    odd++
                } else {
                    map.remove(it)
                    ans += 2
                    odd--
                }
            }

            if (odd != 0) {
                ans++
            }

            return ans
        }
    }

    class Solution763 {

        /**
         * 最简单的算法，使用start和end指针动态维护当前的区间为满足要求的区间，保证两个指针之间的所有字母都在一个最大片段里
         * 这种方法是完全确定了一个区间的范围之后再去计算下一个区间的，所以不会有合并区间的逻辑
         * 时间复杂度：遍历字符串O(n)*挨个确定区间内字母的最远边界O(n)=O(n^2)
         **/
        fun partitionLabels1(s: String): List<Int> {
            val length = s.length
            val ans = mutableListOf<Int>()
            var start = 0
            var end: Int
            while (start < length) {
                val char = s[start]
                end = s.lastIndexOf(char)
                var j = start + 1
                while (j < end) {
                    val charMerge = s[j++]
                    end = Math.max(end, s.lastIndexOf(charMerge))
                }

                ans.add(end - start + 1)

                start = end + 1
            }

            return ans
        }


        fun partitionLabels(s: String): List<Int> {
            // ------------ 存储每个字母属于的片段编号，从1开始 ------------
            val partMap = IntArray(26)
            val ans = mutableListOf<Int>()
            var no = 1

            for (i in s.indices) {
                val char = s[i]
                val key = char - 'a'
                val oldPart = partMap[key]

                if (oldPart == 0) {
                    partMap[key] = no++
                    ans.add(i + 1)
                } else {
                    for (j in partMap.indices) {
                        val part = partMap[j]
                        if (part >= oldPart) {
                            // ------------ 之前出现的字母都合成一个区间 ------------
                        }

                    }
                }

            }
            return ans
        }
    }

    class Solution217 {
        fun containsDuplicate(nums: IntArray): Boolean {
            var i = 1
            val size = nums.size

            nums.sort()

            while (i < size) {
                if (nums[i] == nums[i - 1]) {
                    return true
                }

                i++
            }

            return false
        }
    }

    class Solution53 {

        /**
         * 动态规划经典问题，刚开始求出的子问题是：经过nums[i]的连续子数组的最大和是多少，
         * 但是这个子问题还有不确定的地方，因为不知道nums[i]是在这个连续子数组里的第几个元素（这叫有后效性），
         * 那我们重新定义子问题为 以nums[i]结尾的连续子数组的最大和是多少，这样子问题之间就有了关联，带着这个子问题去遍历一遍示例，
         * 就能发现子问题i的答案只和nums[i]以及子问题i-1的答案有关，依次类推就是答案的规律了，
         * 同时因为我们的的题目是要求最大的和，所以如果子问题i-1的答案是负数，那子问题i的答案就不能是子问题i-1的答案加上nums[i]，
         * 因为负数加上任何数都不会让结果变大，带着这个思想再去完善子问题的求解
         *
         *
         * 另外，什么是无后效性呢，李煜东著《算法竞赛进阶指南》：
         * 为了保证计算子问题能够按照顺序、不重复地进行，动态规划要求已经求解的子问题不受后续阶段的影响。这个条件也被叫做「无后效性」
         * 怎么解决「有后效性」的问题呢，力扣 作者liweiwei1419：
         * 解决「有后效性」的办法是固定住需要分类讨论的地方，记录下更多的结果。在代码层面上表现为：
         * 状态数组增加维度，例如：「力扣」的股票系列问题；
         * 把状态定义得更细致、准确，例如：前天推送的第 124 题：状态定义只解决路径来自左右子树的其中一个子树。
         **/
        fun maxSubArray1(nums: IntArray): Int {
            val dp = IntArray(nums.size)

            dp[0] = nums[0]

            for (i in 1 until nums.size) {
                val num = nums[i]
                if (dp[i - 1] < num) {
                    dp[i] = num
                } else {
                    dp[i] = dp[i - 1] + num
                }
            }

            var ans = Int.MIN_VALUE
            for (i in dp) {
                ans = Math.max(i, ans)
            }

            return ans
        }

        /**
         * 这是动态规划的空间优化版本，因为子问题i的答案只和子问题i-1以及nums[i]有关，所以只需要一个变量保存每一个i-1的子问题的答案即可
         **/
        fun maxSubArray2(nums: IntArray): Int {
            var ans = nums[0]
            var sum = nums[0]

            for (i in 1 until nums.size) {
                if (sum < 0) {
                    sum = nums[i]
                } else {
                    sum += nums[i]
                }
                ans = Math.max(sum, ans)
            }

            return ans
        }
    }

    class Solution1 {
        fun twoSum1(nums: IntArray, target: Int): IntArray {
            for (i in 1 until nums.size) {
                for (j in 0 until i) {
                    if (nums[i] + nums[j] == target) {
                        return intArrayOf(j, i)
                    }
                }
            }

            return intArrayOf()
        }

        fun twoSum2(nums: IntArray, target: Int): IntArray {
            val map = HashMap<Int, Int>()

            for (i in nums.indices) {
                val num = nums[i]

                val cha = target - num
                val cache = map[cha]
                if (cache == null) {
                    map[num] = i
                } else {
                    return intArrayOf(cache, i)
                }
            }

            return intArrayOf()
        }
    }

    class Solution88 {

        /**
         * 偷懒法
         **/
        fun merge1(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
            val size = nums1.size
            var writeIndex = m
            var readIndex = 0

            while (readIndex < n) {
                nums1[writeIndex] = nums2[readIndex]

                writeIndex++
                readIndex++
            }

            nums1.sort()
        }

        /**
         * 因为nums1后半部分是空的，而且大小就算整个nums2都合并进去都不会影响到原来的nums1，所以可以逆序进行排序，
         * 每次都取两个数组最大的元素放到nums1里,以此节省空间
         **/
        fun merge2(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
            val size = nums1.size
            var i1 = m - 1
            var i2 = n - 1
            var writeIndex = size - 1

            while (writeIndex >= 0) {
                nums1[writeIndex--] = if ((i1 >= 0 && i2 >= 0)) {
                    if (nums1[i1] > nums2[i2]) {
                        nums1[i1--]
                    } else {
                        nums2[i2--]
                    }
                } else {
                    if (i2 >= 0) {
                        nums2[i2--]
                    } else {
                        nums1[i1--]
                    }
                }
            }
        }
    }

    class Solution121 {

        /**
         * 动态规划，这里我定的子问题是：在第i天买，第j天卖，能获得的最大收益集合
         * dp[i][0]是前i天的最低价，dp[i][1]是在前i天最低价买入，第i天卖出的最大收益
         **/
        fun maxProfit1(prices: IntArray): Int {
            val dp = Array(prices.size) { IntArray(2) }

            dp[0][0] = prices[0]

            for (i in 1 until prices.size) {
                val price = prices[i]
                // ------------ 比利润都是跟最低价格比 ------------
                val profit = price - dp[i - 1][0]

                if (profit > dp[i - 1][1]) {
                    dp[i][0] = dp[i - 1][0]
                    dp[i][1] = profit
                } else {
                    // ------------ 利润没以前高的话就看看要不要更新最低价格 ------------
                    dp[i][0] = Math.min(price, dp[i - 1][0])
                    dp[i][1] = dp[i - 1][1]
                }
            }


            var ans = 0
            for (i in dp) {
                if (i[1] > ans) ans = i[1]
            }

            return ans
        }

        /**
         * 动态优化，1的优化空间版
         **/
        fun maxProfit2(prices: IntArray): Int {
            val dp = IntArray(prices.size)

            var minPrice = prices[0]

            for (i in 1 until prices.size) {
                val price = prices[i]
                // ------------ 比利润都是跟最低价格比 ------------
                val profit = price - minPrice

                if (profit > dp[i - 1]) {
                    dp[i] = profit
                } else {
                    // ------------ 利润没以前高的话就看看要不要更新最低价格 ------------
                    minPrice = Math.min(price, minPrice)
                    dp[i] = dp[i - 1]
                }
            }


            var ans = 0
            for (i in dp) {
                if (i > ans) ans = i
            }

            return ans
        }

        /**
         * 动态优化，再次优化空间版
         **/
        fun maxProfit3(prices: IntArray): Int {
            var minPrice = prices[0]
            var maxProfit = 0

            for (i in 1 until prices.size) {
                val price = prices[i]
                // ------------ 比利润都是跟最低价格比 ------------
                val profit = price - minPrice

                if (profit > maxProfit) {
                    maxProfit = profit
                } else {
                    // ------------ 利润没以前高的话就看看要不要更新最低价格 ------------
                    minPrice = Math.min(price, minPrice)
                }
            }

            return maxProfit
        }
    }

    class Solution122 {
        /**
         * 动态规划，这里我定的子问题是：在第i天卖，能获得的最大收益集合
         * 第i天卖能获得的最大收益和第i-1天的关系是：
         *      如果第i-1天的收益是正的，那第i天的最大收益就要和i-1天的收益挂上钩，否则不挂钩
         *      如果第i-1天的收益是正的，而且第i天的股价比第i-1天的高，那第i天的最大收益就是(i-1天收益)+(i天的股价)-(i-1天的股价)
         *      如果第i-1天的收益是正的，但是i的股价比i-1低，那第i天的最大收益就是i-1天的收益
         *      如果第i-1天的收益是负的，而且第i天的股价比第i-1天的高，那第i天的最大收益就是(i天的股价)-(i-1天的股价)
         *      如果第i-1天的收益是负的，但是i的股价比i-1低，那第i天的最大收益就是0
         **/
        fun maxProfit(prices: IntArray): Int {
            val dp = IntArray(prices.size)

            for (i in 1 until prices.size) {
                val price = prices[i]
                val dValue = price - prices[i - 1]

                dp[i] = if (dValue < 0) {
                    // ------------ 股价跌了 ------------
                    if (dp[i - 1] <= 0) {
                        0
                    } else {
                        dp[i - 1]
                    }
                } else {
                    // ------------ 股价涨了 ------------
                    if (dp[i - 1] <= 0) {
                        dValue
                    } else {
                        dp[i - 1] + dValue
                    }
                }
            }

            var ans = 0
            for (i in dp) {
                if (i > ans) ans = i
            }

            return ans
        }
    }

    class Solution350 {

        /**
         * 哈希表
         * 结果用了一个数组来存，同时用一个指针记录数组中有效的元素个数
         * 返回结果时只返回数组中有效的元素
         **/
        fun intersect1(nums1: IntArray, nums2: IntArray): IntArray {
            val map = HashMap<Int, Int>()

            val more: IntArray
            val less: IntArray

            if (nums1.size > nums2.size) {
                more = nums1
                less = nums2
            } else {
                more = nums2
                less = nums1
            }
            val ans = IntArray(less.size)
            var ansIndex = 0

            for (i in less) {
                map[i] = map.getOrDefault(i, 0) + 1
            }

            for (i in more) {
                map[i]?.let {
                    ans[ansIndex++] = i
                    if (it == 1) {
                        map.remove(i)
                    } else {
                        map[i] = it - 1
                    }
                }
            }

            return Arrays.copyOfRange(ans, 0, ansIndex)
        }

        /**
         * 双指针
         * 先排序，然后用两个指针从头同时遍历两个数组，如果nums1[i1]的元素小于nums2[i2]，那说明n1小了，也就是i1小了，
         * 所以i1向右移，以此类推
         **/
        fun intersect2(nums1: IntArray, nums2: IntArray): IntArray {
            var i1 = 0
            var i2 = 0

            nums1.sort()
            nums2.sort()

            val length1 = nums1.size
            val length2 = nums2.size

            val ans = IntArray(Math.min(length1, length2))
            var ansIndex = 0

            while (i1 < length1 && i2 < length2) {
                val n1 = nums1[i1]
                val n2 = nums2[i2]

                if (n1 < n2) {
                    i1++
                } else if (n1 > n2) {
                    i2++
                } else {
                    i1++
                    i2++
                    ans[ansIndex++] = n1
                }
            }

            return Arrays.copyOfRange(ans, 0, ansIndex)
        }
    }

    class Solution566 {
        fun matrixReshape(mat: Array<IntArray>, r: Int, c: Int): Array<IntArray> {
            val m = mat.size
            val n = mat[0].size

            if ((r * c != m * n)) {
                return mat
            }

            var index = 0
            return Array(r) { i ->
                IntArray(c) { j ->
                    mat.value(index++)
                }
            }
        }

        private fun Array<IntArray>.value(index: Int): Int {
            val n = get(0).size

            val row = index / n
            val column = index % n

            return get(row)[column]
        }
    }

    class Solution118 {
        fun generate(numRows: Int): List<List<Int>> {
            val ans = kotlin.collections.ArrayList<List<Int>>(numRows)

            for (i in 0 until numRows) {
                val row = ArrayList<Int>(i + 1).also {
                    for (j in 0..i) {
                        if (j == 0 || j == i) {
                            it.add(1)
                        } else {
                            it.add(ans[i - 1][j] + ans[i - 1][j - 1])
                        }
                    }
                }

                ans.add(row)
            }

            return ans
        }
    }

    class Solution36 {

        /**
         * 纯模拟题，这是用时间换空间的方法(只用一个额外9个数的空间记录值，但是需要遍历三次数独数组)，
         * 还有用空间换时间的方法就是用三个额外的9个数的空间记录1-9是否出现，在一次遍历里同时统计元素的出现
         **/
        fun isValidSudoku(board: Array<CharArray>): Boolean {
            val range = 0 until 9

            val count = BooleanArray(10)

            var ans = true

            for (i in range) {
                for (index in count.indices) {
                    count[index] = false
                }
                for (j in range) {
                    val char = board[i][j]
                    if (char == '.') continue
                    val index = char - '0'
                    if (count[index]) {
                        ans = false
                    } else {
                        count[index] = true
                    }
                }
            }

            if (ans) {
                for (i in range) {
                    for (index in count.indices) {
                        count[index] = false
                    }
                    for (j in range) {
                        val char = board[j][i]
                        if (char == '.') continue
                        val index = char - '0'
                        if (count[index]) {
                            ans = false
                        } else {
                            count[index] = true
                        }
                    }
                }
            }

            if (ans) {
                for (i in range) {
                    for (index in count.indices) {
                        count[index] = false
                    }
                    for (j in range) {
                        val char = board[(i / 3) * 3 + j / 3][(i * 3) % 9 + j % 3]
                        if (char == '.') continue
                        val index = char - '0'
                        if (count[index]) {
                            ans = false
                        } else {
                            count[index] = true
                        }
                    }
                }
            }

            return ans
        }
    }

    class Solution73 {

        /**
         * 一次遍历整个数组记录出现0的坐标，最后多次遍历替换
         **/
        fun setZeroes1(matrix: Array<IntArray>): Unit {
            val zeroCoordinates = Stack<Pair<Int, Int>>()

            val height = matrix.size
            val width = matrix[0].size

            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (matrix[i][j] == 0) {
                        zeroCoordinates.push(i to j)
                    }
                }
            }

            val size = zeroCoordinates.size

            var i = 0
            while (i++ < size) {
                setZeroes(matrix, zeroCoordinates.pop())
            }
        }

        private fun setZeroes(matrix: Array<IntArray>, coordinate: Pair<Int, Int>) {
            for (i in matrix.indices) {
                matrix[i][coordinate.second] = 0
            }

            for (i in matrix[coordinate.first].indices) {
                matrix[coordinate.first][i] = 0
            }
        }

        /**
         * 一次遍历整个数组记录出现0的行和列，最后一次遍历替换
         **/
        fun setZeroes2(matrix: Array<IntArray>): Unit {
            val height = matrix.size
            val width = matrix[0].size
            val zeroRow = BooleanArray(height)
            val zeroColumn = BooleanArray(width)

            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (matrix[i][j] == 0) {
                        zeroRow[i] = true
                        zeroColumn[j] = true
                    }
                }
            }

            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (zeroRow[i] || zeroColumn[j]) {
                        matrix[i][j] = 0
                    }
                }
            }
        }
    }

    class Solution387 {

        /**
         * 用哈希表储存每个字符的索引，如果在遍历字符串的时候多次写入了一个字符，那说明出现过两次，那就标记一下这个字符
         * 最后遍历哈希表，如果值不为-1(字符出现过一次以上的标记)，那就取尽可能小的索引值
         **/
        fun firstUniqChar1(s: String): Int {
            val map = HashMap<Char, Int>()

            for (i in 0 until s.length) {
                val char = s[i]
                if (map.contains(char)) {
                    map[char] = -1
                } else {
                    map[char] = i
                }
            }

            var ans = Int.MAX_VALUE

            map.forEach { t, u ->
                if (u != -1) {
                    ans = Math.min(u, ans)
                }
            }

            return if (ans == Int.MAX_VALUE) -1 else ans
        }

        /**
         * 从遍历哈希表改为遍历原字符串，好处就是只要遍历过程中发现有非-1的值，那就可以直接终止循环了
         **/
        fun firstUniqChar2(s: String): Int {
            val map = HashMap<Char, Int>()

            for (i in 0 until s.length) {
                val char = s[i]
                if (map.contains(char)) {
                    map[char] = -1
                } else {
                    map[char] = i
                }
            }

            var ans = Int.MAX_VALUE

            for (i in 0 until s.length) {
                val char = s[i]
                val value = map[char] ?: continue
                if (value != -1) {
                    ans = Math.min(value, ans)
                    break
                }
            }

            return if (ans == Int.MAX_VALUE) -1 else ans
        }
    }

    class Solution383 {
        fun canConstruct(ransomNote: String, magazine: String): Boolean {
            val map = IntArray(26)
            magazine.forEach {
                val key = it - 'a'
                map[key]++
            }

            ransomNote.forEach {
                val key = it - 'a'

                if (map[key]-- <= 0) {
                    return false
                }
            }

            return true
        }
    }

    class Solution242 {
        fun isAnagram(s: String, t: String): Boolean {
            val map = IntArray(26)

            s.forEach {
                val key = it - 'a'
                map[key]++
            }

            t.forEach {
                val key = it - 'a'
                map[key]--
            }

            map.forEach {
                if (it != 0) {
                    return false
                }
            }

            return true
        }
    }

    class Solution102 {

        /**
         * 其实这道题的难点就在于需要将值分层存储，其他的思路都和普通的层序遍历一样。
         * 这里判断层数使用了deep变量进行记录，插入节点值直接根据当前递归深度deep从ans获取当前层级的list
         **/
        fun levelOrder(root: TreeNode?): List<List<Int>> {
            val ans = arrayListOf<ArrayList<Int>>()

            if (root != null) {
                recursion(root, ans, 0)
            }

            return ans
        }

        private fun recursion(
            node: TreeNode,
            list: ArrayList<ArrayList<Int>>,
            deep: Int
        ) {
            val curList = if (list.size <= deep) {
                val nextList = ArrayList<Int>()
                list.add(nextList)
                nextList
            } else {
                list.get(deep)
            }

            curList.add(node.`val`)

            if (node.left != null) {
                recursion(node.left!!, list, deep + 1)
            }

            if (node.right != null) {
                recursion(node.right!!, list, deep + 1)
            }
        }
    }

    class Solution101 {
        fun isSymmetric(root: TreeNode?): Boolean {
            root ?: return true

            return isSymmetric(root.left, root.right)
        }

        private fun isSymmetric(left: TreeNode?, right: TreeNode?): Boolean {
            if (left == null && right == null) {
                return true
            }
            if (left == null || right == null) {
                return false
            }
            return left.`val` == right.`val`
                    && isSymmetric(left.right, right.left)
                    && isSymmetric(left.left, right.right)
        }

        fun isSymmetric2(root: TreeNode?): Boolean {
            root ?: return true

            return isSymmetric2(root.left, root.right)
        }

        private fun isSymmetric2(left: TreeNode?, right: TreeNode?): Boolean {
            val queue = LinkedList<TreeNode?>()

            var node1: TreeNode?
            var node2: TreeNode?
            queue.offer(left)
            queue.offer(right)

            while (!queue.isEmpty()) {
                node1 = queue.poll()
                node2 = queue.poll()

                if (node1 == null && node2 == null) {
                    continue
                }
                if (node1 == null || node2 == null) {
                    return false
                }
                if (node1.`val` != node2.`val`) {
                    return false
                }

                queue.offer(node1.left)
                queue.offer(node2.right)

                queue.offer(node1.right)
                queue.offer(node2.left)

            }

            return true
        }
    }

    class Solution226 {
        /**
         * 从上往下开始翻转
         **/
        fun invertTree(root: TreeNode?): TreeNode? {
            root ?: return root

            val temp = root.left
            root.left = root.right
            root.right = temp
            invertTree(root.left)
            invertTree(root.right)

            return root
        }

        /**
         * 从叶子节点开始翻转
         **/
        fun invertTree2(root: TreeNode?): TreeNode? {
            root ?: return root

            val left = invertTree(root.left)
            val right = invertTree(root.right)
            root.right = left
            root.left = right

            return root
        }
    }

    class Solution112 {
        /**
         * 递归，传递当前节点到父节点的累加值
         **/
        fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
            return hasPathSum(root, targetSum, 0)
        }

        private fun hasPathSum(node: TreeNode?, targetSum: Int, current: Int): Boolean {
            node ?: return false

            val new = current + node.`val`
            return if (node.left == null && node.right == null && new == targetSum) {
                true
            } else {
                hasPathSum(node.left, targetSum, new) || hasPathSum(node.right, targetSum, new)
            }
        }

        /**
         * 这样写相比于方法一少了一次递归调用
         * 方法一在子节点为空的时候还是会递归一次
         **/
        fun hasPathSum2(root: TreeNode?, targetSum: Int): Boolean {
            return hasPathSum2(root, targetSum, 0)
        }

        private fun hasPathSum2(node: TreeNode?, targetSum: Int, current: Int): Boolean {
            node ?: return false

            val new = current + node.`val`

            return if (node.left == null && node.right == null) {
                return new == targetSum
            } else {
                hasPathSum2(node.left, targetSum, new) || hasPathSum2(node.right, targetSum, new)
            }
        }

        /**
         * 用减法的话更妙，就不需要传多一个[targetSum]了
         **/
        fun hasPathSum3(root: TreeNode?, targetSum: Int): Boolean {
            root ?: return false

            val new = targetSum - root.`val`

            return if (root.left == null && root.right == null) {
                return new == 0
            } else {
                hasPathSum(root.left, new) || hasPathSum(root.right, new)
            }
        }
    }

    class Solution701 {
        fun insertIntoBST(root: TreeNode?, `val`: Int): TreeNode? {
            root ?: return TreeNode(`val`)

            if (root.`val` > `val`) {
                if (root.left != null) {
                    insertIntoBST(root.left, `val`)
                } else {
                    root.left = TreeNode(`val`)
                }
            } else {
                if (root.right != null) {
                    insertIntoBST(root.right, `val`)
                } else {
                    root.right = TreeNode(`val`)
                }
            }

            return root
        }

        fun insertIntoBST2(root: TreeNode?, `val`: Int): TreeNode? {
            var cur: TreeNode? = root ?: return TreeNode(`val`)

            while (cur != null) {
                if (cur.`val` > `val`) {
                    if (cur.left != null) {
                        cur = cur.left
                    } else {
                        cur.left = TreeNode(`val`)
                        break
                    }
                } else {
                    if (cur.right != null) {
                        cur = cur.right
                    } else {
                        cur.right = TreeNode(`val`)
                        break
                    }
                }
            }

            return root
        }
    }

    class Solution98 {
        fun isValidBST(root: TreeNode?): Boolean {
            return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE)
        }

        /**
         * 传递俩个基准值，[node]和它子节点的值不能大于[baseMax]，不能小于[baseMin]
         **/
        private fun isValidBST(node: TreeNode?, baseMin: Long, baseMax: Long): Boolean {
            node ?: return true

            if (node.`val` <= baseMin || node.`val` >= baseMax) {
                return false
            }

            return isValidBST(node.left, baseMin, node.`val`.toLong())
                    && isValidBST(node.right, node.`val`.toLong(), baseMax)
        }
    }

    class Solution653 {

        /**
         * 用哈希表存经过的所有节点的值，如果要找的值减当前节点的值在哈希表里存在，说明我们经过过，说明树里有这个两数之和
         **/
        fun findTarget(root: TreeNode?, k: Int): Boolean {
            val set = HashSet<Int>()
            val stack = Stack<TreeNode?>()
            stack.push(root)

            while (stack.isNotEmpty()) {
                val cur = stack.pop() ?: continue

                val dValue = k - cur.`val`

                if (set.contains(dValue)) {
                    return true
                } else {
                    set.add(cur.`val`)
                    if (dValue > cur.`val`) {
                        stack.push(cur.left)
                        stack.push(cur.right)
                    } else {
                        stack.push(cur.right)
                        stack.push(cur.left)
                    }
                }
            }

            return false
        }
    }

    class Solution235 {

        /**
         * 先遍历两次树，获取到根节点到p/q的全路径
         * 然后进行比对，因为起点一样都是root，如果所以不会没有公共祖先的情况
         * 如果两条路径的节点相同，说明它们就是p和q的公共父节点
         * 比对路径的时候，因为是正序（从上向下）遍历，所以下面的节点才是深度更深的（更接近p和q），所以不断更新最近祖先
         **/
        fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
            val stack = Stack<TreeNode?>()
            stack.push(root)

            val pathP = findPath(root, p!!)
            val pathQ = findPath(root, q!!)

            var cur: TreeNode? = null
            val length = Math.min(pathP.size, pathQ.size)

            for (i in 0 until length) {
                if (pathP[i] == pathQ[i]) {
                    cur = pathP[i]
                } else {
                    break
                }
            }

            return cur
        }

        private fun findPath(root: TreeNode?, target: TreeNode): List<TreeNode> {
            val ans = arrayListOf<TreeNode>()

            var cur: TreeNode? = root ?: return emptyList()

            while (cur!! != target) {
                ans.add(cur)

                cur = if (cur.`val` > target.`val`) {
                    cur.left
                } else {
                    cur.right
                }
            }
            ans.add(cur)

            return ans
        }

        /**
         * 多想想最近公共祖先的属性和定义，善用搜索二叉树的定义！
         * 最近的公共祖先就是从一个深度最大，能向下搜索到这两个节点的一个父节点，所以我们在找这两个节点的时候，
         * 除了要利用上二叉搜索树的有序的特性(根据当前节点的值和p,q的值就能推出p和q分别在当前节点的左子树还是右子树上)，
         * 还要想到的一点就是，如果p,q正是分别在当前节点的左右子树上，那当前节点一定就是又能到p，又能到q的最深公共父节点，
         **/
        fun lowestCommonAncestor2(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
            var cur: TreeNode? = root
            p ?: return null
            q ?: return null

            while (cur != null) {
                cur = if (cur.`val` > p.`val` && cur.`val` > q.`val`) {
                    cur.left
                } else if (cur.`val` < p.`val` && cur.`val` < q.`val`) {
                    cur.right
                } else {
                    break
                }
            }

            return cur
        }

    }

    @Test
    fun test() {
        Solution73().setZeroes2(
            arrayOf(
                intArrayOf(1, 1, 1),
                intArrayOf(1, 0, 1),
                intArrayOf(1, 1, 1)
            )
        )
    }

    class TreeNode(var `val`: Int, var left: TreeNode? = null, var right: TreeNode? = null)
}








