package com.nanaten.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nanaten.todoapp.R
import com.nanaten.todoapp.databinding.ListItemEmptyBinding
import com.nanaten.todoapp.databinding.ListItemTodoBinding
import com.nanaten.todoapp.db.Todo

class TodoAdapter : BaseRecyclerViewAdapter() {
    private var list: List<Todo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (list.isEmpty()) {
            EmptyItemViewHolder(parent)
        } else {
            TodoItemViewHolder(parent)
        }
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 1 else list.size
    }

    fun setData(list: List<Todo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoItemViewHolder -> {
                holder.bind(list[position])
            }
            else -> return
        }
    }

    class TodoItemViewHolder(
        parent: ViewGroup,
        private val binding: ListItemTodoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_todo,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Todo) {
            binding.todo = item
        }
    }

    class EmptyItemViewHolder(
        parent: ViewGroup,
        private val binding: ListItemEmptyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_empty,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root)
}