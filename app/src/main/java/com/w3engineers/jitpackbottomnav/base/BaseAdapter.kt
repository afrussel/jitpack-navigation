package com.core.kbasekit.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 12/13/2017 at 4:56 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Imran Hossain on 04/23/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

open abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val mItemList: ArrayList<T> = arrayListOf()
    protected var mListener: ItemClickListener<T>? = null

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun addItem(item: T): Int {
        val tItem = findItem(item)

        if (tItem == null) {
            mItemList.add(item)
            notifyItemInserted(mItemList.size - 1)
            return mItemList.size - 1
        }
        return updateItem(item, tItem)
    }

    fun getItem(pos: Int): T? {
        if(mItemList.size == 0 || pos >= mItemList.size) return null
        return mItemList.get(pos)
    }

    fun addItems(itemList: List<T>) {
        for(item : T in itemList){
         addItem(item)
        }
    }

    fun removeItem(t: T) {
        var index: Int = mItemList.indexOf(t);

        if (index < 0 || index >= mItemList.size) return

        mItemList.removeAt(index)
        notifyItemMoved(index, mItemList.size)
    }

    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    fun getItems(): List<T> {
        return mItemList
    }

    /**
     * @param oldItem T type object
     * @param newItem T type object
     * @return int value: newItem position in list
     */
    fun updateItem(oldItem: T, newItem: T): Int {
        val toIndex = mItemList.indexOf(newItem)
        mItemList[toIndex] = oldItem
        notifyItemChanged(toIndex)
        return toIndex
    }

    fun findItem(item: T): T? {
        for (tItem in mItemList) {
            if (isEqual(item, tItem)) {
                return tItem
            }
        }
        return null
    }

    fun setClickLisener(listener: ItemClickListener<T>) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return newViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        var item: T? = getItem(position)
        holder.bind(item!!)
    }

    fun inflate(viewGroup: ViewGroup, item_layout: Int): ViewDataBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), item_layout, viewGroup, false)
    }

    abstract fun newViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T>
    abstract fun isEqual(leftItem: T, rightItem: T): Boolean
}