package com.studies.shashank_assignment.Fragments



import android.R.attr
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.studies.shashank_assignment.Models.entry
import com.studies.shashank_assignment.databinding.FragmentMCPBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Arrays


class MCPFragment : Fragment() {
    private lateinit var database: DatabaseReference

  lateinit var binding: FragmentMCPBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMCPBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart = binding.chart as LineChart
        val chart2 = binding.chart2 as LineChart
        val circularProgressBar = binding.circularProgressBar

        database = FirebaseDatabase.getInstance().reference
        var entries = arrayListOf<Entry>()
        var entries2 = arrayListOf<Entry>()
//        entries.add(Entry(1.0f,50.0f))
//        entries.add(Entry(2.0f,100.0f))
//        entries.add(Entry(3.0f,200.0f))
//        entries.add(Entry(4.0f,400.0f))

        CoroutineScope(Dispatchers.IO).launch {

            database.child("historylist").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val entryList = mutableListOf<entry>()
                    for (snapshot in dataSnapshot.children) {
                        val entry = snapshot.getValue(entry::class.java)
                        entry?.let { entryList.add(it) }
                    }
                    entryList.map {
                        entries.add(Entry(it.x!!.toFloat(),it.y!!.toFloat()))
                    }
                    setmovementdata(entries)

                    for (entry in entryList) {
                        Log.d("FirebaseData", "Entry: x=${entry.x}, y=${entry.y}")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                    Log.w("FirebaseData", "loadPost:onCancelled", databaseError.toException())
                }
            })
        }
        CoroutineScope(Dispatchers.IO).launch {

            database.child("movementlist").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val entryList = mutableListOf<entry>()
                    for (snapshot in dataSnapshot.children) {
                        val entry = snapshot.getValue(entry::class.java)
                        entry?.let { entryList.add(it) }
                    }
                    entryList.map {
                        entries2.add(Entry(it.x!!.toFloat(),it.y!!.toFloat()))
                    }
                    sethistorydata(entries2)

                    for (entry in entryList) {
                        Log.d("FirebaseData", "Entry: x=${entry.x}, y=${entry.y}")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                    Log.w("FirebaseData", "loadPost:onCancelled", databaseError.toException())
                }
            })
        }








        circularProgressBar.apply {

            setProgressWithAnimation(65f,1000)
            progressMax = 200f
            // Set ProgressBar Color
            progressBarColor = Color.BLACK
            // or with gradient
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.RED
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set background ProgressBar Color
            backgroundProgressBarColor = Color.GRAY
            // or with gradient
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.RED
            backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
        }
        circularProgressBar.onProgressChangeListener = { progress ->
            binding.progresstext.text=progress.toInt().toString()
        }
    }
fun setmovementdata(entries:ArrayList<Entry>){

    val lineDataSet1 = LineDataSet(entries, "Movement Data")
    val dataSets = ArrayList<ILineDataSet>()
    dataSets.add(lineDataSet1)

    val dataLine = LineData(dataSets)
    binding.chart.setData(dataLine)

    binding.chart.animateXY(2000, 2000)
    binding.chart.invalidate()
}

    fun sethistorydata(entries:ArrayList<Entry>){
        val lineDataSet1 = LineDataSet(entries, "Movement Data")
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet1)

        val dataLine = LineData(dataSets)
        binding.chart2.setData(dataLine)

        binding.chart2.animateXY(2000, 2000)
        binding.chart2.invalidate()
    }
}