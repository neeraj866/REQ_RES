package com.resrequsers.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.resrequsers.R
import com.resrequsers.common_classes.Utility
import com.resrequsers.models.users_models.Data
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter(val context: FragmentActivity?) :
    RecyclerView.Adapter<UserListAdapter.UserListAdapterViewHolder>() {

    var userItems: List<Data> = ArrayList()

    var onItemSelectListener: OnItemSelectListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserListAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userItems.size
    }

    override fun onBindViewHolder(holder: UserListAdapterViewHolder, position: Int) {
        val userItem = userItems[position]
        val userName = "${userItem.first_name} ${userItem.last_name}"
        holder.itemView.user_name.text = userName
        holder.itemView.user_email.text = userItem.email
        Utility.setImage(context!!, userItem.avatar, holder.itemView.user_image)

        holder.itemView.delete_user.setOnClickListener {
            onItemSelectListener?.itemSelectedForDelete(userItem)
        }

        holder.itemView.edit_user.setOnClickListener {
            onItemSelectListener?.itemSelectedForUpdate(userItem)
        }
    }

    class UserListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /*
   * This interface is created to send back the selected item to the user list fragment
   */
    interface OnItemSelectListener {
        fun itemSelectedForDelete(data: Data)
        fun itemSelectedForUpdate(data: Data)
    }
}