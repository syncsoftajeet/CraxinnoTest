package com.example.craxinnotest.agendadetails

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable.Orientation
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.craxinnotest.agenda.model.Attendee
import com.example.testui.R
import com.example.testui.agenda.viewmodel.AgendaViewModel
import com.example.testui.agendadetails.model.AgendaSpeaker
import com.example.testui.databinding.ActivityAgendaDetailsBinding
import com.example.testui.databinding.DetailsUserItemsBinding
import com.example.testui.databinding.SpeakersItesmBinding
import com.example.testui.databinding.UserItemsBinding
import com.example.testui.network.NetworkResult
import com.example.testui.utils.AppCons
import com.example.testui.utils.formatDateTime
import com.example.testui.utils.formatmothYear
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class AgendaDetailsActivity : AppCompatActivity() {

    var _binding: ActivityAgendaDetailsBinding? = null
    val binding get() = _binding!!
    var registerUrl  : String? = null
    var documentUrl  : String? = null
    var aid : String? = null

    val viewModel: AgendaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAgendaDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


         aid = intent.getStringExtra(AppCons.AID)


        val layoutManager = LinearLayoutManager(this@AgendaDetailsActivity)
        binding.speakerRecyclerview.layoutManager = layoutManager


        binding.registrationlinkButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(registerUrl))
            startActivity(browserIntent)
        }

        binding.documentlinkButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(documentUrl))
            startActivity(browserIntent)
        }


        binding.backbtn.setOnClickListener {
            finish()
        }

        viewModel.agendaDetailslResponse.observe(this){
            when(it){
                is NetworkResult.Success ->{
                    binding.progresslayout.progresslayout.visibility = View.GONE
                    val data = it.data?.data!!
                    binding.agendaTitle.text = data.name
                    binding.mlocation.text = data.location_name
                    binding.monthDate.text = formatmothYear(data.start_date)
                    binding.time.text = "${formatDateTime(data.start_date)} - ${formatDateTime(data.end_date)} EST"
                    Glide.with(this@AgendaDetailsActivity).load("${it.data.imgPath}${data.header_img}").into(binding.bannerImage)
                    Glide.with(this@AgendaDetailsActivity).load("${it.data.imgPath}${data.sponsor_img}").into(binding.sponserImage)


                    binding.descriptionWebview.setBackgroundColor(Color.TRANSPARENT);
                    binding.descriptionWebview.loadDataWithBaseURL(null, data.description, "text/html", "UTF-8", null)
                    binding.sponserName.text = data.sponsor_name
                    binding.registrationlinkButton.text = data.register_links[0].register_text
                    binding.documentlinkButton.text = data.agenda_documents[0].document_name
                    registerUrl = data.register_links[0].register_link
                    documentUrl = data.agenda_documents[0].document_file
                    binding.speakerRecyclerview.adapter = SpeakerAdapter(data.agenda_speakers)


                    binding.userRecyclerview.adapter = AttendeeAdapter(data.attendees)
                }
                is NetworkResult.Error ->{
                    binding.progresslayout.progresslayout.visibility = View.VISIBLE
                }
                is NetworkResult.Loading ->{
                    binding.progresslayout.progresslayout.visibility = View.VISIBLE
                }

                else -> {}
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            aid?.let {
                viewModel.getAgendaDetails(it)
            }
        }



    }


    class SpeakerAdapter(val agendaSpeakerslist: List<AgendaSpeaker>) : RecyclerView.Adapter<SpeakerAdapter.SpeakerDataHolder>() {
        class SpeakerDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val binding = SpeakersItesmBinding.bind(itemView.rootView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerDataHolder {
            return SpeakerDataHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.speakers_itesm, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return agendaSpeakerslist.size
        }

        override fun onBindViewHolder(holder: SpeakerDataHolder, position: Int) {
            val speaker = agendaSpeakerslist[position]

            holder.binding.speakername.text = speaker.name
            holder.binding.speakerType.text = speaker.title
            Glide.with(holder.itemView.context).load(speaker.image).placeholder(R.drawable.userplaceholer).into(holder.binding.speakerImage)
        }
    }


    class AttendeeAdapter(val attendeeslist: List<Attendee>)  : RecyclerView.Adapter<AttendeeAdapter.UserDataHolder>(){
        class UserDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val binding = DetailsUserItemsBinding.bind(itemView.rootView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataHolder {
            return UserDataHolder(LayoutInflater.from(parent.context).inflate(R.layout.details_user_items,parent,false))

        }

        override fun getItemCount(): Int {
            return attendeeslist.size
        }

        override fun onBindViewHolder(holder: UserDataHolder, position: Int) {
            attendeeslist[position].image?.let {
                Glide.with(holder.itemView.context).load(attendeeslist[position].image).placeholder(R.drawable.userplaceholer).into(holder.binding.images)
            }

        }
    }
}