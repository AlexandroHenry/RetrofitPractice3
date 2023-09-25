package com.example.retrofitpractice3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.retrofitpractice3.R
import com.example.retrofitpractice3.api.RetrofitManager
import com.example.retrofitpractice3.data.Root
import com.example.retrofitpractice3.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat

class HomeFragment : Fragment(), OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        with(binding) {
            floatingActionButton.setOnClickListener(this@HomeFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when(v) {
            binding.floatingActionButton -> {
                RetrofitManager.instance.requestData { data ->
                    if (data != null) {
                        // 데이터를 받아와서 뷰에 설정
                        setDataToViews(data)
                    } else {
                        // 데이터를 가져오지 못했을 때의 처리
                    }
                }
            }
        }
    }

    private fun setDataToViews(data: Root) {
        // 받아온 데이터를 뷰에 설정
        activity?.runOnUiThread {
            // 받아온 데이터를 뷰에 설정
            binding.nameText.text = "${data.results[0].name.first} ${data.results[0].name.last}"
            binding.usernameText.text = data.results[0].login.username
            binding.ageText.text = data.results[0].dob.age.toString()
            binding.emailText.text = data.results[0].email
            binding.phoneText.text = data.results[0].phone
            binding.addrText.text = "${data.results[0].location.street.number} ${data.results[0].location.street.name}, ${data.results[0].location.city}, ${data.results[0].location.state}, ${data.results[0].location.country}, ${data.results[0].location.postcode}"
            binding.dobText.text = formatDate(data.results[0].dob.date)


            Glide.with(this)
                .load(data.results[0].picture.large)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .override(400, 400)
                .into(binding.imageView)
            // 나머지 항목도 필요한 대로 설정해주세요
        }
    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd")

        try {
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            // 파싱 또는 포맷팅 오류 처리
            return ""
        }
    }
}