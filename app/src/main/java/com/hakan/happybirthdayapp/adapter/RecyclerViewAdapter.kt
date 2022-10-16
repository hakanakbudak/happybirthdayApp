package com.hakan.happybirthdayapp.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hakan.happybirthdayapp.data.User
import com.hakan.happybirthdayapp.databinding.FragmentEventBinding
import com.hakan.happybirthdayapp.databinding.UserItemBinding


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {}


    var userList = emptyList<User>()

    private var _binding: RecyclerViewAdapter? = null
    private val binding: RecyclerViewAdapter get() = _binding!!


    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(user: User) {
        (userList as MutableList).remove(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        return MyViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.apply {
            txtName.text = currentItem.name
            txtSurname.text = currentItem.surname
            txtDepartment.text = currentItem.departmentText
            //txtDepartment.text=currentItem.departmentPosition.toString()
            txtDate.text=currentItem.date
            txtNote.text=currentItem.descriptor
            root.setOnClickListener{
                onClickListener?.let { clickListener->
                    clickListener(holder.absoluteAdapterPosition, currentItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    private var onClickListener: ((position: Int, user: User) -> Unit)? = null
    fun setOnClickListener(f: (position: Int, user: User) -> Unit) {
        onClickListener = f
    }

}