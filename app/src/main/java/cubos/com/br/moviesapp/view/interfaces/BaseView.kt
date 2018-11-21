package com.example.ivo.mvpteste.view.interfaces

interface BaseView {

    fun onSuccess(obj: Any, page: Int, totalPages: Int)
    fun onError(message: String)

}