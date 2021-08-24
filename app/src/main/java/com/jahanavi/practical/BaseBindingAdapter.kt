package com.layPay.core


import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.layPay.base.BaseBindingViewHolder

abstract class BaseBindingAdapter<I> : RecyclerView.Adapter<BaseBindingViewHolder>(),
    BaseBindingViewHolder.ClickListener {

    protected var items: MutableList<I>? = ArrayList()
    private var itemClickListener: ItemClickListener<I>? = null
    private var checkedListener: CheckChangeListener<I>? = null

    override fun onViewClick(position: Int) {
        if (itemClickListener != null && items != null) {
            itemClickListener!!.onClick(items!![position], position)
        }
    }

    fun onViewChecked(position: Int, checked: Boolean) {
        if (checkedListener != null && items != null) {
            checkedListener!!.onClick(items!![position], position, checked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = bind(inflater, parent, viewType)
        return BaseBindingViewHolder(binding, this)
    }

    protected abstract fun bind(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding

    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }

    fun setListItems(items: MutableList<I>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getListItems(): List<I>? {
        return items
    }

    @JvmOverloads
    fun addItem(item: I, position: Int = items!!.size) {
        items!!.add(position, item)
        notifyItemInserted(position)
    }

    @JvmOverloads
    fun addItems(itemsToAdd: MutableList<I>, position: Int = items!!.size) {
        items!!.addAll(position, itemsToAdd as Collection<I>)
        notifyItemRangeInserted(position, itemsToAdd.size)
    }

    fun removeItem(position: Int) {
        try {
            items!!.removeAt(position)
            notifyDataSetChanged()
        } catch (e: IndexOutOfBoundsException) {

        }

    }

    fun clear() {
        val size = items!!.size
        if (size > 0) {
            items!!.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener<I>) {
        this.itemClickListener = itemClickListener
    }

    fun setCheckedChangeListener(itemClickListener: CheckChangeListener<I>) {
        this.checkedListener = itemClickListener

    }

    interface ItemClickListener<I> {
        fun onClick(item: I, position: Int)
    }



    interface CheckChangeListener<I> {
        fun onClick(item: I, position: Int, checked: Boolean)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
