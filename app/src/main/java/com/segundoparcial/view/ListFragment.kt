package com.segundoparcial.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.UserAdapter
import com.example.myapplication.helpers.DataBaseHelper
import com.segundoparcial.R
import com.segundoparcial.model.User

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private var listUser= mutableListOf<User>()
    private lateinit var rvList : RecyclerView
    private lateinit var adapter: UserAdapter
    private var dataBase: DataBaseHelper? =  null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        initComponent(view)
        initListListener()
        return view
    }

    override fun onResume() {
        super.onResume()
        initListListener()
    }

    private fun initComponent(view :View){
        dataBase = DataBaseHelper(view.context)
        rvList =  view.findViewById(R.id.rvList)
        adapter = UserAdapter(listUser)
        rvList.layoutManager = LinearLayoutManager(view.context)
        rvList.adapter = adapter

    }

    private fun initListListener(){
        dataBase?.let {
            val lp = it.getAllUsers()
            listUser.clear()
            listUser.addAll(lp)
            adapter.notifyDataSetChanged()
        }


    }
}