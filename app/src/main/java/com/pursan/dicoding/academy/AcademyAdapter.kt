package com.pursan.dicoding.academy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pursan.dicoding.R

class AcademyAdapter(val listAcademy: ArrayList<Academy>) : RecyclerView.Adapter<AcademyAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.layout_academy, viewGroup,false )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAcademy.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, rate, photo, overview, detail) = listAcademy[position]

        Glide.with(holder.itemView.context)
            .load(photo)
            .apply(RequestOptions())
            .into(holder.imgPhoto)

        holder.tvName.text = name
        holder.tvRate.text = rate

        val mContext = holder.itemView.context

        holder.itemView.setOnClickListener {
            val moveDetail = Intent(mContext, AcademyDetail::class.java)
            moveDetail.putExtra(AcademyDetail.EXTRA_RATE, rate)
            moveDetail.putExtra(AcademyDetail.EXTRA_NAME, name)
            moveDetail.putExtra(AcademyDetail.EXTRA_PHOTO, photo)
            moveDetail.putExtra(AcademyDetail.EXTRA_DETAIL, detail)
            moveDetail.putExtra(AcademyDetail.EXTRA_OVERVIEW, overview)
            mContext.startActivity(moveDetail)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvRate: TextView = itemView.findViewById(R.id.tv_item_rate)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

}