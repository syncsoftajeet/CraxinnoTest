package com.example.testui.agenda.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.craxinnotest.agenda.Adapter.AgendaAdapter
import com.example.craxinnotest.agenda.model.AgnedaRequestModel
import com.example.craxinnotest.agendadetails.AgendaDetailsActivity
import com.example.testui.agenda.viewmodel.AgendaViewModel

import com.example.testui.databinding.ActivityMainBinding
import com.example.testui.network.NetworkResult
import com.example.testui.utils.AppCons
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    val viewModel: AgendaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.agendRecyclerview.showShimmerAdapter()





        viewModel.agendaresponse.observe(this){
            when(it){
                is  NetworkResult.Success ->{
                    binding.agendRecyclerview.hideShimmerAdapter()
                    Log.e("ass@222323", "onCreate:  Successs", )
                    binding.agendRecyclerview.adapter = AgendaAdapter(it.data!!.data,object : AgendaAdapter.ItemClickListener {
                        override fun onItemClicked(aid: String) {
                            startActivity(Intent(this@MainActivity, AgendaDetailsActivity::class.java).putExtra(AppCons.AID,aid))
                        }

                    })
                }
                is NetworkResult.Error ->{
                    Log.e("ass@222323", "onCreate:  error", )
                }
                is NetworkResult.Loading ->{
                    Log.e("ass@222323", "onCreate:  Loading", )
                }

                else -> {}
            }
        }

        lifecycleScope.launch(Dispatchers.IO){
            viewModel.getAgendaList(AgnedaRequestModel("1989","117195"))
        }

    }


}