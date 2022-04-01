package com.dk.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class SoftInputHelper {

    companion object {
        fun showSoftInput(editText: EditText?) {
            editText?.requestFocus()
            val inputMethodManager = editText?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager?.showSoftInput(editText, 0)
        }

        fun hideSoftInput(editText: EditText?) {
            val inputMethodManager = editText?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager?.hideSoftInputFromWindow(editText?.windowToken, 0)
        }
    }


    var mRootViewVisibleHeight  = 0 //纪录根视图的显示高度

    interface OnSoftInputChangeListener {
        fun onChanged(isShow: Boolean, height: Int)
    }

    fun attachSoftInput(activity: Activity?, listener: OnSoftInputChangeListener?) {
        val rootView = activity?.window?.decorView ?: return
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener { //获取当前根视图在屏幕上显示的大小
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val visibleHeight = r.height()
            if (mRootViewVisibleHeight == 0) {
                mRootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }
            //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
            if (mRootViewVisibleHeight == visibleHeight) {
                return@OnGlobalLayoutListener
            }
            //根视图显示高度变小超过200，可以看作软键盘显示了
            if (mRootViewVisibleHeight - visibleHeight > 200) {
                listener?.onChanged(true, mRootViewVisibleHeight - visibleHeight)
                mRootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }

            //根视图显示高度变大超过200，可以看作软键盘隐藏了
            if (visibleHeight - mRootViewVisibleHeight > 200) {
                listener?.onChanged(false, visibleHeight - mRootViewVisibleHeight)
                mRootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }
        })
    }


}