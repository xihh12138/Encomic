package com.xihh.base.data

data class StateData<Data>(
    override val data: Data,
    override val code: Int = REFRESH,
    override val msg: String? = null
) : Result<Data>(data, code, msg) {

    override val isSuccess get() = code == SUCCESS
    val isSuccessMore get() = code == SUCCESS_MORE
    val isRefresh get() = code == REFRESH
    val isLoadMore get() = code == LOAD_MORE
    val isFailed get() = code == FAILED
    val isFailedMore get() = code == FAILED_MORE
    val isNoData get() = code == NO_DATA
    val isNoMoreData get() = code == NO_MORE_DATA

    fun asSuccess(data: Data = this.data): StateData<Data> = copy(data, SUCCESS)
    fun asSuccessMore(data: Data = this.data): StateData<Data> = copy(data, SUCCESS_MORE)
    fun asRefresh(): StateData<Data> = copy(data, REFRESH)
    fun asLoadMore(): StateData<Data> = copy(data, LOAD_MORE)
    fun asFailed(msg: String? = null): StateData<Data> = copy(data, FAILED, msg)
    fun asFailedMore(msg: String? = null): StateData<Data> = copy(data, FAILED_MORE, msg)
    fun asNoData(msg: String? = null): StateData<Data> = copy(data, NO_DATA, msg)
    fun asNoMoreData(msg: String? = null): StateData<Data> = copy(data, NO_MORE_DATA, msg)

    val stateText: String
        get() = when (code) {
            SUCCESS -> "SUCCESS"
            SUCCESS_MORE -> "SUCCESS_MORE"
            REFRESH -> "REFRESH"
            LOAD_MORE -> "LOAD_MORE"
            FAILED -> "FAILED"
            FAILED_MORE -> "FAILED_MORE"
            NO_DATA -> "NO_DATA"
            NO_MORE_DATA -> "NO_MORE_DATA"
            else -> "NULL"
        }

    override val defaultMsg: String
        get() = "失败"

    override fun toString(): String {
        return "StateData(data=$data, state=$stateText, msg=$messageOrDefault)"
    }

    companion object {
        const val SUCCESS = 0
        const val SUCCESS_MORE = 1
        const val REFRESH = 2
        const val LOAD_MORE = 3
        const val FAILED = 4
        const val FAILED_MORE = 5
        const val NO_DATA = 6
        const val NO_MORE_DATA = 7

        fun <Data> success(data: Data): StateData<Data> {
            return if (data == null || ((data is Collection<*>) && data.isEmpty())) {
                StateData(data, NO_DATA)
            } else {
                StateData(data, SUCCESS)
            }
        }

        fun <Data> successMore(data: Data): StateData<Data> {
            return if (data == null || ((data is Collection<*>) && data.isEmpty())) {
                StateData(data, NO_MORE_DATA)
            } else {
                StateData(data, SUCCESS_MORE)
            }
        }

        fun <Data> refresh(data: Data): StateData<Data> = StateData(data, REFRESH)

        fun <Data> loadMore(data: Data): StateData<Data> = StateData(data, LOAD_MORE)

        fun <Data> failed(data: Data, msg: String? = null): StateData<Data> =
            StateData(data, FAILED)

        fun <Data> failedMore(data: Data, msg: String? = null): StateData<Data> =
            StateData(data, FAILED_MORE)

        fun <Data> noData(data: Data, msg: String? = null): StateData<Data> =
            StateData(data, NO_DATA)

        fun <Data> noMoreData(data: Data, msg: String? = null): StateData<Data> =
            StateData(data, NO_MORE_DATA)


    }
}