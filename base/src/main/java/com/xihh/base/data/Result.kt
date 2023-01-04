package com.xihh.base.data

open class Result<Data>(
    open val data: Data,
    open val code: Int = 0,
    open val msg: String? = null,
) {

    open val isSuccess get() = code == 0 || code == 200

    val messageOrDefault get() = msg ?: defaultMsg
    open val defaultMsg get() = "网络异常"

    fun success(block: Result<Data>.(Data) -> Unit) {
        if (isSuccess) {
            block(data)
        }
    }

    fun fail(block: Result<Data>.(Int, String?) -> Unit) {
        if (!isSuccess) {
            block(code, messageOrDefault)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Result<*>

        if (data != other.data) return false
        if (code != other.code) return false
        if (msg != other.msg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + code
        result = 31 * result + (msg?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "API(data=$data, code=$code, msg=$msg)"
    }

}