package com.studies.shashank_assignment.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.studies.shashank_assignment.Adapters.viewpagerAdapter
import com.studies.shashank_assignment.R
import com.studies.shashank_assignment.databinding.FragmentDocsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class DocsFragment : Fragment() {

          lateinit var binding:FragmentDocsBinding
    private lateinit var viewPagerAdapter: viewpagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDocsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    private fun generateDateList(): List<String> {
        val dateList = mutableListOf<String>()
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)

        for (i in 0..10) {
            val date = currentDate.plusDays(i.toLong())
            val formattedDate = date.format(formatter)
            dateList.add(formattedDate)
        }

        return dateList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = viewpagerAdapter(this)
        binding.viewpager.adapter = viewPagerAdapter


        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.customView = createTabView(viewPagerAdapter.getTitle(position))
        }.attach()

        updateTabSelection(binding.tablayout.getTabAt(0), true)
        val unselectedtabView = binding.tablayout.getTabAt(1)?.customView
        val tabTextView = unselectedtabView?.findViewById<TextView>(R.id.tab_text)
        unselectedtabView?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        tabTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

        // Set up tab selection listener
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateTabSelection(tab, true)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabSelection(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No action needed
            }
        })


        //setup spinner 1

        val dates = generateDateList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dates)


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner1.adapter=adapter

     // setup spinner 2

        val sessions= mutableListOf<String>()
        for(i in 1 until 10){
            sessions.add("session_$i")
        }
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sessions)


        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter=adapter2
    }


    private fun updateTabSelection(tab: TabLayout.Tab?, isSelected: Boolean) {
        val tabView = tab?.customView
        val tabTextView = tabView?.findViewById<TextView>(R.id.tab_text)

        if (isSelected) {
            tabView?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
            tabTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            tabView?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            tabTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }
    }
    private fun createTabView(title: String): View {
        val tabView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        val tabTextView = tabView.findViewById<TextView>(R.id.tab_text)
        tabTextView.text = title
        return tabView
    }}